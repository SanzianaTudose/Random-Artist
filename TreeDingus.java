/**
 * TreeDingus - part of HA RandomArtist
 * a little bit more advanced example of a shape
 * a tree consisting of a trunk (rectangle) and a crown (circle)
 *
 * @author huub
 */

import java.awt.Graphics;
import java.awt.Color;

class TreeDingus extends Dingus {
    private int crownRadius;
    private int trunkHeight;
    private int trunkWidth;
    private boolean filled; // true: filled; false: outline

    public TreeDingus(int maxX, int maxY) {
        // initialize Dingus properties
        super(maxX, maxY);

        // initialize TreeDingus properties
        crownRadius = random.nextInt(maxX / 4); // or something more sophisticated
        trunkHeight = random.nextInt((maxY - 2 * crownRadius) / 2);
        trunkWidth = crownRadius / 3 + 1;
        filled = random.nextBoolean();
    }

    @Override
    void draw(Graphics g) {
        // draw crown
        g.setColor(color);
        if (filled) {
            // more general way to draw an oval than with fillOval (hint :-)
            g.fillArc(x, y, 2*crownRadius, 2*crownRadius, 0, 360);
        } else {
            g.drawArc(x, y, 2*crownRadius, 2*crownRadius, 0, 360);
        }

        // draw trunk
        g.setColor(color);
        int xx = x + crownRadius - trunkWidth/2;
        int yy = y + 2*crownRadius;
        if (filled) {
            g.fillRect(xx, yy, trunkWidth, trunkHeight);
        } else {
            g.fillRect(xx, yy, trunkWidth, trunkHeight);
        }
    }
}
