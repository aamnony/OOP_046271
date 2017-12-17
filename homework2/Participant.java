package homework2;

import java.util.ArrayList;
import java.util.Iterator;

public class Participant implements Simulatable<String> {

    private final double fee;
    ArrayList<Transaction> storageBuffer;
    ArrayList<Transaction> workingBuffer;
    private String label;

    /**
     * @requires fee >= 0, label != null
     * @modifies this
     * @effects constructs a new Participant
     */
    public Participant(double fee, String label) {
        this.fee = fee;
        this.label = label;
        storageBuffer = new ArrayList<Transaction>();
        workingBuffer = new ArrayList<Transaction>();
    }

    /**
     * @modifies this, graph
     * @effects Simulates this, goes over all transactions that are
     *          currently in this.workingBuffer and transfers them to 
     *          this.storageBuffer or takes a fee in the amount this.fee
     *          and transfer them to the channel connected to it.
     *          will not transfer transaction if channel is full.
     */
    @Override
    public void simulate(BipartiteGraph<String> graph) {
        String participantLabel = graph.getChildren(this.label).get(0); // we know there is only one child 
        Channel C = (Channel) graph.getNodeData(participantLabel);
        
        Iterator<Transaction> iterator = workingBuffer.iterator();
        while(iterator.hasNext())
        {
            Transaction T = iterator.next();
            Transaction newTransaction = new Transaction(T.getDest(), T.getValue() - this.fee);

            if(T.getDest().equals(this.label)) {
                iterator.remove();
                this.storageBuffer.add(T);
            }
            else if(C.canReceiveTransaction(newTransaction)) {
                iterator.remove();
                C.receiveTransaction(newTransaction);
                Transaction feeTransaction = new Transaction(this.label, this.fee);
                this.storageBuffer.add(feeTransaction);
            }
        }
           
    }
    
    /**
     * @modifies this.workingBuffer
     * @effects adds a transaction to the working queue.
     *          will be handled on the next call to simulate.
     */
    public boolean receiveTransaction(Transaction newTransaction) {
        workingBuffer.add(newTransaction);
        return true;
    }

    /**
     * @return the sum of all transaction targeting this Participant.
     *         including transactions created by transaction fees.
     */
    public double getBalance(){
        double sum = 0;
        for(Transaction T : this.storageBuffer) {
            sum = sum + T.getValue();
        }
        return sum;
    }
}
