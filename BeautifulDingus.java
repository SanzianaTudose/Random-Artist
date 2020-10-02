/**
 * BeautifulDingus - part of HA RandomArtist
 * a little bit more advanced example of a shape
 * a tree consisting of a trunk (rectangle) and a crown (circle)
 *
 * @author Sanziana Tudose 
 * studentid: 1561057
 */

import java.awt.Graphics;
import java.awt.Color;

public class BeautifulDingus extends Dingus {
    final int minLength = 100;
    int width, height;
    boolean filledOvals;
    Color myColor;


    public BeautifulDingus(int maxX, int maxY) {
         // initialize Dingus properties
         super(maxX, maxY);

         // initialize BeautifulDingus properties
         width = minLength + random.nextInt(maxX - minLength);
         height = minLength + random.nextInt(maxY - minLength);
         filledOvals = random.nextBoolean();

         myColor = color;
    }

    //Color changeColor(Color arg) {
      //  return new Color(arg.getRed() * random.nextDouble(), arg.getGreen() * random.nextDouble(), arg.getBlue() * random.nextDouble());
    //}

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        g.drawRoundRect(x, y, width, height, random.nextInt(width), random.nextInt(height));
        
        if (filledOvals) {
            myColor = myColor.brighter();
            g.setColor(myColor);
            g.fillArc(x - width / (1+random.nextInt(30)), y - height / (1+random.nextInt(30)), width / 2, height /2, 0, 360);

            myColor = myColor.brighter();
            g.setColor(myColor);
            g.fillArc(x + width / (1+random.nextInt(30)), y + height /  (1+random.nextInt(30)), width / 2, height /2, 0, 360);

            myColor = myColor.brighter();
            g.setColor(myColor);
            g.fillArc(x + width / (1+random.nextInt(30)), y - height / (1+random.nextInt(30)), width / 2, height /2, 0, 360);
       
            myColor = myColor.brighter();
            g.setColor(myColor);
            g.fillArc(x - width / (1+random.nextInt(30)), y + height / (1+random.nextInt(30)), width / 2, height /2, 0, 360);
        }
        else {
            g.drawArc(x - width / 4, y - height / 4, width / 2, height /2, 0, 360);
            g.drawArc(x + width / 4, y + height / 4, width / 2, height /2, 0, 360);
            g.drawArc(x + width / 4, y - height / 4, width / 2, height /2, 0, 360);
            g.drawArc(x - width / 4, y + height / 4, width / 2, height /2, 0, 360);
        }


    /*    g.setColor(color);
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
        }*/
    }
}
