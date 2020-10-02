/**
 * CircleDingus -- part of HA RandomArtist
 * example of a very simple Dingus
 * @author huub
 */

import java.awt.Graphics;

class CircleDingus extends Dingus {
    protected int radius;
    protected boolean filled; //true: filled, false: outline

    public CircleDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);
        // initialize randomly the CircleDingus properties, i.e., radius and filledness
        radius = random.nextInt(maxX / 4);
        filled = random.nextBoolean();
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        if (filled)
            g.fillArc(x, y, radius, radius,0, 360);
        else
            g.drawArc(x, y, radius, radius,0, 360);
    }
}
