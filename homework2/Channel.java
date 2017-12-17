package homework2;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author USER
 *
 */
public class Channel implements Simulatable<String> {

    private final double limit;
    private double total;
    ArrayList<Transaction> workingBuffer;
    private String label;

    /**
     * @requires limit >= 0, label != null
     * @modifies this
     * @effects constructs a new Channel
     */
    public Channel(double limit, String label) {
        this.limit = limit;
        this.label = label;
        this.total = 0;
        workingBuffer = new ArrayList<Transaction>();
    }

    /**
     * @modifies this, graph
     * @effects Simulates this, goes over all transactions that are currently in
     *          this.workingBuffer and transfers them to participant connected to
     *          it.
     */
    @Override
    public void simulate(BipartiteGraph<String> graph) {
        String participantLabel = graph.getChildren(this.label).iterator().next(); // we know there is only one child
        Participant p = (Participant) graph.getNodeData(participantLabel);

        Iterator<Transaction> iterator = workingBuffer.iterator();
        while (iterator.hasNext()) {
            Transaction t = iterator.next();
            p.receiveTransaction(t);
            iterator.remove();
        }
    }

    /**
     * @modifies this.workingBuffer
     * @effects adds a transaction to the working queue. will be handled on the next
     *          call to simulate.
     */
    public void receiveTransaction(Transaction newTransaction) {
        if (this.total + newTransaction.getValue() < limit) {
            throw new IlligalTransactionException();
        }
        workingBuffer.add(newTransaction);
    }

    /**
     * @return true if the given transaction can be received. false if the channel
     *         will overflow given the transaction.
     */
    public boolean canReceiveTransaction(Transaction newTransaction) {
        if (this.total + newTransaction.getValue() < limit) {
            return true;
        }
        return false;
    }

    /**
     * @return a string describing all the transactions currently in this channel.
     */
    public String getWorkingBufferString() {
        StringBuffer str = new StringBuffer();
        for (Transaction t : this.workingBuffer) {
            str.append(t.toString());
            str.append(" ");
        }
        return str.toString();
    }
}
