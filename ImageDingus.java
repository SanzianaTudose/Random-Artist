/**
 * ImageDingus - part of HA RandomArtist
 * Draws an image using elipses based on random pixels 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

class ImageDingus extends Dingus {

    final static String[] files = {"dog.jpeg", "pippi.png", "sanziana.jpg",};
    final static int[][] matrices = {
        {-1, -1},
        {-1, 0},
        {-1, 1},
        {0, -1},
        {0, 0},
        {0, 1},
        {1, -1},
        {1, 0},
        {1, 1}
    };

    protected BufferedImage image;
    protected File imageFile;
    protected boolean loaded;

    protected int width;
    protected int height;

    protected double size;
    protected double scaleX;
    protected double scaleY;

    public ImageDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);

        // Load the image file and initialise properties
        loaded = false;
        try {
            // Load the image
            int index = random.nextInt(files.length);
            String fileName = files[index];
            imageFile = new File("./" + fileName);
            image = ImageIO.read(imageFile);
            loaded = true;

            // Width and height of the image
            width = image.getWidth();
            height = image.getHeight();

            // Use scaling to handle both small and large images
            size = random.nextInt(maxX/2);
            scaleX = Math.max(0.25, size / width);
            scaleY = Math.max(0.25, size / height);
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    // Get the average color value of the neighbouring pixels and itself
    Pixel kernelAverage(int centerX, int centerY) {
        int colors = 0;
        Pixel totalColor = new Pixel(0);

        // For every pixel in the kernel
        for(int[] matrix : matrices) {
            int tempX = centerX + matrix[0];
            int tempY = centerY + matrix[1];

            // If it's in bounds, add the pixel's color values to the total
            if(tempX >= 0 && tempX < width && tempY >= 0 && tempY < height) {
                Pixel tempPixel = new Pixel(image.getRGB(tempX, tempY));
                totalColor.addPixel(tempPixel);
                colors++;
            }
        }

        // Divide total by the number of neighbours
        totalColor.divideChannels(1f / colors);
        return totalColor;
    }

    @Override
    void draw(Graphics g) {
        // Don't draw if the image hasn't been loaded
        if(!loaded) return;

        for(int i = 0; i < 10000; i++) {
            // For every iteration pick a random pixel from the image with random size
            int randX = random.nextInt(width);
            int randY = random.nextInt(height);
            int pixelSize = Math.min(5, random.nextInt(maxY/25)) + 3;

            int offsetX = x + ((int) (scaleX * (randX - pixelSize/2f)));
            int offsetY = y + ((int) (scaleY * (randY - pixelSize/2f)));

            // Calculate the average color value and draw an ellipse at the random position
            Pixel pixel = kernelAverage(randX, randY);
            g.setColor(new Color(pixel.r, pixel.g, pixel.b, pixel.a));
            g.fillArc(offsetX, offsetY, (int) (scaleX * pixelSize), (int) (scaleY * pixelSize), 0, 360);
        }
    }
}


class Pixel {

    public int r;
    public int g;
    public int b;
    public int a;

    Pixel(int value) {
        a = value >> 24 & 0xFF;
        r = value >> 16 & 0xFF;
        g = value >> 8 & 0xFF;
        b = value & 0xFF;
    }

    void addPixel(Pixel pixel) {
        a += pixel.a;
        r += pixel.r;
        g += pixel.g;
        b += pixel.b;
    }

    void divideChannels(double amount) {
        a = (int) (amount * a);
        r = (int) (amount * r);
        g = (int) (amount * g);
        b = (int) (amount * b);
    }

    int toInt() {
        return (a << 24) | (r << 18) | (g << 18) | b;
    }
}