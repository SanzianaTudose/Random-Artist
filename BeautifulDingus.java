/**
 * BeautifulDingus - part of HA RandomArtist
 * Concentric circles with 
 * smaller random circles on their outline
 */

import java.awt.Graphics;
import java.awt.Color;

public class BeautifulDingus extends Dingus {
    private int bigRadius;
    private int centerX, centerY;
    private int numBigCircles;
    private Color myColor;

    public BeautifulDingus(int maxX, int maxY) {
        // initialize Dingus properties
        super(maxX, maxY);

        // initialize BeautifulDingus properties
        bigRadius = 50 + random.nextInt(maxX / 4);
        centerX = x + bigRadius / 2;
        centerY = y + bigRadius / 2;
        numBigCircles = 3 + random.nextInt(bigRadius / 10);
        myColor = color;
    }

    // Draws points on the outline of the circle with center at (circleX, circleY) and radius circleRadius
    void drawPointsOnCircle(Graphics g, int circleX, int circleY, int circleRadius) {
        // Determine properties of the smaller circles
        int numCircles = 4 + random.nextInt(5);
        int smallRadius = circleRadius / (3 + random.nextInt(3));
        
        for (int i = 0; i <= numCircles; i++) { 
            double angle = 2 * Math.PI * i / numCircles;
            int smallX = (int) (Math.round(circleX + circleRadius * Math.cos(angle) / 2)); 
            int smallY = (int) (Math.round(circleY + circleRadius * Math.sin(angle) / 2)); 
            
            // Draw a smaller circle
            g.setColor(myColor);
            g.fillOval(smallX - smallRadius / 2, smallY - smallRadius / 2, smallRadius, smallRadius);
            
            // Draw an outline for that circle
            g.setColor(myColor.darker());
            g.drawOval(smallX - smallRadius / 2, smallY - smallRadius / 2, smallRadius, smallRadius);
        }
    } 

    // Returns a color {percentage}% brigther than initColor
    Color brigtherColor(Color initColor, double percentage) {
        int red = initColor.getRed();
        int green = initColor.getGreen();
        int blue = initColor.getBlue();

        red = (int) Math.round(Math.min(255, red + red * percentage));
        green = (int) Math.round(Math.min(255, green + green * percentage));
        blue = (int) Math.round(Math.min(255, blue + blue * percentage));

        return new Color(red, green, blue);
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
       
        // Draw the main circle
        g.drawOval(x, y, bigRadius, bigRadius); 

        // Draw each of the other concentric circles
        for (int i = 0; i < numBigCircles; i++) {
            int curRadius = bigRadius - i * (20 + random.nextInt(5));

            myColor = brigtherColor(myColor, 0.15f);
            drawPointsOnCircle(g, centerX, centerY, curRadius);
        }
    }
}
