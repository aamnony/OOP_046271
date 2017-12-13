package homework1;

import java.awt.*;
import java.awt.event.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import javax.swing.*;

/**
 * Main application class for exercise #1.<br>
 * This application allows the user to add shapes to a graphical window and to
 * animate them.
 */
@SuppressWarnings("serial")
public class Animator extends JFrame implements ActionListener {

    // Preferred frame width and height.
    private static final int WINDOW_WIDTH = 600;
    private static final int MAXIMUM_SHAPE_WIDTH = (3 * WINDOW_WIDTH) / 10;
    private static final int MINIMUM_SHAPE_WIDTH = WINDOW_WIDTH / 10;
    private static final int WINDOW_HEIGHT = 400;
    private static final int MAXIMUM_SHAPE_HEIGHT = (3 * WINDOW_HEIGHT) / 10;
    private static final int MINIMUM_SHAPE_HEIGHT = WINDOW_HEIGHT / 10;
    private static final int MINIMUM_ANGLE = 0;
    private static final int MAXIMUM_ANGLE = 359;

    // Graphical components
    private JMenuBar menuBar;
    private JMenu fileMenu, insertMenu, helpMenu;
    private JMenuItem newItem, exitItem, triangleItem, ovalItem, numberedOvalItem, sectorItem, aboutItem;
    private JCheckBoxMenuItem animationCheckItem;
    private JPanel mainPanel;

    // Shapes that have been added to this.
    private Collection<Shape> shapes = new HashSet<>();

    private Random random = new Random();

    /**
     * @modifies this
     * @effects Initializes the GUI and enables a timer that steps animation of all
     *          shapes in this 25 times per second while animation checkbox is
     *          selected.
     */
    public Animator() {
        super("Animator");

        // Create main panel and menubar.
        mainPanel = (JPanel) createMainPanel();
        getContentPane().add(mainPanel);
        menuBar = (JMenuBar) createMenuBar();
        setJMenuBar(menuBar);

        // Enable animation timer (ticks 25 times per second).
        Timer timer = new Timer(40, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (animationCheckItem.isSelected()) {

                    for (Iterator<Shape> iterator = shapes.iterator(); iterator.hasNext();) {
                        Shape shape = (Shape) iterator.next();
                        if (shape instanceof Animatable) {
                            ((Animatable) shape).step(mainPanel.getBounds());
                        }
                    }

                    // Make sure that the shapes are redrawn.
                    repaint();
                }
            }
        });
        timer.start();
    }

    /**
     * @return main GUI panel.
     */
    private JComponent createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        mainPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        mainPanel.setBackground(Color.WHITE);

        return mainPanel;
    }

    /**
     * @return main GUI menubar.
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        newItem = new JMenuItem("New");
        newItem.addActionListener(this);
        fileMenu.add(newItem);
        animationCheckItem = new JCheckBoxMenuItem("Animation");
        fileMenu.add(animationCheckItem);
        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        insertMenu = new JMenu("Insert");
        triangleItem = new JMenuItem("Triangle");
        triangleItem.addActionListener(this);
        insertMenu.add(triangleItem);
        ovalItem = new JMenuItem("Oval");
        ovalItem.addActionListener(this);
        insertMenu.add(ovalItem);
        numberedOvalItem = new JMenuItem("Numbered Oval");
        numberedOvalItem.addActionListener(this);
        insertMenu.add(numberedOvalItem);
        sectorItem = new JMenuItem("Sector");
        sectorItem.addActionListener(this);
        insertMenu.add(sectorItem);
        menuBar.add(insertMenu);

        helpMenu = new JMenu("Help");
        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this);
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);

        return menuBar;
    }

    /**
     * @modifies g
     * @effects Paint this including all its shapes to g. This method is invoked by
     *          Swing to draw components. It should not be invoked directly, but the
     *          repaint method should be used instead in order to schedule the
     *          component for redrawing.
     */
    public void paint(Graphics g) {
        super.paint(g);

        for (Iterator<Shape> iterator = shapes.iterator(); iterator.hasNext();) {
            Shape shape = (Shape) iterator.next();
            shape.draw(getContentPane().getGraphics());
        }

    }

    /**
     * @modifies this
     * @effects Invoked when the user selects an action from the menubar and
     *          performs the appropriate operation.
     */
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());

        // File->New : clear all shapes
        if (source.equals(newItem)) {
            shapes.clear();
            repaint();

            LocationChangingNumberedOval.resetOvalsCount();
        }

        // File->Exit: close application
        else if (source.equals(exitItem)) {
            dispose();
        }

        // Insert a shape
        else if ((source.equals(triangleItem)) || (source.equals(ovalItem)) || (source.equals(numberedOvalItem))
                || (source.equals(sectorItem))) {

            Color randomShapeColor = new Color(random.nextInt());

            int randomShapeX = random.nextInt(WINDOW_WIDTH - MAXIMUM_SHAPE_WIDTH);
            int randomShapeY = random.nextInt(WINDOW_HEIGHT - MAXIMUM_SHAPE_HEIGHT);
            Point randomShapeLocation = new Point(randomShapeX, randomShapeY);

            int randomShapeWidth = randomInt(MINIMUM_SHAPE_WIDTH, MAXIMUM_SHAPE_WIDTH);
            int randomShapeHeight = randomInt(MINIMUM_SHAPE_HEIGHT, MAXIMUM_SHAPE_HEIGHT);
            Dimension randomShapeSize = new Dimension(randomShapeWidth, randomShapeHeight);

            Shape newShape;
            if (source.equals(triangleItem)) {
                newShape = new LocationAndColorChangingTriangle(randomShapeLocation, randomShapeColor);
            } else if (source.equals(ovalItem)) {
                newShape = new LocationChangingOval(randomShapeLocation, randomShapeColor);
            } else if (source.equals(numberedOvalItem)) {
                newShape = new LocationChangingNumberedOval(randomShapeLocation, randomShapeColor);
            } else /* if (source.equals(sectorItem)) */ {
                int startAngle = randomInt(MINIMUM_ANGLE, MAXIMUM_ANGLE); 
                int arcAngle = randomInt(MINIMUM_ANGLE, MAXIMUM_ANGLE);
                newShape = new AngleChangingSector(randomShapeLocation, randomShapeColor, startAngle, arcAngle); 
            }

            try {
                newShape.setSize(randomShapeSize);
            } catch (ImpossibleSizeException ise) {
                randomShapeSize = ise.getAlternativeDimension();
                newShape.setSize(randomShapeSize);
            }
            shapes.add(newShape);

            repaint();
        }

        // Help->About : show about message dialog
        else if (source.equals(aboutItem)) {
            JOptionPane.showMessageDialog(this, "Animator - 1st" + " homework assignment", "About",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * @requires {@code upperBound} > {@code lowerBound}.
     * @param lowerBound
     *            The lower bound (inclusive) of the returned {@code int}.
     * @param upperBound
     *            The upper bound (exclusive) of the returned {@code int}.
     * @return a pseudorandom {@code int} value between {@code lowerBound}
     *         (inclusive) and the {@code upperBound} (exclusive).
     */
    private int randomInt(int lowerBound, int upperBound) {
        return lowerBound + random.nextInt(upperBound - lowerBound);
    }

    /**
     * @effects Animator application.
     */
    public static void main(String[] args) {
        Animator application = new Animator();

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setResizable(false);
        application.pack();
        application.setVisible(true);
    }
}
