package homework1;

import java.awt.*;
import java.util.Random;

/**
 * A LocationChaningShape is a Shape that can change its location using its
 * step() method. A LocationChaningShape has a velocity property that determines
 * the speed of location changing. Thus, a typical LocationChaningShape consists
 * of the following set of properties: {location, color, shape, size, velocity}
 */
public abstract class LocationChangingShape extends Shape implements Animatable {

    private Random random;
    private int velocityX, velocityY;

    // TODO (BOM): Write Abstraction Function

    /**
     * @effects Checks to see if the representation invariant is being violated.
     * @throws AssertionError
     *             if the representation invariant is violated.
     */
    private void checkRep() {
        assert (this.velocityX > 5 || this.velocityX < -5
                || this.velocityX == 0) : "The X velocity of the LocationChangingShape has to be between -5 and 5 and not 0.";

        assert (this.velocityY > 5 || this.velocityY < -5
                || this.velocityY == 0) : "The X velocity of the LocationChangingShape has to be between -5 and 5 and not 0.";
    }

    private int getRandomVeclocity() {
        int randVal;
        do {
            randVal = random.nextInt(10) - 5;
        } while (randVal == 0);
        return randVal;
    }

    /**
     * @effects Initializes this with a a given location and color. Each of the
     *          horizontal and vertical velocities of the new object is set to a
     *          random integral value i such that -5 <= i <= 5 and i != 0
     */
    public LocationChangingShape(Point location, Color color) {
        super(location, color);
        this.random = new Random();
        this.velocityX = getRandomVeclocity();
        this.velocityY = getRandomVeclocity();
        checkRep();
    }

    /**
     * @return the horizontal velocity of this.
     */
    public int getVelocityX() {
        checkRep();
        return velocityX;

    }

    /**
     * @return the vertical velocity of this.
     */
    public int getVelocityY() {
        checkRep();
        return velocityY;
    }

    /**
     * @modifies this
     * @effects Sets the horizontal velocity of this to velocityX and the vertical
     *          velocity of this to velocityY.
     */
    public void setVelocity(int velocityX, int velocityY) {
        checkRep();
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        checkRep();
    }

    /**
     * @modifies this
     * @effects Let p = location v = (vx, vy) = velocity r = the bounding rectangle
     *          of this If (part of r is outside bound) or (r is within bound but
     *          adding v to p would bring part of r outside bound) { If adding v to
     *          p would move r horizontally farther away from the center of bound,
     *          vx = -vx If adding v to p would move r vertically farther away from
     *          the center of bound, vy = -vy } p = p + v
     */
    public void step(Rectangle bound) {
        checkRep();
        double boundX = bound.getCenterX();
        double boundY = bound.getCenterY();
        Point location = this.getLocation();
        Point newLocation = this.getLocation();
        newLocation.translate(velocityX, velocityY);
        Rectangle shape = this.getBounds();
        shape.setLocation(newLocation);

        // if(!bound.contains(shape)) {
        if (shape.getMinX() < bound.getMinX() || shape.getMaxX() > bound.getMaxX()) {
            if (Math.abs(boundX - location.getX()) < Math.abs(boundX - newLocation.getX())) velocityX = -velocityX;
        }

        if (shape.getMinY() < bound.getMinY() || shape.getMaxY() > bound.getMaxY()) {
            if (Math.abs(boundY - location.getY()) < Math.abs(boundY - newLocation.getY())) velocityY = -velocityY;
        }

        location.translate(velocityX, velocityY);
        this.setLocation(location);
        checkRep();
    }
}
