
/**
 * PlantDingus -- part of HA RandomArtist
 * example of a PlantDingus
 * @author huub
 */

import java.awt.Graphics;
import java.util.ArrayList;

class PlantDingus extends Dingus {

    // Axiom is the starting point of the L-system and angle the angle increment
    final static int angle = 20;
    final static String axiom = "F";

    // Rules use Turtle graphics alphabet (Ff+-[])
    final static Rule[] rules = {
        new Rule('F', "F[+F+F]F[-F-F]F", 34),
        new Rule('F', "F[+F+F]F", 33),
        new Rule('F', "F[-F-F]F", 33)
    };

    // Length of the lines drawn and current generation
    protected int length;
    protected String current;

    public PlantDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);

        // Pick a semi random length
        length = 150 + random.nextInt(maxY/2);
        current = axiom;

        // Calculate new generations
        int maxGen = 3 + random.nextInt(3);
        for(int i = 0; i < maxGen; i++) {
            generate();
        }
    }

    // Calculate a new generation
    void generate() {
        // Since we're using big String values, use a StringBuffer for performance boost
        StringBuffer nextGen = new StringBuffer();

        // Loop over every character in the current generation
        for(int i = 0; i < current.length(); i++) {
            char chr = current.charAt(i);
            int probability = random.nextInt(100) + 1;
            boolean added = false;

            // Check the character if it applies to any of the rules
            for(Rule rule : rules) {
                char letter = rule.letter;
                if(chr != letter) continue;

                // Check which random rule applies
                int chance = rule.chance;
                if(probability <= chance) {
                    // Rule found, add the production to the new generation
                    nextGen.append(rule.production);
                    added = true;
                    break;
                }

                // Decrease probability with chance for the next rules
                probability -= chance;
            }

            // If there's no rule applied, add the character
            if(!added) nextGen.append(chr);
        }

        // Update generation and halve the length
        current = nextGen.toString();
        length /= 2;
    }

    // Draw the Plant in given matrix with given length as step size
    void drawPlant(Graphics matrix, int length) {
        // This will keep track of all the saved states
        ArrayList<State> states = new ArrayList<State>(0);

        // Use a vector so it the line to be drawn can be rotated and save the current state
        Vector line = new Vector(0, length);
        states.add(new State(matrix, line));

        // Loop over every letter in the current generation
        for(int i = 0; i < current.length(); i++) {
            char chr = current.charAt(i);
            switch(chr) {
                case 'F':
                    // Move forward and draw a line
                    matrix.drawLine(0, 0, line.x, -line.y);
                    matrix.translate(line.x, -line.y);
                    break;
                case 'f':
                    // Move forward
                    matrix.translate(0, -length);
                    break;
                case '+':
                    // Rotate left
                    line.rotate(angle);
                    break;
                case '-':
                    // Rotate right
                    line.rotate(-angle);
                    break;
                case '[':
                    // Save the current state
                    states.add(new State(matrix, line));
                    break;
                case ']':
                    // Restore the previous state
                    State state = states.get(states.size() - 1);
                    matrix = state.matrix;
                    line = state.line;

                    // Remove the previous state from the saved states
                    states.remove(states.size() - 1);
                    break;
            }
        }
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        Graphics matrix = g.create();

        // Set the matrix' origin to the drawing's x and y and then draw the plant
        matrix.translate(x, y + length/2);
        drawPlant(matrix, length);
    }
}

class State {

    public Graphics matrix;
    public Vector line;

    State(Graphics matrix, Vector line) {
        this.matrix = matrix.create();
        this.line = line.copy();
    }
}

class Rule {
    
    public char letter;
    public String production;
    public int chance;

    Rule(char letter, String production, int chance) {
        this.letter = letter;
        this.production = production;
        this.chance = chance;
    }
}