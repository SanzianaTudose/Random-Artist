
import java.awt.Graphics;
import java.awt.Color;

public class CloudDingus extends Dingus {
    
    protected int radius;
    protected boolean filled;
    protected int maxCircles;
    protected int minCircleSize;

    public CloudDingus(int maxX, int maxY) {
        super(maxX, maxY);

        radius = random.nextInt(maxX/2);
        filled = random.nextBoolean();
        minCircleSize = 10;

        maxCircles = 1;
        int tempRadius = radius;
        while(tempRadius > minCircleSize) {
            maxCircles++;
            tempRadius /= 2;
        }
    }

    int iterationColor(int value, int iteration) {
        return value + (255 - value)/(maxCircles - iteration);
    }

    void drawCircle(Graphics g, int x, int y, int radius, int iteration) {
        g.setColor(color);
        if(filled) {
            int iterRed = iterationColor(color.getRed(), iteration);
            int iterGreen = iterationColor(color.getGreen(), iteration);
            int iterBlue = iterationColor(color.getBlue(), iteration);
            Color iterColor = new Color(iterRed, iterGreen, iterBlue, color.getAlpha());
            g.setColor(iterColor);

            g.fillArc(x, y, radius, radius, 0, 360);
        } else {
            g.drawArc(x, y, radius, radius, 0, 360);
        }
    }

    void drawCloud(Graphics g, int x, int y, int radius, int iteration) {
        if(radius < minCircleSize) return;

        drawCircle(g, x, y, radius, iteration);
        radius /= 2;
        iteration++;
        drawCloud(g, x - radius/2, y + radius/2, radius, iteration);
        drawCloud(g, x + (int) (radius*1.5), y + radius/2, radius, iteration);
    }

    @Override
    void draw(Graphics g) {
        drawCloud(g, x, y, radius, 0);
    }
}
