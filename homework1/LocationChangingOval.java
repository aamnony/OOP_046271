package homework1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class LocationChangingOval extends LocationChangingShape {
    private Dimension dimension;

    // Abstraction Function:
    // Represents a 2D oval, changes its location using this.step().
    // where its bounding rectangle's top left
    // corner is located at this.location, and its color is this.color.
    // The size of the geometric shape is set via this.setSize(Dimension).
    // its velocity is randomized upon creation
    // but can be modified with this.setVelocity(int velocityX, int velocityY)
    
    // Representation Invariant:
    // this class doesnt have any special fields therefore doesnt require CheckRep.
    
    LocationChangingOval(Point location, Color color) {
        super(location, color);
    }

    @Override
    public void setSize(Dimension dimension) throws ImpossibleSizeException {
        this.dimension = dimension;

    }

    @Override
    public Rectangle getBounds() {
        Rectangle r = new Rectangle(dimension);
        return r;
    }

    @Override
    public void draw(Graphics g) {
        int x = this.getLocation().x;
        int y = this.getLocation().y;
        g.setColor(this.getColor());
        g.fillOval(x, y, dimension.width, dimension.height);
    }

    
    public Point getMiddle() {
        int middleX = this.getLocation().x + this.dimension.width / 2;
        int middleY = this.getLocation().y + this.dimension.height / 2;
        return new Point(middleX, middleY);
    }

}
