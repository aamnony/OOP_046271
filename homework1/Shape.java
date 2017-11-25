package homework1;

import java.awt.*;

/**
 * A Shape is an abstraction of a shape object. A typical Shape consists of a
 * set of properties: {location, color, shape, size}. Shapes are mutable and
 * cloneable.
 */
public abstract class Shape implements Cloneable {

    private Point location;
    private Color color;

    // Abstraction Function:
    // Represents a 2D geometric shape, where its bounding rectangle's top left
    // corner is located at this.location, and its color is this.color.
    // The size of the geometric shape is dependent on the type of the shape (e.g.
    // triangle, rectangle, oval) and is set via this.setSize(Dimension).

    // Representation Invariant:
    // (this.location != null) && (this.color != null)
    // (this.location.x >= 0) && (this.location.y >= 0)

    /**
     * @effects Initializes this with a given location and color.
     */
    public Shape(Point location, Color color) {
        setLocation(location);
        setColor(color);
    }

    /**
     * @return the top left corner of the bounding rectangle of this.
     */
    public Point getLocation() {
        return (Point) location.clone();
    }

    /**
     * @modifies this
     * @effects Moves this to the given location, i.e. this.getLocation() returns
     *          location after call has completed.
     */
    public void setLocation(Point location) {
        this.location = (Point) location.clone();
    }

    /**
     * @modifies this
     * @effects Resizes this so that its bounding rectangle has the specified
     *          dimension. If this cannot be resized to the specified dimension =>
     *          this is not modified, throws ImpossibleSizeException (the exception
     *          suggests an alternative dimension that is supported by this).
     */
    public abstract void setSize(Dimension dimension) throws ImpossibleSizeException;

    /**
     * @return the bounding rectangle of this.
     */
    public abstract Rectangle getBounds();

    /**
     * @return true if the given point lies inside the bounding rectangle of this
     *         and false otherwise.
     */
    public boolean contains(Point point) {
        return getBounds().contains(point);
    }

    /**
     * @return color of this.
     */
    public Color getColor() {
        return color;
    }

    /**
     * @modifies this
     * @effects Sets color of this.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @modifies g
     * @effects Draws this onto g.
     */
    public abstract void draw(Graphics g);

    /**
     * @effects Creates and returns a copy of this.
     */
    public Object clone() {
        Shape clonedShape;
        try {
            clonedShape = (Shape) super.clone();
            // super.clone() creates a shallow copy of this, so we need to clone all the
            // mutable fields of the cloned object as well.
            clonedShape.setLocation(clonedShape.location);
            return clonedShape;
        } catch (CloneNotSupportedException e) {
            // This should not happen, superclass is Object and does (natively) implement
            // clone().
            throw new InternalError(e);
        }
    }
}
