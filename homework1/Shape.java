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
    // (this.location.getX() >= 0) && (this.location.getY() >= 0)

    /**
     * @effects Checks to see if the representation invariant is being violated.
     * @throws AssertionError
     *             if the representation invariant is violated.
     */
    private void checkRep() {
        assert (this.location != null) : "The location of a Shape cannot be null.";

        assert (this.color != null) : "The color of a Shape cannot be null.";

        assert (this.location.getX() >= 0) : "The X coordinate of the location of a Shape cannot negative.";

        assert (this.location.getY() >= 0) : "The Y coordinate of the location of a Shape cannot negative.";
    }

    /**
     * @effects Initializes this with a given location and color.
     */
    public Shape(Point location, Color color) {
        this.location = (Point) location.clone();
        this.color = color;
        checkRep();
    }

    /**
     * @return the top left corner of the bounding rectangle of this.
     */
    public Point getLocation() {
        checkRep();
        return (Point) location.clone();
    }

    /**
     * @modifies this
     * @effects Moves this to the given location, i.e. this.getLocation() returns
     *          location after call has completed.
     */
    public void setLocation(Point location) {
        checkRep();
        this.location = (Point) location.clone();
        checkRep();
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
        checkRep();
        return getBounds().contains(point);
    }

    /**
     * @return color of this.
     */
    public Color getColor() {
        checkRep();
        return color;
    }

    /**
     * @modifies this
     * @effects Sets color of this.
     */
    public void setColor(Color color) {
        checkRep();
        this.color = color;
        checkRep();
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
        checkRep();
        Shape clonedShape;
        try {
            clonedShape = (Shape) super.clone();
            // super.clone() creates a shallow copy of this, so we need to clone all the
            // mutable fields of the cloned object as well.
            // We set all fields explicitly for better readability (i.e. setColor()).
            clonedShape.setLocation(this.location);
            clonedShape.setColor(this.color);
        } catch (CloneNotSupportedException e) {
            // This should not happen, superclass is Object and does (natively) implement
            // clone().
            throw new InternalError(e);
        }
        checkRep();
        return clonedShape;
    }
}
