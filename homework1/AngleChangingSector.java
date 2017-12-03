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

    public AngleChangingSector(Point location, Color color) {
        super(location, color);
        this.random = new Random();
        this.startAngle = random.nextInt(359);
        this.arcAngle = random.nextInt(359);
        this.direction = 1; // default growing direction
    }

    @Override
    public void step(Rectangle bound) {
        if (this.arcAngle == 359)
            this.direction = -1;
        else if (this.arcAngle == 0) this.direction = 1;

        this.arcAngle = this.arcAngle + this.direction;
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
        g.fillArc(x, y, dimension.width, dimension.height, startAngle, arcAngle);
    }

}
