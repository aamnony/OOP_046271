package homework2.test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Represents a graph, where each <i>node</i>:
 * <li>Is labeled
 * <li>Is colored - <i>black</i> or <i>white</i>
 * <li>May contain data</li><br>
 * Nodes may be connected to each other via <i>edges</i>, which are also
 * labeled.<br>
 * Edges exist only between opposite colored nodes, i.e. from <b>black</b> to
 * <b>white</b>, or from <b>white</b> to <b>black</b>.<br>
 * Additionally, edges have direction, meaning two different edges may connect
 * <b>node 1</b> and <b>node 2</b>:
 * <li>An edge from <b>node 1</b> to <b>node 2</b>
 * <li>An edge from <b>node 2</b> to <b>node 1</b></li>
 * <p>
 * 
 * @param <L>
 *            The type of the node and edge labels.
 * 
 * @see LabeledEdgedGraph
 */
public class ColoredGraph<L> {

    private Map<L, Node<L>> labelNodeMap = new HashMap<>();

    // Abstraction Function:
    // Represents a bipartite graph (see class description), where this.labelNodeMap
    // contains a mapping between the nodes' labels and the nodes themselves.
    // The nodes are represented by the inner class Node<L>, which contains the node
    // data object, its color, and all the edges connecting it and the other nodes
    // in this graph.

    // Representation Invariant:
    // this.labelNodeMap does not contain null keys and values.
    // I.e. the graph does not contain null labels for nodes or null Nodes.
    // (see Representation Invariant of inner class Node for more information).

    private void checkRep() {
        assert (!labelNodeMap.containsKey(null)
                && !labelNodeMap.containsValue(null)) : "This graph cannot contain null nodes";

    }

    /**
     * @modifies This.
     * @effects Adds a new node labeled {@code nodeLabel}, with data
     *          {@code nodeData} and color based on {@code isWhite}, to this
     *          graph.<br>
     *          If a node labeled {@code nodeLabel} already exists in this graph -
     *          this method does not modify this graph.
     * @return {@code true} - if the node was added,<br>
     *         {@code false} - otherwise.
     */
    public boolean addNode(L nodeLabel, Object nodeData, boolean isWhite) {
        checkRep();
        if (labelNodeMap.containsKey(nodeLabel)) {
            return false;
        }

        labelNodeMap.put(nodeLabel, new Node<L>(nodeData, isWhite));
        checkRep();
        return true;
    }

    /**
     * @modifies This.
     * @effects Removes the node labeled {@code nodeLabel}, and all its edges
     *          (incoming and outgoing) from this graph.<br>
     *          If no such node exists - this method does not modify this graph.
     * @return {@code true} - if the node was removed,<br>
     *         {@code false} - otherwise.
     */
    public boolean removeNode(L nodeLabel) {
        checkRep();
        if (!labelNodeMap.containsKey(nodeLabel)) {
            return false;
        }

        Node<L> node = labelNodeMap.get(nodeLabel);

        // Remove all edges between the given node and its children.
        Collection<L> childLabels = node.getChildren();
        for (L childLabel : childLabels) {
            Node<L> childNode = labelNodeMap.get(childLabel);
            childNode.disconnectParent(nodeLabel);
        }

        // Remove all edges between the given node and its parents.
        Collection<L> parentLabels = node.getParents();
        for (L parentLabel : parentLabels) {
            Node<L> parentNode = labelNodeMap.get(parentLabel);
            parentNode.disconnectChild(nodeLabel);
        }

        // Finally, remove the given node from the graph.
        labelNodeMap.remove(nodeLabel);
        checkRep();
        return true;
    }

    /**
     * @return The data of the node labeled {@code nodeLabel}.
     * @throws IllegalArgumentException
     *             if no node labeled {@code nodeLabel} exists in this graph.
     */
    public Object getNodeData(L nodeLabel) throws IllegalArgumentException {
        checkRep();
        if (!labelNodeMap.containsKey(nodeLabel)) {
            throw new IllegalArgumentException("No node labeled: [" + nodeLabel + "] exists in this graph.");
        }
        return labelNodeMap.get(nodeLabel).data;
    }

    /**
     * @modifies The node labeled {@code nodeLabel} in this graph.
     * @effects Replaces the data of the node labeled {@code nodeLabel} with
     *          {@code newNodeData}.
     * @throws IllegalArgumentException
     *             if no node labeled {@code nodeLabel} exists in this graph.
     */
    public void setNodeData(L nodeLabel, Object newNodeData) throws IllegalArgumentException {
        checkRep();
        if (!labelNodeMap.containsKey(nodeLabel)) {
            throw new IllegalArgumentException("No node labeled: [" + nodeLabel + "] exists in this graph.");
        }
        labelNodeMap.get(nodeLabel).data = newNodeData;
    }

    /**
     * @requires An edge from parentNode and childNode (with any label) does not
     *           exist.
     * @modifies This.
     * @effects Adds an edge from the node labeled {@code parentNodeLabel} to the
     *          node labeled {@code childNodeLabel} in this graph. The new edge is
     *          labeled {@code edgeLabel}.<br>
     *          If the edge cannot be added to this graph- this method does not
     *          modify this graph.
     * @return {@code true} - if the edge was added,<br>
     *         {@code false} otherwise.
     */
    public boolean addEdge(L parentNodeLabel, L childNodeLabel, L edgeLabel) {
        checkRep();
        if (labelNodeMap.containsKey(parentNodeLabel) && labelNodeMap.containsKey(childNodeLabel)) {
            Node<L> parent = labelNodeMap.get(parentNodeLabel);
            Node<L> child = labelNodeMap.get(childNodeLabel);
            if (parentNodeLabel.equals(childNodeLabel)) {
                return false;
            }
            // Nodes have valid colors - connect them with an edge labeled edgeLabel.
            parent.connectChild(childNodeLabel, edgeLabel);
            child.connectParent(parentNodeLabel, edgeLabel);
            return true;
        }
        return false;
    }

    /**
     * @modifies This.
     * @effects Removes the edge labeled {@code edgeLabel}, which is from the node
     *          labeled {@code parentNodeLabel} to the node labeled
     *          {@code childNodeLabel}, from this graph.<br>
     *          If no such edge exists - this method does not modify this graph.
     * @return {@code true} - if the edge was removed,<br>
     *         {@code false} otherwise.
     */
    public boolean removeEdge(L parentNodeLabel, L childNodeLabel, L edgeLabel) {
        checkRep();
        if (labelNodeMap.containsKey(parentNodeLabel) && labelNodeMap.containsKey(childNodeLabel)) {
            // Both nodes exist in the graph, we can continue checking.
            Node<L> parent = labelNodeMap.get(parentNodeLabel);
            Node<L> child = labelNodeMap.get(childNodeLabel);

            if (parent.isConnectedToChild(childNodeLabel, edgeLabel)
                    && child.isConnectedToParent(parentNodeLabel, edgeLabel)) {

                // The nodes are connected with each other, and the connecting edge is labeled
                // edgeLabel - we found it, so lets remove it.
                parent.disconnectChild(childNodeLabel);
                child.disconnectParent(parentNodeLabel);
                return true;
            }
        }
        return false;
    }

    /**
     * @return An unmodifiable (read-only) {@link Collection} of all the node labels
     *         in this graph.
     */
    public Collection<L> getNodes() {
        checkRep();
        return Collections.unmodifiableSet(labelNodeMap.keySet());
    }

    /**
     * @return An unmodifiable (read-only) {@link Collection} of all the labels of
     *         nodes with the given color, in this graph.
     */
    public Collection<L> getNodes(boolean white) {
        checkRep();
        Set<L> labels = new HashSet<>(labelNodeMap.size() / 2);

        for (Entry<L, Node<L>> entry : labelNodeMap.entrySet()) {
            if (entry.getValue().isWhite == white) {
                labels.add(entry.getKey());
            }
        }
        return labels;
    }

    /**
     * @return An unmodifiable (read-only) {@link Collection} of all the child node
     *         labels of the node labeled {@code nodeLabel}, in this graph.<br>
     *         If no node labeled {@code nodeLabel} exists in this graph,
     *         {@code null} is returned.
     */
    public Collection<L> getChildren(L nodeLabel) {
        checkRep();
        if (labelNodeMap.containsKey(nodeLabel)) {
            return Collections.unmodifiableSet(labelNodeMap.get(nodeLabel).outgoingEdges.keySet());
        }
        return null;
    }

    /**
     * @return An unmodifiable (read-only) {@link Collection} of all the parent node
     *         labels of the node labeled {@code nodeLabel}, in this graph.<br>
     *         If no node labeled {@code nodeLabel} exists in this graph,
     *         {@code null} is returned.
     */
    public Collection<L> getParents(L nodeLabel) {
        checkRep();
        if (labelNodeMap.containsKey(nodeLabel)) {
            return Collections.unmodifiableSet(labelNodeMap.get(nodeLabel).incomingEdges.keySet());
        }
        return null;
    }

    /**
     * @return The label of the <b>child</b> node, that is connected by the edge
     *         labeled {@code edgeLabel} to the node labeled
     *         {@code parentNodeLabel}, in this graph.<br>
     *         If no such label exists - {@code null} is returned.
     */
    public L getChildByEdgeLabel(L parentNodeLabel, L edgeLabel) {
        checkRep();
        if (!labelNodeMap.containsKey(parentNodeLabel)) {
            return null;
        }

        Node<L> node = labelNodeMap.get(parentNodeLabel);
        return node.getChild(edgeLabel);
    }

    /**
     * @return The label of the <b>parent</b> node, that is connected by the edge
     *         labeled {@code edgeLabel} to the node labeled {@code childNodeLabel},
     *         in this graph.<br>
     *         If no such label exists - {@code null} is returned.
     */
    public L getParentByEdgeLabel(L childNodeLabel, L edgeLabel) {
        checkRep();
        if (!labelNodeMap.containsKey(childNodeLabel)) {
            return null;
        }

        Node<L> node = labelNodeMap.get(childNodeLabel);
        return node.getParent(edgeLabel);
    }

    /**
     * @return An unmodifiable (read-only) {@link Collection} of all the edge
     *         labels, connecting out from the node labeled {@code nodeLabel}, in
     *         this graph.<br>
     *         If no node labeled {@code nodeLabel} exists in this graph,
     *         {@code null} is returned.
     */
    public Collection<L> getOutgoingEdges(L nodeLabel) {
        checkRep();
        if (labelNodeMap.containsKey(nodeLabel)) {
            return Collections.unmodifiableCollection(labelNodeMap.get(nodeLabel).outgoingEdges.values());
        }
        return null;
    }

    /**
     * @return An unmodifiable (read-only) {@link Collection} of all the edge
     *         labels, connecting into the node labeled {@code nodeLabel}, in this
     *         graph.<br>
     *         If no node labeled {@code nodeLabel} exists in this graph,
     *         {@code null} is returned.
     */
    public Collection<L> getIncomingEdges(L nodeLabel) {
        checkRep();
        if (labelNodeMap.containsKey(nodeLabel)) {
            return Collections.unmodifiableCollection(labelNodeMap.get(nodeLabel).incomingEdges.values());
        }
        return null;
    }

    /**
     * 
     * Represents a node in a Bipartite Graph.
     *
     * @param <L>
     *            The type of the node and edge labels.
     */
    private static class Node<L> {

        private Object data;
        private boolean isWhite;
        private Map<L, L> incomingEdges; // key = parent labels, value = edges labels
        private Map<L, L> outgoingEdges; // key = child labels, value = edges labels

        // Abstraction Function:
        // Represents a node in a bipartite graph, where:
        //
        // * this.data contains the (optional) data of this node
        //
        // * this.isWhite is true if the node is colored white, and false if black,
        //
        // * this.outgoingEdges contains the edges from this node to it's children
        // ---- (keys = child labels, values = edges labels)
        //
        // * this.incomingEdges contains the edges from this node's parents to itself
        // ---- (keys = parent labels, values = edges labels)

        // Representation Invariant:
        // this.incomingEdges does not contain null keys and values &&
        // this.outgoingEdges does not contain null keys and values.
        // I.e. the node does not connect to null labeled or connect to nodes via null
        // labeled edges.

        private void checkRep() {
            assert (!incomingEdges.containsKey(null)
                    && !outgoingEdges.containsKey(null)) : "This node cannot be connected to null labeled nodes";

            assert (!incomingEdges.containsValue(null) && !outgoingEdges
                    .containsValue(null)) : "This node cannot connect to other nodes via null labeled edges";
        }

        /**
         * @effects Initializes this with a given data and color.
         */
        private Node(Object data, boolean isWhite) {
            this.data = data;
            this.isWhite = isWhite;
            incomingEdges = new HashMap<>();
            outgoingEdges = new HashMap<>();
        }

        /**
         * @return {@code true} - if a child labeled {@code childNodeLabel} is connected
         *         to this node via an edge labeled {@code edgeLabel}.
         */
        private boolean isConnectedToChild(L childNodeLabel, L edgeLabel) {
            checkRep();
            if (outgoingEdges.containsKey(childNodeLabel)) {
                return outgoingEdges.get(childNodeLabel).equals(edgeLabel);
            }
            return false;
        }

        /**
         * @return {@code true} - if a parent labeled {@code parentNodeLabel} is
         *         connected to this node via an edge labeled {@code edgeLabel}.
         */
        private boolean isConnectedToParent(L parentNodeLabel, L edgeLabel) {
            checkRep();
            if (incomingEdges.containsKey(parentNodeLabel)) {
                return incomingEdges.get(parentNodeLabel).equals(edgeLabel);
            }
            return false;
        }

        /**
         * @modifies This.
         * @effects Adds an edge labeled {@code edgeLabel} from a node labeled
         *          {@code parentNodeLabel} to this node.<br>
         *          If an edge already exists from a node labeled
         *          {@code parentNodeLabel} to this node - this method does not modify
         *          this node.
         * @return {@code true} - if the edge was added,<br>
         *         {@code false} otherwise.
         */
        private boolean connectParent(L parentNodeLabel, L edgeLabel) {
            checkRep();
            if (!incomingEdges.containsKey(parentNodeLabel)) {
                incomingEdges.put(parentNodeLabel, edgeLabel);
                checkRep();
                return true;
            }
            return false;
        }

        /**
         * @modifies This.
         * @effects Adds an edge labeled {@code edgeLabel} from this node to a node
         *          labeled {@code childNodeLabel}.<br>
         *          If an edge already exists from this node to a node labeled
         *          {@code childNodeLabel} - this method does not modify this node.
         * @return {@code true} - if the edge was added,<br>
         *         {@code false} otherwise.
         */
        private boolean connectChild(L childNodeLabel, L edgeLabel) {
            checkRep();
            if (!outgoingEdges.containsKey(childNodeLabel)) {
                outgoingEdges.put(childNodeLabel, edgeLabel);
                checkRep();
                return true;
            }
            return false;
        }

        /**
         * @modifies This.
         * @effects Removes the edge from a node labeled {@code parentNodeLabel} to this
         *          node.<br>
         *          If no such edge exists - this method does not modify this node.
         * @return {@code true} - if the edge was removed,<br>
         *         {@code false} otherwise.
         */
        private boolean disconnectParent(L parentNodeLabel) {
            checkRep();
            if (incomingEdges.containsKey(parentNodeLabel)) {
                incomingEdges.remove(parentNodeLabel);
                checkRep();
                return true;
            }
            return false;
        }

        /**
         * @modifies This.
         * @effects Removes the edge from this node to a node labeled
         *          {@code parentNodeLabel}.<br>
         *          If no such edge exists - this method does not modify this node.
         * @return {@code true} - if the edge was removed,<br>
         *         {@code false} otherwise.
         */
        private boolean disconnectChild(L childNodeLabel) {
            checkRep();
            if (outgoingEdges.containsKey(childNodeLabel)) {
                outgoingEdges.remove(childNodeLabel);
                checkRep();
                return true;
            }
            return false;
        }

        /**
         * @return The label of the <b>parent</b> node, that is connected by the edge
         *         labeled {@code edgeLabel} to this node.<br>
         *         If no such label exists - {@code null} is returned.
         */
        private L getParent(L edgeLabel) {
            checkRep();
            if (incomingEdges.containsValue(edgeLabel)) {
                Set<L> parentLabels = incomingEdges.keySet();
                for (L parentLabel : parentLabels) {
                    if (edgeLabel.equals(incomingEdges.get(parentLabel))) {
                        return parentLabel;
                    }
                }
            }
            return null;
        }

        /**
         * @return The label of the <b>child</b> node, that is connected by the edge
         *         labeled {@code edgeLabel} to this node.<br>
         *         If no such label exists - {@code null} is returned.
         */
        private L getChild(L edgeLabel) {
            checkRep();
            if (outgoingEdges.containsValue(edgeLabel)) {
                Set<L> childLabels = outgoingEdges.keySet();
                for (L childLabel : childLabels) {
                    if (edgeLabel.equals(outgoingEdges.get(childLabel))) {
                        return childLabel;
                    }
                }
            }
            return null;
        }

        /**
         * @return A {@link Collection} of all the parent node labels of this node.
         */
        private Collection<L> getParents() {
            checkRep();
            return incomingEdges.keySet();
        }

        /**
         * @return A {@link Collection} of all the child node labels of this node.
         */
        private Collection<L> getChildren() {
            checkRep();
            return outgoingEdges.keySet();
        }
    }
}
