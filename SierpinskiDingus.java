
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public class SierpinskiDingus extends Dingus {
    
    protected int radius;
    protected Coordinate center;
    protected Color inColor;

    public SierpinskiDingus(int maxX, int maxY) {
        super(maxX, maxY);

        // Get the equilateral triangle with center (x, y) and random radius
        center = new Coordinate(x, y);
        radius = 1 + random.nextInt(maxX/4);
        inColor = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), color.getAlpha());
    }

    // Set an equilateral triangle described using KochLines
    ArrayList<Coordinate> getTriangle(Coordinate center, int radius) {
        // Calculate three points on the biggest equilateral triangle possible
        // In the imaginary circle with given center and radius
        Coordinate left = new Coordinate(
            (int) (center.x - (2/3f) * Math.sqrt(3) * radius),
            center.y + radius
        );
        Coordinate right = new Coordinate(
            (int) (center.x + (2/3f) * Math.sqrt(3) * radius), 
            center.y + radius
        );
        Coordinate top = new Coordinate(center.x, center.y - radius);

        // Add the points to an array and return it
        ArrayList<Coordinate> points = new ArrayList<Coordinate>();
        points.add(left);
        points.add(right);
        points.add(top);
        return points;
    }

    // Draw a triangle between the given three points
    void drawTriangle(Graphics g, Coordinate point1, Coordinate point2, Coordinate point3, Color color) {
        g.setColor(color);
        int[] x = {point1.x, point2.x, point3.x};
        int[] y = {point1.y, point2.y, point3.y};
        g.fillPolygon(x, y, 3);
    }

    // Recursively draw Sierpinski's  triangle
    void triangle(Graphics g, Coordinate center, int rad) {
        // Get the points of the equilateral triangle
        ArrayList<Coordinate> points = getTriangle(center, rad);
        Coordinate left = points.get(0);
        Coordinate right = points.get(1);
        Coordinate top = points.get(2);

        // Draw the triangle background on first iteration
        if(rad == radius) {
            drawTriangle(g, left, right, top, color);
        }

        // Draw triangles when the current radius is small enough, else iterate again
        if(rad < 20) {
            drawTriangle(g, left, right, top, inColor);
        } else {
            rad /= 2;

            // Get the three points halfway closer to the center
            top.y += rad;
            left.x += rad * (2/3f) * Math.sqrt(3);
            left.y -= rad;
            right.x -= rad * (2/3f) * Math.sqrt(3);
            right.y -= rad;

            // Call the function on the points and the smaller radius
            triangle(g, left, rad);
            triangle(g, right, rad);
            triangle(g, top, rad);
        }
    }

    @Override
    void draw(Graphics g) {
        // Set the color and draw the triangle
        g.setColor(color);
        triangle(g, center, radius);
    }
}
