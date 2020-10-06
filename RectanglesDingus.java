/*
* Rectangles in a gradient 
*
*/

import java.awt.Graphics;
import java.awt.Color;

public class RectanglesDingus extends Dingus{
    protected boolean filled; //true: filled, false: outline
    protected int numRect;
    protected int width, height;

    public RectanglesDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties
        super(maxX, maxY);

        // initialize randomly the RectanglesDingus properties
        filled = random.nextBoolean();
        numRect = 5 + random.nextInt(10);
        width = maxX / 10 + random.nextInt(maxX / 3);
        height = maxY / 10 + random.nextInt(maxX / 3);
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        
        for (int i = 0; i < numRect; i++) {
            int curStep = 100 * i / numRect;
            
            // TODO: make gradient?
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            if (filled) {
                g.fillRect(x + curStep, y + curStep, width - curStep, height - curStep);
            } else {
                g.drawRect(x + curStep, y + curStep, width - curStep, height - curStep);
            }
        }
    }
    
}
