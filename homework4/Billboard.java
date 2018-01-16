package homework4;

import java.awt.*;
import javax.swing.*;

/**
 * A Billboard object will create a GUI window with 25 Panels in it.
 * it observes ColorGenerator to indicate its Panels need a color update.
 * the Panels with be updated in an order according to ColorChangeStrategy.
 */
public class Billboard extends JFrame{    
    
    private static final int PANELS_IN_ROW = 5;
    private static final int WINDOW_SIZE = 100 * PANELS_IN_ROW;

    protected final int PanelNum = PANELS_IN_ROW*PANELS_IN_ROW;
    protected Panel[] Panels;
    ColorChangeStrategy strategy;
    
    // Abstraction Function:
    // Represents a Billboard that changes its panels color according
    // to ColorChangeStrategy strategy whenever UpdatePanels is called.

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
    public Billboard(ColorChangeStrategy _strategy){
        this.strategy = _strategy;
        JPanel mainPanel = new JPanel();
        getContentPane().add(mainPanel);
        
        mainPanel.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        mainPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        mainPanel.setBackground(Color.WHITE);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
            
        Panels = new Panel[PanelNum];
        for(int index = 0; index < PanelNum; index++) {
            int x = index % PANELS_IN_ROW;
            int y = index / PANELS_IN_ROW;
            Point panelLocation = new Point(x,y);
            Panels[index] = new Panel(panelLocation, WINDOW_SIZE/PANELS_IN_ROW);
        }
        
        checkRep();
    }
    
    /**
     * @effects Updates the Billboards panels color
     *          according to its strategy.
     */
    public void UpdatePanels(Color color){
        int[] order = strategy.GetColorChangeOrder();
        try {
            for(int i = 0; i < 25; i++) {
                int PanelIndex = order[i];
                Panels[PanelIndex].UpdateColor(color, getContentPane().getGraphics());
                Thread.sleep(40);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
