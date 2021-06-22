package recursion.seirpinski_triangle;

import recursion.Position;

import javax.swing.*;
import java.awt.*;

/*
Generates Seirpinski's Triangle w/ Lines only
 */
public class SeirpinskiLine extends JFrame implements SeirpinskiConstants {
    private int depth;
    private double scale;
    private int mode;

    ///constructors///
    //constructor for default settings
    public SeirpinskiLine() {
        this.depth = DEFAULT_DEPTH;
        this.scale = DEFAULT_SCALE;
        this.mode = DEFAULT_MODE;

        setUpGUI();
        repaint();
    }

    //constructor for custom inputs for depth, scale, and color mode
    public SeirpinskiLine(int depth, double scale, int mode) {
        this.depth = depth;
        this.scale = scale;
        this.mode = mode;

        //assigns default value is inputs aren't in accepted range
        if(depth > 10 || depth < 1) {
            depth = DEFAULT_DEPTH;
        }
        if(scale < 1) {
            scale = DEFAULT_SCALE;
        }
        if(mode < 0 || mode > BACKGROUND.length) {
            mode = DEFAULT_MODE;
        }

        setUpGUI();
        repaint();
    }

    public int side() {
        return (int) (TRIANGLE_SIDE_SIZE * scale);
    }

    public int height() {
        return (int) (TRIANGLE_SIDE_SIZE * scale * TRIANGLE_HEIGHT_RATIO);
    }

    public void setUpGUI() {
        this.setTitle("Seirpinski's Triangle (Line) - " + MODE_NAME[mode] + " - Depth: " + depth + " - Scale: " + scale);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(side() + (X_OFFSET * 2), height() + (Y_OFFSET * 2));
    }

    public void paint(Graphics g) {
        g.setColor(BACKGROUND[mode]);
        g.fillRect(0,0,getWidth(), getHeight());

        Position a = new Position(X_OFFSET, height() + Y_OFFSET);
        Position b = new Position(X_OFFSET + (side() / 2), Y_OFFSET);
        Position c = new Position(X_OFFSET + side(), height() + Y_OFFSET);

        seirpinski(g, a, b, c, depth);
    }

    public void seirpinski(Graphics g, Position a, Position b, Position c, int depth) {
        if(depth > 0) {
            drawTriangle(g, a, b, c);

            seirpinski(g, a, midPoint(a,b), midPoint(a,c), depth - 1);
            seirpinski(g, midPoint(a,b), b, midPoint(b,c), depth - 1);
            seirpinski(g, midPoint(a,c), midPoint(b,c), c, depth - 1);
        }
    }

    public void drawTriangle(Graphics g, Position a, Position b, Position c) {
        ((Graphics2D) g).setStroke(new BasicStroke(LINE_SIZE));

        g.setColor(LINE_1[mode]);
        g.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
        g.setColor(LINE_2[mode]);
        g.drawLine(b.getX(), b.getY(), c.getX(), c.getY());
        g.setColor(LINE_3[mode]);
        g.drawLine(c.getX(), c.getY(), a.getX(), a.getY());
    }

    public Position midPoint(Position p1, Position p2) {
        int newX = (p1.getX() + p2.getX()) / 2;
        int newY = (p1.getY() + p2.getY()) / 2;

        return new Position(newX, newY);
    }
}
