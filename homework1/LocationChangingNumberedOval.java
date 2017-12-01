package homework1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class LocationChangingNumberedOval extends LocationChangingOval {
    private static int NUM_OVALS = 1;
    private int number;
    
    LocationChangingNumberedOval(Point location, Color color) {
        super(location, color);
        this.number = NUM_OVALS;
        NUM_OVALS++;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(new Color(0,0,0)); // Black
        int middle_x = this.getLocation().x + this.dimension.width/2;
        int middle_y = this.getLocation().y + this.dimension.height/2;
        g.drawString(String.valueOf(number), middle_x, middle_y);
    }
}
