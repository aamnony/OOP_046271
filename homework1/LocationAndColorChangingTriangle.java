package homework1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class LocationAndColorChangingTriangle extends ColorAndLocationChangingShape {
    private Dimension dimension;

    // Abstraction Function:
    // Represents a 2D triangle, that changes its location using this.step().
    // its bounding rectangle's top left corner is located at this.location,
    // and its color is this.color.
    // The size of the triangle is set via this.setSize(Dimension).
    // its velocity is randomized upon creation
    // but can be modified with this.setVelocity(int velocityX, int velocityY)
    // each time this triangle changes direction it changes its color to a random color.
    
    // Representation Invariant:
    // this class doesnt have any special fields therefore doesnt require CheckRep.

    /**
     * @effects Initializes this with a a given location and color. Each of the
     *          horizontal and vertical velocities of the new object is set to a
     *          random integral value i such that -5 <= i <= 5 and i != 0
     */
    public LocationAndColorChangingTriangle(Point location, Color color) {
        super(location, color);
        dimension = null;
    }

    /**
     * @modifies this
     * @effects Resizes this so that its bounding rectangle has the specified
     *          dimension. If this cannot be resized to the specified dimension =>
     *          this is not modified, throws ImpossibleSizeException (the exception
     *          suggests an alternative dimension that is supported by this).
     */
    @Override
    public void setSize(Dimension dimension) throws ImpossibleSizeException {
        if (dimension.getHeight() != dimension.getWidth()) {
            double size = dimension.getHeight();
            dimension.setSize(size, size);
            throw new ImpossibleSizeException(dimension);
        }
        this.dimension = dimension;
    }

    /**
     * @return the bounding rectangle of this.
     */
    @Override
    public Rectangle getBounds() {
        Rectangle r = new Rectangle(dimension);
        return r;
    }

    /**
     * @modifies g
     * @effects Draws this onto g.
     */
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
