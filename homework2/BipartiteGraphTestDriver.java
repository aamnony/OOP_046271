package homework2;

import java.util.*;

import javax.swing.event.ListSelectionEvent;

/**
 * This class implements a testing driver for BipartiteGraph. The driver manages
 * BipartiteGraphs whose nodes and edges are Strings.
 */
public class BipartiteGraphTestDriver {

    private Map<String, BipartiteGraph<String>> graphs;

    /**
     * @modifies this
     * @effects Constructs a new test driver.
     */
    public BipartiteGraphTestDriver() {
        graphs = new HashMap<>();
    }

    /**
     * @requires graphName != null
     * @modifies this
     * @effects Creates a new graph named graphName. The graph is initially empty.
     */
    public void createGraph(String graphName) {
        graphs.put(graphName, new BipartiteGraph<>());
    }

    /**
     * @requires createGraph(graphName) && nodeName != null && neither
     *           addBlackNode(graphName,nodeName) nor
     *           addWhiteNode(graphName,nodeName) has already been called on this
     * @modifies graph named graphName
     * @effects Adds a black node represented by the String nodeName to the graph
     *          named graphName.
     * @return {@code true} - if the node was added,<br>
     *         {@code false} - otherwise.
     */
    public boolean addBlackNode(String graphName, String nodeName) {
        return graphs.get(graphName).addNode(nodeName, null, false);
    }

    /**
     * @requires createGraph(graphName) && nodeName != null && neither
     *           addBlackNode(graphName,nodeName) nor
     *           addWhiteNode(graphName,nodeName) has already been called on this
     * @modifies graph named graphName
     * @effects Adds a white node represented by the String nodeName to the graph
     *          named graphName.
     * @return {@code true} - if the node was added,<br>
     *         {@code false} - otherwise.
     */
    public boolean addWhiteNode(String graphName, String nodeName) {
        return graphs.get(graphName).addNode(nodeName, null, true);
    }

    /**
     * @requires createGraph(graphName) && ((addBlackNode(parentName) &&
     *           addWhiteNode(childName)) || (addWhiteNode(parentName) &&
     *           addBlackNode(childName))) && edgeLabel != null && node named
     *           parentName has no other outgoing edge labeled edgeLabel && node
     *           named childName has no other incoming edge labeled edgeLabel
     * @modifies graph named graphName
     * @effects Adds an edge from the node parentName to the node childName in the
     *          graph graphName. The new edge's label is the String edgeLabel.
     * @return {@code true} - if the edge was added,<br>
     *         {@code false} - otherwise.
     */
    public boolean addEdge(String graphName, String parentName, String childName, String edgeLabel) {
        return graphs.get(graphName).addEdge(parentName, childName, edgeLabel);
    }

    /**
     * @requires createGraph(graphName)
     * @return a space-separated list of the names of all the black nodes in the
     *         graph graphName, in alphabetical order.
     */
    public String listBlackNodes(String graphName) {
        List<String> blackNodes = new ArrayList<>(graphs.get(graphName).getNodes(false));
        Collections.sort(blackNodes);
        return toSpaceSeparatedString(blackNodes);
    }

    /**
     * @requires createGraph(graphName)
     * @return a space-separated list of the names of all the white nodes in the
     *         graph graphName, in alphabetical order.
     */
    public String listWhiteNodes(String graphName) {
        List<String> whiteNodes = new ArrayList<>(graphs.get(graphName).getNodes(true));
        Collections.sort(whiteNodes);
        return toSpaceSeparatedString(whiteNodes);
    }

    /**
     * @requires createGraph(graphName) && createNode(parentName)
     * @return a space-separated list of the names of the children of parentName in
     *         the graph graphName, in alphabetical order.
     */
    public String listChildren(String graphName, String parentName) {
        List<String> children = new ArrayList<>(graphs.get(graphName).getChildren(parentName));
        Collections.sort(children);
        return toSpaceSeparatedString(children);
    }

    /**
     * @requires createGraph(graphName) && createNode(childName)
     * @return a space-separated list of the names of the parents of childName in
     *         the graph graphName, in alphabetical order.
     */
    public String listParents(String graphName, String childName) {
        List<String> parents = new ArrayList<>(graphs.get(graphName).getParents(childName));
        Collections.sort(parents);
        return toSpaceSeparatedString(parents);
    }

    /**
     * @requires addEdge(graphName, parentName, str, edgeLabel) for some string str
     * @return the name of the child of parentName that is connected by the edge
     *         labeled edgeLabel, in the graph graphName.
     */
    public String getChildByEdgeLabel(String graphName, String parentName, String edgeLabel) {
        return graphs.get(graphName).getChildByEdgeLabel(parentName, edgeLabel);
    }

    /**
     * @requires addEdge(graphName, str, childName, edgeLabel) for some string str
     * @return the name of the parent of childName that is connected by the edge
     *         labeled edgeLabel, in the graph graphName.
     */
    public String getParentByEdgeLabel(String graphName, String childName, String edgeLabel) {
        return graphs.get(graphName).getParentByEdgeLabel(childName, edgeLabel);
    }

    /**
     * @return A space-separated {@link String} of the elements in the given list.
     */
    private static String toSpaceSeparatedString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (Iterator<String> it = list.iterator(); it.hasNext();) {
            String node = it.next();
            sb.append(node);
            if (it.hasNext()) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }
}
