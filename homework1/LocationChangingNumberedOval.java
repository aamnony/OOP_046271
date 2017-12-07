package homework1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class LocationChangingNumberedOval extends LocationChangingOval {

    // Abstraction Function:
    // Represents a 2D numbered oval, the first instance of this class will
    // be numbered 1, each consecutive instance will advance that counter.
    // the oval changes its location using this.step(). its bounding rectangle's
    // top left corner is located at this.location, and its color is this.color.
    // The size of the oval is set via this.setSize(Dimension).
    // its velocity is randomized upon creation
    // but can be modified with this.setVelocity(int velocityX, int velocityY)

    // Representation Invariant:
    // this class doesnt have any special fields therefore doesnt require CheckRep.

    /**
     * Contains the number of {@code LocationChangingNumberedOval}s in the program.
     */
    private static int NUM_OVALS = 0;

    private int number;

    /**
     * @effects Initializes this with a a given location and color. Each of the
     *          horizontal and vertical velocities of the new object is set to a
     *          random integral value i such that -5 <= i <= 5 and i != 0
     */
    public LocationChangingNumberedOval(Point location, Color color) {
        super(location, color);
        NUM_OVALS++;
        this.number = NUM_OVALS;
    }

    /**
     * @modifies g
     * @effects Draws this onto g.
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(Color.BLACK);
        Point middle = getMiddle();
        g.drawString(String.valueOf(number), middle.x, middle.y);
    }

    /**
     * @modifies {@link #NUM_OVALS}.
     * @effects Resets {@code NUM_OVALS} to zero.
     */
    public static void resetOvalsCount() {
        NUM_OVALS = 0;
    }
}
