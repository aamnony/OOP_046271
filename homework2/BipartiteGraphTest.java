package homework2;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

import javafx.scene.paint.Color;

/**
 * BipartiteGraphTest contains JUnit block-box unit tests for BipartiteGraph.
 */
public class BipartiteGraphTest {

    @Test
    public void testExample() {
        BipartiteGraphTestDriver driver = new BipartiteGraphTestDriver();

        // create a graph
        driver.createGraph("graph1");

        // add a pair of nodes
        driver.addBlackNode("graph1", "n1");
        driver.addWhiteNode("graph1", "n2");

        // add an edge
        driver.addEdge("graph1", "n1", "n2", "edge");

        // check neighbors
        assertEquals("wrong black nodes", "n1", driver.listBlackNodes("graph1"));
        assertEquals("wrong white nodes", "n2", driver.listWhiteNodes("graph1"));
        assertEquals("wrong children", "n2", driver.listChildren("graph1", "n1"));
        assertEquals("wrong children", "", driver.listChildren("graph1", "n2"));
        assertEquals("wrong parents", "", driver.listParents("graph1", "n1"));
        assertEquals("wrong parents", "n1", driver.listParents("graph1", "n2"));
    }

    @Test
    public void anotherGeneralTest() {
        BipartiteGraphTestDriver d = new BipartiteGraphTestDriver();
        d.createGraph("g");
        assertEquals("node is legal but was not addded", true, d.addBlackNode("g", "b1"));
        assertEquals("node is legal but was not addded", true, d.addWhiteNode("g", "w2"));
        assertEquals("node is legal but was not addded", true, d.addWhiteNode("g", "w3"));

        assertEquals("edge is illegal but was addded (same node)", false, d.addEdge("g", "b1", "b1", "b1-b1"));
        assertEquals("edge is illegal but was addded (non-existent node)", false, d.addEdge("g", "b7", "w2", "b7-w2"));

        assertEquals("edge is legal but was not addded", true, d.addEdge("g", "b1", "w2", "b1-w2"));
        assertEquals("edge is legal but was not addded", true, d.addEdge("g", "b1", "w3", "b1-w3"));
        assertEquals("edge is illegal but was addded (same colored nodes)", false, d.addEdge("g", "w2", "w3", "w2-w3"));

        assertEquals("wrong child", "w2", d.getChildByEdgeLabel("g", "b1", "b1-w2"));
        assertEquals("wrong child", "w3", d.getChildByEdgeLabel("g", "b1", "b1-w3"));

    }

    @Test
    public void addingIllegalNodes() {
        BipartiteGraphTestDriver d = new BipartiteGraphTestDriver();
        d.createGraph("g");

        assertEquals("node is legal but was not addded", true, d.addBlackNode("g", "b1"));
        assertEquals("node is legal but was not addded", true, d.addBlackNode("g", "b2"));
        assertEquals("node is legal but was not addded", true, d.addWhiteNode("g", "w1"));
        assertEquals("node is legal but was not addded", true, d.addWhiteNode("g", "w2"));
        assertEquals("node is legal but was not addded", true, d.addWhiteNode("g", "w3"));

        assertEquals("node already exists, but was added", false, d.addBlackNode("g", "b2"));
        for (int i = 1; i <= 3; i++) {
            assertEquals("node already exists, but was added", false, d.addWhiteNode("g", "w" + i));
        }
    }

    @Test
    public void addingIllegalEdges() {
        BipartiteGraphTestDriver d = new BipartiteGraphTestDriver();
        d.createGraph("g");

        assertEquals("node is legal but was not addded", true, d.addBlackNode("g", "b1"));
        assertEquals("node is legal but was not addded", true, d.addBlackNode("g", "b2"));
        assertEquals("node is legal but was not addded", true, d.addWhiteNode("g", "w1"));
        assertEquals("node is legal but was not addded", true, d.addWhiteNode("g", "w2"));
        assertEquals("node is legal but was not addded", true, d.addWhiteNode("g", "w3"));

        assertEquals("edge is illegal but was addded (same node)", false, d.addEdge("g", "w3", "w3", "w3-w3"));
        assertEquals("edge is illegal but was addded (same colored nodes)", false, d.addEdge("g", "b2", "b1", "b2-b1"));

        assertEquals("edge is legal but was not addded", true, d.addEdge("g", "b1", "w1", "b1-w1"));
        assertEquals("edge is legal but was not addded", true, d.addEdge("g", "w1", "b1", "w1-b1"));
        assertEquals("edge is legal but was not addded", true, d.addEdge("g", "b2", "w3", "BW"));

        assertEquals("wrong parents", "w1", d.listParents("g", "b1"));
        assertEquals("wrong parents", "", d.listParents("g", "b2"));
        assertEquals("wrong parents", "b1", d.listParents("g", "w1"));
        assertEquals("wrong parents", "", d.listParents("g", "w2"));
        assertEquals("wrong parents", "b2", d.listParents("g", "w3"));

        assertEquals("wrong children", "w1", d.listChildren("g", "b1"));
        assertEquals("wrong children", "w3", d.listChildren("g", "b2"));
        assertEquals("wrong children", "b1", d.listChildren("g", "w1"));
        assertEquals("wrong children", "", d.listChildren("g", "w2"));
        assertEquals("wrong children", "", d.listChildren("g", "w3"));

        assertEquals("wrong child", "w3", d.getChildByEdgeLabel("g", "b2", "BW"));
    }

    @Test
    public void removingNodes() {
        BipartiteGraphTestDriver d = new BipartiteGraphTestDriver();
        d.createGraph("g");
        assertEquals("node is legal but was not addded", true, d.addBlackNode("g", "b1"));
        assertEquals("node is legal but was not addded", true, d.addBlackNode("g", "b2"));
        assertEquals("node is legal but was not addded", true, d.addWhiteNode("g", "w1"));
        assertEquals("node is legal but was not addded", true, d.addWhiteNode("g", "w2"));
        assertEquals("node is legal but was not addded", true, d.addWhiteNode("g", "w3"));

        assertEquals("wrong black nodes", "b1 b2", d.listBlackNodes("g"));
        assertEquals("wrong white nodes", "w1 w2 w3", d.listWhiteNodes("g"));

        assertEquals("node is illegal but was removed", false, d.removeNode("g", "w77"));
        assertEquals("node is legal but was not removed", true, d.removeNode("g", "w2"));
        assertEquals("node is legal but was not removed", true, d.removeNode("g", "w1"));
        assertEquals("wrong white nodes", "w3", d.listWhiteNodes("g"));
        assertEquals("node is legal but was not removed", true, d.removeNode("g", "w3"));

        assertEquals("wrong black nodes", "b1 b2", d.listBlackNodes("g"));
        assertEquals("wrong white nodes", "", d.listWhiteNodes("g"));
    }

    @Test
    public void removingEdges() {
        BipartiteGraphTestDriver d = new BipartiteGraphTestDriver();
        d.createGraph("g");
        assertEquals("node is legal but was not addded", true, d.addBlackNode("g", "b1"));
        assertEquals("node is legal but was not addded", true, d.addBlackNode("g", "b2"));
        assertEquals("node is legal but was not addded", true, d.addWhiteNode("g", "w1"));
        assertEquals("node is legal but was not addded", true, d.addWhiteNode("g", "w2"));
        assertEquals("node is legal but was not addded", true, d.addWhiteNode("g", "w3"));

        assertEquals("edge is legal but was not addded", true, d.addEdge("g", "b1", "w2", "b1-w2"));
        assertEquals("edge is legal but was not addded", true, d.addEdge("g", "b2", "w2", "b2-w2"));
        assertEquals("edge is legal but was not addded", true, d.addEdge("g", "w3", "b1", "w3-b1"));
        assertEquals("edge is legal but was not addded", true, d.addEdge("g", "w2", "b1", "w2-b1"));

        assertEquals("wrong children", "w2", d.listChildren("g", "b1"));
        assertEquals("wrong children", "w2", d.listChildren("g", "b2"));
        assertEquals("wrong children", "", d.listChildren("g", "w1"));
        assertEquals("wrong children", "b1", d.listChildren("g", "w3"));
        assertEquals("wrong children", "b1", d.listChildren("g", "w2"));

        assertEquals("edge is legal but was not removed", true, d.removeEdge("g", "b1", "w2", "b1-w2"));
        assertEquals("edge is illegal but was removed", false, d.removeEdge("g", "b1", "w2", "b1-w2"));
        assertEquals("edge is legal but was not removed", true, d.removeEdge("g", "b2", "w2", "b2-w2"));
        assertEquals("edge is illegal but was removed", false, d.removeEdge("g", "b8", "w2", "b8-w2"));
        assertEquals("edge is legal but was not removed", true, d.removeEdge("g", "w2", "b1", "w2-b1"));

        assertEquals("wrong children", "", d.listChildren("g", "b1"));
        assertEquals("wrong children", "", d.listChildren("g", "b2"));
        assertEquals("wrong children", "", d.listChildren("g", "w1"));
        assertEquals("wrong children", "b1", d.listChildren("g", "w3"));
        assertEquals("wrong children", "", d.listChildren("g", "w2"));
    }

    @Test
    public void changingNodesData() {
        BipartiteGraphTestDriver d = new BipartiteGraphTestDriver();
        d.createGraph("g");
        assertEquals("node is legal but was not addded", true, d.addBlackNode("g", "b1"));
        assertEquals("node is legal but was not addded", true, d.addWhiteNode("g", "w1"));

        assertNull("wrong node data", d.getNodeData("g", "b1"));
        assertNull("wrong node data", d.getNodeData("g", "w1"));

        d.setNodeData("g", "b1", Color.rgb(127, 255, 212));
        d.setNodeData("g", "w1", Locale.forLanguageTag("en"));

        assertEquals("wrong node data", Color.AQUAMARINE, d.getNodeData("g", "b1"));
        assertEquals("wrong node data", Locale.ENGLISH, d.getNodeData("g", "w1"));

        d.setNodeData("g", "w1", null);
        assertNull("wrong node data", d.getNodeData("g", "w1"));
    }
}
