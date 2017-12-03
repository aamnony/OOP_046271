package homework1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class LocationChangingNumberedOval extends LocationChangingOval {

    /**
     * Contains the number of {@code LocationChangingNumberedOval}s in the program.
     */
    private static int NUM_OVALS = 1;

    private int number;

    LocationChangingNumberedOval(Point location, Color color) {
        super(location, color);
        this.number = NUM_OVALS;
        NUM_OVALS++;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(new Color(0, 0, 0)); // Black
        int middleX = this.getLocation().x + this.dimension.width / 2;
        int middleY = this.getLocation().y + this.dimension.height / 2;
        g.drawString(String.valueOf(number), middleX, middleY);
    }

    /**
     * @modifies {@link #NUM_OVALS}.
     * @effects Resets {@code NUM_OVALS} to zero.
     */
    public static void resetOvalsCount() {
        NUM_OVALS = 0;
    }
}
