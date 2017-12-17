package homework2;

import java.util.Collection;
import java.util.List;

import hw0.Coin;

/**
 * This class implements simulation of pipes and filters
 */

public class Simulator<L, O> {

    private BipartiteGraph<L> graph = new BipartiteGraph<L>();

    /**
     * @return the simulators BipartiteGraph
     */
    public BipartiteGraph<L> getGraph() {
        return this.graph;
    }

    /**
     * @modifies This.
     * @effects Simulate a single step for each node in the graph invokes each
     *          node.data simulate function.
     */
    public void simulate() {
        Collection<L> whiteLabels = this.graph.getNodes(true); // white
        Collection<L> blackLabels = this.graph.getNodes(false); // black

        for (L nodeLabel : whiteLabels) {
            Simulatable<L> pipe = (Simulatable<L>) this.graph.getNodeData(nodeLabel);
            pipe.simulate(this.graph);
        }

        for (L nodeLabel : blackLabels) {
            Simulatable<L> filter = (Simulatable<L>) this.graph.getNodeData(nodeLabel);
            filter.simulate(this.graph);
        }
    }
}
