
import java.awt.Graphics;
import java.util.ArrayList;

public class SnowflakeDingus extends Dingus {
    
    protected int radius;
    protected ArrayList<KochLine> kochLines;

    public SnowflakeDingus(int maxX, int maxY) {
        super(maxX, maxY);

        // Get the equilateral triangle with center (x, y) and random radius
        radius = 1 + random.nextInt(maxX/4);
        getTriangle(new Coordinate(x, y), radius);

        // Calculate new generations for the KochLines based on the radius' size
        for(int gen = 0; gen <= Math.min(radius/20 + 1, 5); gen++) {
            generate();
        }
    }

    // Set an equilateral triangle described using KochLines
    void getTriangle(Coordinate center, int radius) {
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

        // Add the KochLines between the Coordinates in clockwise order
        kochLines = new ArrayList<KochLine>();
        kochLines.add(new KochLine(left, top));
        kochLines.add(new KochLine(top, right));
        kochLines.add(new KochLine(right, left));
    }

    // Generates a new generation for each KochLine
    void generate() {
        // Add all new KochLines to nextGen, and later assign this to kochLines
        ArrayList<KochLine> nextGen = new ArrayList<KochLine>();

        // A KochLine's next gen can be divided up in four new segments, with the middle segments rotated upward
        // Here every point between segments has an assigned name, with B left from D and C as the center
        for(KochLine line : kochLines) {
            Vector lineVector = new Vector(line.start, line.end);
            lineVector.scale(1/3f);

            // Point B is the start plus the segment vector
            Vector pointB = new Vector(line.start);
            pointB.add(lineVector);

            // Point C is point B plus the segment vector rotated upward
            Vector pointC = pointB.copy();
            lineVector.rotate(60);
            pointC.add(lineVector);

            // Point D is point C plus the segment vector rotated downward (twice)
            Vector pointD = pointC.copy();
            lineVector.rotate(-120);
            pointD.add(lineVector);

            // Add the KochLines of every segment to the new generation
            // Vector is a subclass of Coordinate so it can directly be passed to the KochLine constructor
            nextGen.add(new KochLine(line.start, pointB));
            nextGen.add(new KochLine(pointB, pointC));
            nextGen.add(new KochLine(pointC, pointD));
            nextGen.add(new KochLine(pointD, line.end));
        }
        kochLines = nextGen;
    }

    @Override
    void draw(Graphics g) {
        // Set the color and draw each KochLine
        g.setColor(color);
        for(KochLine line : kochLines) {
            line.draw(g);
        }
    }
}

class KochLine {
    public Coordinate start;
    public Coordinate end;

    KochLine(Coordinate startPoint, Coordinate endPoint) {
        this.start = startPoint;
        this.end = endPoint;
    }

    void draw(Graphics g) {
        g.drawLine(start.x, start.y, end.x, end.y);
    }
}

class Coordinate {
    public int x;
    public int y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Vector extends Coordinate {

    Vector(int x, int y) {
        super(x, y);
    }
    Vector(Coordinate destination) {
        super(destination.x, destination.y);
    }
    Vector(Coordinate start, Coordinate end) {
        super(end.x - start.x, end.y - start.y);
    }

    void add(Vector vec) {
        x += vec.x;
        y += vec.y;
    }

    void scale(double amount) {
        x = (int) (x * amount);
        y = (int) (y * amount);
    }

    void rotate(int degrees) {
        // Rotate counter-clockwise
        double radians = -Math.PI * degrees / 180;

        // Calculate new x and y values of the vector
        int oldX = x;
        int oldY = y;
        x = (int) (Math.cos(radians) * oldX - Math.sin(radians) * oldY);
        y = (int) (Math.sin(radians) * oldX + Math.cos(radians) * oldY);
    }

    Vector copy() {
        return new Vector(x, y);
    }
}