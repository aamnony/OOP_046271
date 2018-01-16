package homework4;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Billboard extends JFrame{    
    
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    
    protected final int PanelNum = 25;
    protected Panel[] Panels;
    ColorChangeStrategy strategy;
    
    public Billboard(ColorChangeStrategy _strategy) {
        this.strategy = _strategy;
        JPanel mainPanel = new JPanel();
        getContentPane().add(mainPanel);
        
        mainPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        mainPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        mainPanel.setBackground(Color.WHITE);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
            
        Panels = new Panel[PanelNum];
        for(int i = 0; i < PanelNum; i++) {
            Panels[i] = new Panel(i);
        }
    }
    
    public void UpdatePanels(Color color) {
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
