package homework1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class LocationChangingOval extends LocationChangingShape{
    protected Dimension dimension;

    LocationChangingOval(Point location, Color color) {
        super(location, color);
    }

    @Override
    public void setSize(Dimension dimension) throws ImpossibleSizeException {
        this.dimension= dimension;
        
    }

    @Override
    public Rectangle getBounds() {
        Rectangle r = new Rectangle(dimension);
        return r;
    }

    @Override
    public void draw(Graphics g) {
        int x = this.getLocation().x;
        int y = this.getLocation().y; 
        g.setColor(this.getColor());
        g.fillOval(x, y, dimension.width, dimension.height);
    }

}
