package homework2;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimulatorTest {

    private SimulatorTestDriver driver;
    
    @Test
    public void simpleTest() {
        driver = new SimulatorTestDriver();
        driver.createSimulator("sim");
        driver.addChannel("sim", "c0", 100);
        driver.addChannel("sim", "c1", 50);
        driver.addChannel("sim", "c2", 20);
        driver.addParticipant("sim", "p1", 1);
        driver.addParticipant("sim", "p2", 1);
        driver.addParticipant("sim", "p3", 1);
        driver.addEdge("sim", "c0", "p1", "c0p1");
        driver.addEdge("sim", "p1", "c1", "p1c1");
        driver.addEdge("sim", "c1", "p2", "c1p2");
        driver.addEdge("sim", "p2", "c2", "p2c2");
        driver.addEdge("sim", "c2", "p3", "c2p3");
        Transaction t3 = new Transaction("p3", 10);
        Transaction t2 = new Transaction("p2", 10);
        driver.sendTransaction("sim", "c0", t3);

        assertEquals("c0", "Dest: p3 Value: 10.0 ", driver.listContents("sim", "c0"));
        assertEquals("c1", "", driver.listContents("sim", "c1"));
        assertEquals("c2", "", driver.listContents("sim", "c1"));        
        assertEquals("p1", 0, driver.getParticipantBalace("sim", "p1"), 0);
        assertEquals("p2", 0, driver.getParticipantBalace("sim", "p2"), 0);
        assertEquals("p3", 0, driver.getParticipantBalace("sim", "p3"), 0);
        
        driver.simulate("sim");
        driver.sendTransaction("sim", "c0", t2);
        assertEquals("c0", "Dest: p2 Value: 10.0 ", driver.listContents("sim", "c0"));
        assertEquals("c1", "Dest: p3 Value: 9.0 ", driver.listContents("sim", "c1"));
        assertEquals("c2", "", driver.listContents("sim", "c2"));
        assertEquals("p1", 1, driver.getParticipantBalace("sim", "p1"), 0);
        assertEquals("p2", 0, driver.getParticipantBalace("sim", "p2"), 0);
        assertEquals("p3", 0, driver.getParticipantBalace("sim", "p3"), 0);
        
        driver.simulate("sim");
        assertEquals("c0","", driver.listContents("sim", "c0"));
        assertEquals("c1", "Dest: p2 Value: 9.0 ", driver.listContents("sim", "c1"));
        assertEquals("c2", "Dest: p3 Value: 8.0 ", driver.listContents("sim", "c2"));
        assertEquals("p1", 2, driver.getParticipantBalace("sim", "p1"), 0);
        assertEquals("p2", 1, driver.getParticipantBalace("sim", "p2"), 0);
        assertEquals("p3", 0, driver.getParticipantBalace("sim", "p3"), 0);
        
        driver.simulate("sim");
        assertEquals("c0","", driver.listContents("sim", "c0"));
        assertEquals("c1", "", driver.listContents("sim", "c1"));
        assertEquals("c2", "", driver.listContents("sim", "c2"));
        assertEquals("p1", 2, driver.getParticipantBalace("sim", "p1"), 0);
        assertEquals("p2", 10, driver.getParticipantBalace("sim", "p2"), 0);
        assertEquals("p3", 8, driver.getParticipantBalace("sim", "p3"), 0);
    }
    
    @Test
    public void congestionTest() {
        driver = new SimulatorTestDriver();
        driver.createSimulator("sim");
        driver.addChannel("sim", "c1", 100);
        driver.addChannel("sim", "c2", 10);
        driver.addParticipant("sim", "p1", 0);
        driver.addParticipant("sim", "p2", 0);
        driver.addEdge("sim", "c1", "p1", "c1p1");
        driver.addEdge("sim", "p1", "c2", "p1c2");
        driver.addEdge("sim", "c2", "p2", "c2p2");
        Transaction t = new Transaction("p2", 10);
        Transaction t2 = new Transaction("p2", 10);
        driver.sendTransaction("sim", "c1", t);
        driver.sendTransaction("sim", "c1", t2);
        
        assertEquals("c1","Dest: p2 Value: 10.0 Dest: p2 Value: 10.0 ", driver.listContents("sim", "c1"));
        assertEquals("c2","", driver.listContents("sim", "c2"));
        assertEquals("p1", 0, driver.getParticipantBalace("sim", "p1"), 0);
        assertEquals("p2", 0, driver.getParticipantBalace("sim", "p2"), 0);
        
        driver.simulate("sim");
        assertEquals("c1","", driver.listContents("sim", "c1"));
        assertEquals("c2","Dest: p2 Value: 10.0 ", driver.listContents("sim", "c2"));
        assertEquals("p1", 0, driver.getParticipantBalace("sim", "p1"), 0);
        assertEquals("p2", 0, driver.getParticipantBalace("sim", "p2"), 0);

        driver.simulate("sim");
        assertEquals("c1","", driver.listContents("sim", "c1"));
        assertEquals("c2","Dest: p2 Value: 10.0 ", driver.listContents("sim", "c2"));
        assertEquals("p1", 0, driver.getParticipantBalace("sim", "p1"), 0);
        assertEquals("p2", 10, driver.getParticipantBalace("sim", "p2"), 0);
        
        driver.simulate("sim");
        assertEquals("c1","", driver.listContents("sim", "c1"));
        assertEquals("c2","", driver.listContents("sim", "c2"));
        assertEquals("p1", 0, driver.getParticipantBalace("sim", "p1"), 0);
        assertEquals("p2", 20, driver.getParticipantBalace("sim", "p2"), 0);
    }

}
