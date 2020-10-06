/*
 * part of HA Random artist
 * TO BE COMPLETED
 *
 * @author huub
 * @author kees
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class Painting extends JPanel implements ActionListener {

   /*---- Randomness ----*/
    /** you can change the variable SEED if you like
     *  the same program with the same SEED will generate exactly the same sequence of pictures
     */
    final static long SEED = 37; // seed for the random number generator; any number works

    /** do not change the following two lines **/
    final static Random random = new Random( SEED ); // random generator to be used by all classes
    int numberOfRegenerates = 0;

   /*---- Screenshots ----
    * do not change
    */
    char current = '0';
    String filename = "randomshot_"; // prefix
    
    /*---- Dinguses ----*/
    int numShapes;
    ArrayList<Dingus> shapes;
    
    public Painting() {
        setPreferredSize(new Dimension(800, 450)); // make panel 800 by 450 pixels.
        
        //...
    }

    @Override
    protected void paintComponent(final Graphics g) { // draw all your shapes
        super.paintComponent(g); // clears the panel
        // draw all shapes
        for (final Dingus shape : shapes) {
            shape.draw(g);
        }
    }

    /**
     * reaction to button press
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        if ("Regenerate".equals(e.getActionCommand())) {
            regenerate();
            repaint();
        } else { // screenshot
            saveScreenshot(this, filename + current++); // ++ to show of compact code :-)
        }
    }

    void regenerate() {
        numberOfRegenerates++; // do not change

        // clear the shapes list
        shapes = new ArrayList<Dingus>();

        // create random shapes
        setBackground(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        numShapes = 20 + random.nextInt(20);

        while (numShapes > 0) {
            int randomShape = random.nextInt(8);
            
            switch (randomShape) {
                case 0:
                    shapes.add(new BroccoliDingus(getWidth(), getHeight()));
                    break;
                case 1:
                    shapes.add(new PlantDingus(getWidth(), getHeight()));
                    break;
                case 2:
                    shapes.add(new BeautifulDingus(getWidth(), getHeight()));
                    break;
                case 3:
                    shapes.add(new CloudDingus(getWidth(), getHeight()));
                    break;
                case 4:
                    shapes.add(new SierpinskiDingus(getWidth(), getHeight()));
                    break;
                case 5:
                    shapes.add(new SnowflakeDingus(getWidth(), getHeight()));
                    break;
                case 6:
                    shapes.add(new RectanglesDingus(getWidth(), getHeight()));
                    break;
                case 7:
                    shapes.add(new ImageDingus(getWidth(), getHeight()));
                    break;
            }
            
            numShapes--;
        }
    }

    /**
     * saves a screenshot of a Component on disk overides existing files
     *
     * @param component - Component to be saved
     * @param name      - filename of the screenshot, followed by a sequence number
     * 
     *                  do not change
     */
    void saveScreenshot(final Component component, final String name) {
        final String randomInfo = "" + SEED + "+" + (numberOfRegenerates - 1); // minus 1 because the initial picture
                                                                               // should not count
        System.out.println(SwingUtilities.isEventDispatchThread());
        final BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        // call the Component's paint method, using
        // the Graphics object of the image.
        final Graphics graphics = image.getGraphics();
        component.paint(graphics); // alternately use .printAll(..)
        graphics.drawString(randomInfo, 0, component.getHeight());

        try {
            ImageIO.write(image, "PNG", new File(name + ".png"));
            System.out.println("Saved screenshot as " + name);
        } catch (final IOException e) {
            System.out.println( "Saving screenshot failed: "+e );
        }
    }
    
}