/**
 * BroccoliDingus - part of HA RandomArtist
 * Fractal tree shape
 */

import java.awt.Graphics;

class BroccoliDingus extends Dingus {

    protected int angle;
    protected int size;

    public BroccoliDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);

        // Initialize properties
        angle = random.nextInt(40);
        size = random.nextInt(maxY/4);
    }

    void branch(Graphics matrix, int size, int rot) {
        // Draw the branch and translate the matrix to the branchs's endpoint
        Vector line = new Vector(0, size);
        line.rotate(rot);
        matrix.drawLine(0, 0, line.x, -line.y);
        matrix.translate(line.x, -line.y);

        size *= 0.7;
        if(size > 2) {
            // If size is big enough, create two new branches each at a different angle
            Graphics newMatrix = matrix.create();
            branch(newMatrix, size, rot + angle);

            newMatrix = matrix.create();
            branch(newMatrix, size, rot - angle);
        }
    }

    @Override
    void draw(Graphics g) {
        Graphics matrix = g.create();
        matrix.setColor(color);
        matrix.translate(x, y + size/2);
        branch(matrix, size, 0);
    }
}
