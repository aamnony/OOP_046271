package homework4;

import java.awt.*;

/**
 * A Panel object is a single Panel in a Panel array that will be created by
 * Billboard. its location and size are set at init and its color will be
 * changed periodically with UpdateColor(Color, Graphics).
 */
public class Panel {

    private Color color;
    private final int size;
    private final Point location;

    // Abstraction Function:
    // Represents a single panel in a Billboard, with color this.color
    // at location this.location and size this.size.

    // Representation Invariant:
    // (this.location.x > 0) && (this.location.y > 0)
    // (this.size > 0)
    // (this.color != null)

    /**
     * @effects Checks to see if the representation invariant is being violated.
     * @throws AssertionError
     *             if the representation invariant is violated.
     */
    private void checkRep() {
        assert (this.location.x >= 0) : "x coordinate must be positive";

        assert (this.location.y >= 0) : "y coordinate must be positive";

        assert (this.size >= 0) : "size must be positive";

        assert (this.color != null) : "color cant be null";
    }

    /**
     * @effects Creates a new white Panel at 0 < index < 25
     */
    public Panel(Point location, int size) {
        this.color = Color.WHITE;
        this.location = location;
        this.size = size;
        checkRep();
    }

    /**
     * @effects Updates the color of this panel and redraw it in Graphics g.
     */
    public void updateColor(Color color, Graphics g) {
        checkRep();
        this.color = color;
        g.setColor(this.color);
        g.fillRect(this.location.x * size, this.location.y * size, size, size);
        checkRep();
    }
}
