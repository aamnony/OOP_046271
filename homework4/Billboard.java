package homework4;

import java.awt.*;
import javax.swing.*;

/**
 * A Billboard object will create a GUI window with 25 Panels in it. it observes
 * ColorGenerator to indicate its Panels need a color update. the Panels will be
 * updated in an order according to ColorChangeStrategy.
 */
public class Billboard extends JFrame {

    private static final int PANELS_IN_ROW = 5;
    private static final int WINDOW_SIZE = 100 * PANELS_IN_ROW;
    private static final int PANELS_COUNT = PANELS_IN_ROW * PANELS_IN_ROW;

    private Panel[] panels;
    private ColorChangeStrategy strategy;

    // Abstraction Function:
    // Represents a Billboard that changes its panels color according
    // to ColorChangeStrategy strategy whenever updatePanels is called.

    // Representation Invariant:
    // (this.strategy != null)

    /**
     * @effects Checks to see if the representation invariant is being violated.
     * @throws AssertionError
     *             if the representation invariant is violated.
     */
    private void checkRep() {
        assert (this.strategy != null) : "strategy cant be null";
    }

    /**
     * @effects Creates a new Billboard with ColorChangeStrategy strategy.
     */
    public Billboard(ColorChangeStrategy strategy) {
        this.strategy = strategy;
        JPanel mainPanel = new JPanel();
        getContentPane().add(mainPanel);

        mainPanel.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        mainPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        mainPanel.setBackground(Color.WHITE);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);

        panels = new Panel[PANELS_COUNT];
        for (int i = 0; i < PANELS_COUNT; i++) {
            int x = i % PANELS_IN_ROW;
            int y = i / PANELS_IN_ROW;
            Point panelLocation = new Point(x, y);
            panels[i] = new Panel(panelLocation, WINDOW_SIZE / PANELS_IN_ROW);
        }

        checkRep();
    }

    /**
     * @effects Updates the Billboards panels color according to its strategy.
     */
    public void updatePanels(Color color) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int[] order = strategy.getColorChangeOrder();
                try {
                    for (int i = 0; i < 25; i++) {
                        int panelIndex = order[i];
                        panels[panelIndex].updateColor(color, getContentPane().getGraphics());
                        Thread.sleep(40);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
