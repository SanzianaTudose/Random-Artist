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

    // Array of the image's filenames
    final static String[] files = {"dog.jpeg", "pippi.png", "sanziana.jpg",};

    // Variables used to load the image
    protected BufferedImage image;
    protected File imageFile;
    protected boolean loaded;

    // Properties of the loaded image
    protected int width;
    protected int height;

    // Scaling for images of different dimensions
    protected double size;
    protected double scaleX;
    protected double scaleY;

    // Pixel class of the instance color
    protected Pixel colorPixel;

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

            // Convert the color to a Pixel class
            colorPixel = new Pixel(color.getRGB());
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    // Get the average color value of the neighbouring pixels and itself
    Pixel kernelAverage(int centerX, int centerY) {
        int colors = 0;
        Pixel totalColor = new Pixel(0);

        // For every pixel in the kernel
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                int tempX = centerX + i;
                int tempY = centerY + j;

                // If it's in bounds, add the pixel's color values to the total
                if(tempX >= 0 && tempX < width && tempY >= 0 && tempY < height) {
                    Pixel tempPixel = new Pixel(image.getRGB(tempX, tempY));
                    totalColor.addPixel(tempPixel);
                    colors++;
                }
            }
        }

        // Divide total by the number of neighbours
        totalColor.multiplyChannels(1f / colors);
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

            // Calculate the average color value and factor in the instance color
            Pixel pixel = kernelAverage(randX, randY);
            pixel.addPixel(colorPixel);
            pixel.multiplyChannels(0.5);

            // Draw an ellipse at the random position with the new color
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

    void multiplyChannels(double amount) {
        a = (int) (amount * a);
        r = (int) (amount * r);
        g = (int) (amount * g);
        b = (int) (amount * b);
    }

    int toInt() {
        return (a << 24) | (r << 18) | (g << 18) | b;
    }
}