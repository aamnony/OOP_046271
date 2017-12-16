package homework2;

import java.util.List;

import hw0.Coin;

/**
 * This class implements simulation of pipes and filters
 */

public class Simulator<L,O> {

    private BipartiteGraph<L> G = new BipartiteGraph<L>();
    
    /**
     * @return the simulators BipartiteGraph
     */
    public BipartiteGraph<L> getGraph(){
        return this.G;
    }
    
    /**
     * @modifies This.
     * @effects Simulate a single step for each node in the graph
     *          invokes each node.data simulate function.
     */
    public void simulate() {
        List<L> Whitelabels = (List<L>) this.G.getNodes(true); // white
        List<L> Blacklabels = (List<L>) this.G.getNodes(false); // black
        
        for (L nodeLabel : Whitelabels) {
            Simulatable<L> pipe = (Simulatable<L>) this.G.getNodeData(nodeLabel);
            pipe.simulate(this.G);
        }
        
        for (L nodeLabel : Blacklabels) {
            Simulatable<L> filter = (Simulatable<L>) this.G.getNodeData(nodeLabel);
            filter.simulate(this.G);
        }
    }
}
