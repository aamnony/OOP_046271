package homework1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class LocationAndColorChangingTriangle extends ColorAndLocationChangingShape {
    private Dimension dimension;

    public LocationAndColorChangingTriangle(Point location, Color color) {
        super(location, color);
        dimension = null;
    }

    @Override
    public void setSize(Dimension dimension) throws ImpossibleSizeException {
        if (dimension.getHeight() != dimension.getWidth()) {
            double size = dimension.getHeight();
            dimension.setSize(size, size);
            throw new ImpossibleSizeException(dimension);
        }
        this.dimension = dimension;
    }

    @Override
    public Rectangle getBounds() {
        Rectangle r = new Rectangle(dimension);
        return r;
    }

    @Override
    public void draw(Graphics g) {
        int[] x = new int[3];
        int[] y = new int[3];
        x[0] = this.getLocation().x;
        y[0] = this.getLocation().y;
        x[1] = x[0] + this.dimension.width;
        y[1] = y[0];
        x[2] = x[0];
        y[2] = y[0] + this.dimension.height;
        g.setColor(this.getColor());
        g.fillPolygon(x, y, 3);
    }

}
