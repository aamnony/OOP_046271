package homework2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements a testing driver for Simulator. The driver manages
 * Simulators for payment channels
 */
public class SimulatorTestDriver {

    private Map<String, Simulator<String, Transaction>> simulators;

    /**
     * @modifies this
     * @effects Constructs a new test driver.
     */
    public SimulatorTestDriver() {
        this.simulators = new HashMap<>();
    }

    /**
     * @requires simName != null
     * @modifies this
     * @effects Creates a new simulator named simName. The simulator's graph is
     *          initially empty.
     */
    public void createSimulator(String simName) {
        Simulator<String, Transaction> s = new Simulator<String, Transaction>();
        this.simulators.put(simName, s);
    }

    /**
     * @requires createSimulator(simName) && channelName != null && channelName has
     *           not been used in a previous addChannel() or addParticipant() call
     *           on this object limit > 0
     * @modifies simulator named simName
     * @effects Creates a new Channel named by the String channelName, with a limit,
     *          and add it to the simulator named simName.
     */
    public void addChannel(String simName, String channelName, double limit) {
        Simulator<String, Transaction> s = this.simulators.get(simName);
        Channel c = new Channel(limit, channelName);
        s.getGraph().addNode(channelName, c, true);
    }

    /**
     * @requires createSimulator(simName) && participantName != null &&
     *           participantName has not been used in a previous addParticipant(),
     *           addChannel() call on this object fee > 0
     * @modifies simulator named simName
     * @effects Creates a new Participant named by the String participantName and
     *          add it to the simulator named simName.
     */
    public void addParticipant(String simName, String participantName, double fee) {
        Simulator<String, Transaction> s = this.simulators.get(simName);
        Participant p = new Participant(fee, participantName);
        s.getGraph().addNode(participantName, p, false);
    }

    /**
     * @requires createSimulator(simName) && ((addPipe(parentName) &&
     *           addFilter(childName)) || (addFilter(parentName) &&
     *           addPipe(childName))) && edgeLabel != null && node named parentName
     *           has no other outgoing edge labeled edgeLabel && node named
     *           childName has no other incoming edge labeled edgeLabel
     * @modifies simulator named simName
     * @effects Adds an edge from the node named parentName to the node named
     *          childName in the simulator named simName. The new edge's label is
     *          the String edgeLabel.
     */
    public void addEdge(String simName, String parentName, String childName, String edgeLabel) {
        Simulator<String, Transaction> s = this.simulators.get(simName);
        s.getGraph().addEdge(parentName, childName, edgeLabel);
    }

    /**
     * @requires createSimulator(simName) && addChannel(channelName) A transaction
     *           Transaction != null
     * @modifies channel named channelName
     * @effects pushes the Transaction into the channel named channelName in the
     *          simulator named simName.
     */
    public void sendTransaction(String simName, String channelName, Transaction tx) throws IlligalTransactionException {
        Simulator<String, Transaction> s = this.simulators.get(simName);
        Channel c = (Channel) s.getGraph().getNodeData(channelName);
        c.receiveTransaction(tx);
    }

    /**
     * @requires addChannel(channelName)
     * @return a space-separated list of the Transaction values currently in the
     *         channel named channelName in the simulator named simName.
     */
    public String listContents(String simName, String channelName) {
        Simulator<String, Transaction> s = this.simulators.get(simName);
        Channel c = (Channel) s.getGraph().getNodeData(channelName);
        return c.getWorkingBufferString();
    }

    /**
     * @requires addParticipant(participantName)
     * @return The sum of all Transaction values stored in the storage of the
     *         participant participantName in the simulator simName
     */
    public double getParticipantBalace(String simName, String participantName) {
        Simulator<String, Transaction> s = this.simulators.get(simName);
        Participant p = (Participant) s.getGraph().getNodeData(participantName);
        return p.getBalance();
    }

    /**
     * @requires createSimulator(simName)
     * @modifies simulator named simName
     * @effects runs simulator named simName for a single time slice.
     */
    public void simulate(String simName) {
        Simulator<String, Transaction> s = this.simulators.get(simName);
        s.simulate();
    }

    /**
     * Prints the all edges.
     *
     * @requires simName the sim name
     * @effects Prints the all edges.
     */
    public void printAllEdges(String simName) {
        Simulator<String, Transaction> s = this.simulators.get(simName);
        BipartiteGraph<String> graph = s.getGraph();
        for (String nodeLabel : graph.getNodes()) {
            for (String edgeLabel : graph.getOutgoingEdges(nodeLabel))
                System.out.print(edgeLabel);
        }
    }

}
