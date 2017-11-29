package homework1.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import homework1.ImpossibleSizeException;
import homework1.Shape;

public final class RectTestShape extends Shape {

    private Dimension size;

    public RectTestShape(Point location, Color color) {
        super(location, color);
    }

    @Override
    public void setSize(Dimension dimension) throws ImpossibleSizeException {
        if (dimension.width <= 0 || dimension.height <= 0) {
            int w = 1 + Math.abs(dimension.width);
            int h = 1 + Math.abs(dimension.height);
            Dimension alternativeDimension = new Dimension(w, h);

            throw new ImpossibleSizeException(alternativeDimension);
        }

        size = dimension;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getLocation(), size);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getLocation().x, getLocation().y, size.width, size.height);

    }

}
