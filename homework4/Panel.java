package homework4;

import java.awt.Color;
import java.awt.Graphics;

public class Panel implements Observer{

    private Color color;
    private final int PanelIndex;
    private final int size = 100;
    
    public Panel(int index) {
        this.color = new Color(0, 0, 0);
        PanelIndex = index;
    }
    
    private int GetXLocation() {
        return PanelIndex % 5;
    }
    
    private int GetYLocation() {
        return PanelIndex / 5;
    }
    @Override
    public void UpdateColor(Color _color, Graphics g) {
        this.color = _color;
        int x = GetXLocation();
        int y = GetYLocation();
        g.setColor(this.color);
        g.fillRect(x*size, y*size, size, size);
        
    }
    
    public Color GetColor() {
        return this.color;
    }

}
