package homework1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class LocationChangingNumberedOval extends LocationChangingOval {

    /**
     * Contains the number of {@code LocationChangingNumberedOval}s in the program.
     */
    private static int NUM_OVALS = 0;

    private int number;

    public LocationChangingNumberedOval(Point location, Color color) {
        super(location, color);
        NUM_OVALS++;
        this.number = NUM_OVALS;
    }

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
