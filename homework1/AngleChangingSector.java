package homework1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

public class AngleChangingSector extends Shape implements Animatable {

    private Random random;
    private int startAngle;
    private int arcAngle;
    private Dimension dimension;
    private int direction;

    // Abstraction Function:
    // Represents a 2D sector of an oval, with random startAngle and arcAngle.
    // arcAngle changes with each call to this.step() by 1 degree, when it
    // reaches 359/0 the angle chances its direction.
    // the sectors bounding rectangle's top left corner is located at this.location,
    // and its color is this.color.
    // The size of the sector is set via this.setSize(Dimension).

    // Representation Invariant:
    // (this.startAngle >= 0) && (this.startAngle <= 359)
    // (this.arcAngle >= 0) && (this.arcAngle <= 359)
    // (this.direction == 1) || (this.direction == -1)

    /**
     * @effects Checks to see if the representation invariant is being violated.
     * @throws AssertionError
     *             if the representation invariant is violated.
     */
    private void checkRep() {
        assert (this.startAngle >= 0 && this.startAngle <= 359) : "invalid startAngle angle";
        
        assert (this.arcAngle >= 0 && this.arcAngle <= 359) : "invalid startAngle angle";
        
        assert (this.direction == 1 && this.direction == -1) : "invalid direction";
    }

    /**
     * @effects Initializes this with a a given location and color. Each of the
     *          horizontal and vertical velocities of the new object is set to a
     *          random integral value i such that -5 <= i <= 5 and i != 0.
     *          Each of the startAngle and arcAngle of the new object is set to a
     *          random integral value i such that 359 <= i <= 0
     */
    public AngleChangingSector(Point location, Color color) {
        super(location, color);
        this.random = new Random();
        this.startAngle = random.nextInt(359);
        this.arcAngle = random.nextInt(359);
        this.direction = 1; // default growing direction
        checkRep();
    }

    /**
     * @modifies this
     * @effects Increases or decreases this.arcAngle by 1 degree.
     *          If this.arcAngle reaches a limit (359 or 0) then
     *          this.direction changes 1->-1 or -1->1.  
     */
    @Override
    public void step(Rectangle bound) {
        checkRep();
        if (this.arcAngle == 359)
            this.direction = -1;
        else if (this.arcAngle == 0) this.direction = 1;

        this.arcAngle = this.arcAngle + this.direction;
        checkRep();
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
        checkRep();
        this.dimension = dimension;
        checkRep();
    }

    /**
     * @return the bounding rectangle of this.
     */
    @Override
    public Rectangle getBounds() {
        checkRep();
        Rectangle r = new Rectangle(dimension);
        return r;
    }

    /**
     * @modifies g
     * @effects Draws this onto g.
     */
    @Override
    public void draw(Graphics g) {
        checkRep();
        int x = this.getLocation().x;
        int y = this.getLocation().y;
        g.setColor(this.getColor());
        g.fillArc(x, y, dimension.width, dimension.height, startAngle, arcAngle);
    }

}
