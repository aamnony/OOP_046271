package homework2.test;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.Collection;
import java.util.Random;
import javax.swing.*;

public class GraphPanel extends JComponent {

    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 1000;
    private static final int NODE_RADIUS = 35;
    private static final int EDGE_ARROW_SIZE = 6;

    private static final Random RND = new Random();

    private Point mousePt = new Point(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
    private Rectangle mouseRect = new Rectangle();

    private boolean selecting = false;

    private ColoredGraph<String> graph = new ColoredGraph<>();

    public GraphPanel() {
        this.setOpaque(true);
        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseMotionHandler());
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0x00f0f0f0));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setFont(g.getFont().deriveFont(20));
        drawNodes(g, false);
        drawNodes(g, true);
        drawEdges(g);

        if (selecting) {
            g.setColor(Color.darkGray);
            g.drawRect(mouseRect.x, mouseRect.y, mouseRect.width, mouseRect.height);
        }
    }

    protected void buildGraph() {

        int m = RND.nextInt(7) + 1;
        for (int i = 0; i < m; i++) {
            graph.addNode(String.valueOf(i), rndNodeLocation(), false);
        }
        for (int i = 0; i < 1 + RND.nextInt(m); i++) {
            graph.addEdge(String.valueOf(i), String.valueOf(1 + RND.nextInt(m)), String.valueOf(RND.nextGaussian()));
        }
        /*
         * graph.addNode("w1", rndNodeLocation(), true); graph.addNode("w2",
         * rndNodeLocation(), true); graph.addNode("w3", rndNodeLocation(), true);
         * graph.addNode("b1", rndNodeLocation(), false); graph.addNode("b2",
         * rndNodeLocation(), false); graph.addNode("b3", rndNodeLocation(), false);
         * 
         * graph.addEdge("w1", "b2", "w1-b2"); graph.addEdge("b3", "w1", "b3-w1");
         * graph.addEdge("b2", "w2", "b2-w2"); graph.addEdge("b3", "w2", "b3-w2");
         * graph.addEdge("b3", "w3", "b3-w3"); graph.addEdge("w1", "b1", "w1-b1");
         * graph.addEdge("b2", "w1", "b2-w1"); graph.addEdge("w3", "b2", "w3-b2");
         * graph.addEdge("w3", "b1", "w3-b1"); graph.addEdge("b1", "w2", "b1-w2");
         */
    }

    private void drawEdges(Graphics g) {
        g.setColor(Color.RED);

        for (String node : graph.getNodes()) {
            NodeData nd1 = (NodeData) graph.getNodeData(node);

            Collection<String> edges = graph.getOutgoingEdges(node);
            for (String edge : edges) {
                String child = graph.getChildByEdgeLabel(node, edge);
                NodeData nd2 = (NodeData) graph.getNodeData(child);

                drawArrow(g, nd1.p, nd2.p, EDGE_ARROW_SIZE);
                g.drawString(edge, (nd1.p.x + nd2.p.x) / 2, (nd1.p.y + nd2.p.y) / 2);
            }

            if (nd1.selected) {
                g.setColor(Color.darkGray);
                g.drawRect(nd1.b.x, nd1.b.y, nd1.b.width, nd1.b.height);
            }
        }
    }

    private void drawNodes(Graphics g, boolean isWhite) {
        for (String node : graph.getNodes(isWhite)) {
            NodeData nd = (NodeData) graph.getNodeData(node);

            g.setColor(isWhite ? Color.WHITE : Color.BLACK);
            g.fillOval(nd.b.x, nd.b.y, nd.b.width, nd.b.height);

            g.setColor(isWhite ? Color.BLACK : Color.WHITE);
            g.drawString(node, nd.p.x, nd.p.y);

            g.setColor(Color.BLACK);
            g.drawOval(nd.b.x, nd.b.y, nd.b.width, nd.b.height);
        }

    }

    private NodeData rndNodeLocation() {
        return new NodeData(RND, getWidth(), getHeight());
    }

    /**
     * Select no nodes.
     */
    public void selectNone() {
        for (String node : graph.getNodes()) {
            NodeData nd = (NodeData) graph.getNodeData(node);
            nd.selected = false;
        }
    }

    private String lastSelected;

    /**
     * Select a single node; return true if not already selected.
     */
    public boolean selectOne(Point p) {
        for (String node : graph.getNodes()) {
            NodeData nd = (NodeData) graph.getNodeData(node);
            if (nd.b.contains(p)) {
                if (!nd.selected) {
                    selectNone();
                    nd.selected = true;
                }
                lastSelected = node;
                return true;
            }
        }
        lastSelected = null;
        return false;
    }

    /**
     * Select each node in r.
     */
    public void selectRect(Rectangle r) {
        for (String node : graph.getNodes()) {
            NodeData nd = (NodeData) graph.getNodeData(node);
            nd.selected = r.contains(nd.p);
        }
    }

    /**
     * Toggle selected state of each node containing p.
     */
    public void selectToggle(Point p) {
        for (String node : graph.getNodes()) {
            NodeData nd = (NodeData) graph.getNodeData(node);
            if (nd.b.contains(p)) {
                nd.selected = !nd.selected;
            }
        }
    }

    /**
     * Update each node's position by d (delta).
     */
    public void updatePosition(Point d) {
        for (String node : graph.getNodes()) {
            NodeData nd = (NodeData) graph.getNodeData(node);
            if (nd.selected) {
                nd.p.x += d.x;
                nd.p.y += d.y;
                nd.updateBounds();
            }
        }
    }

    private static void drawArrow(Graphics g1, Point p1, Point p2, int arrowSize) {
        Graphics2D g = (Graphics2D) g1.create();

        double x1 = p1.x;
        double y1 = p1.y;
        double x2 = p2.x;
        double y2 = p2.y;

        double dx = x2 - x1;
        double dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        x1 = x1 + NODE_RADIUS * Math.cos(angle);
        y1 = y1 + NODE_RADIUS * Math.sin(angle);
        dx = x2 - x1;
        dy = y2 - y1;
        int len = (int) (Math.sqrt(dx * dx + dy * dy) - NODE_RADIUS);

        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);
        g.fillPolygon(new int[] { len, len - arrowSize, len - arrowSize, len },
                new int[] { 0, -arrowSize, arrowSize, 0 }, 4);
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            selecting = false;
            mouseRect.setBounds(0, 0, 0, 0);
            e.getComponent().repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePt = e.getPoint();
            if (e.isShiftDown()) {
                selectOne(mousePt);
                if (lastSelected != null) {
                    
                    JFrame f = new JFrame(GraphPanel.this.title + "->" + lastSelected);
                    // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    GraphPanel gp = new GraphPanel();
                    f.add(new JScrollPane(gp), BorderLayout.CENTER);
                    f.pack();
                    f.setLocationByPlatform(true);
                    f.setVisible(true);
                    gp.buildGraph();
                }

            } else if (e.isControlDown()) {
                selectToggle(mousePt);
            } else if (e.isPopupTrigger()) {
                selectOne(mousePt);
            } else if (selectOne(mousePt)) {
                selecting = false;
            } else {
                selectNone();
                selecting = true;
            }
            e.getComponent().repaint();
        }
    }

    private class MouseMotionHandler extends MouseMotionAdapter {

        private Point delta = new Point();

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selecting) {
                mouseRect.setBounds(Math.min(mousePt.x, e.getX()), Math.min(mousePt.y, e.getY()),
                        Math.abs(mousePt.x - e.getX()), Math.abs(mousePt.y - e.getY()));
                selectRect(mouseRect);
            } else {
                delta.setLocation(e.getX() - mousePt.x, e.getY() - mousePt.y);
                updatePosition(delta);
                mousePt = e.getPoint();
            }
            e.getComponent().repaint();
        }
    }

    private static class NodeData {

        private int r;
        private Point p;
        private Rectangle b = new Rectangle();
        private boolean selected = false;

        NodeData(Random rnd, int w, int h) {
            r = NODE_RADIUS;
            p = new Point(randomInt(rnd, r, w - r), randomInt(rnd, r, h - r));
            updateBounds();
        }

        private void updateBounds() {
            b.setBounds(p.x - r, p.y - r, 2 * r, 2 * r);
        }

        /**
         * @return a pseudorandom {@code int} value between {@code lowerBound}
         *         (inclusive) and the {@code upperBound} (exclusive).
         */
        private static int randomInt(Random rnd, int lowerBound, int upperBound) {
            return lowerBound + rnd.nextInt(upperBound - lowerBound);
        }
    }

    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                JFrame f = new JFrame("");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GraphPanel gp = new GraphPanel();
                f.add(new JScrollPane(gp), BorderLayout.CENTER);
                f.pack();
                f.setLocationByPlatform(true);
                f.setVisible(true);
                gp.buildGraph();
            }
        });
    }
}
