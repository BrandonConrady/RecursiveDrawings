package recursion.seirpinski_triangle;

import recursion.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
Generates Seirpinski's Triangle, filling in triangles
 */
public class SeirpinskiFill extends JFrame implements SeirpinskiConstants {
    private int depth;
    private double scale;
    private boolean extraTriangles;
    private boolean spinningColors;
    private final Position A, B, C, CENTROID;
    private Position a, b, c;
    private Position[] rotations;

    private int count = 0;

    ///constructors///
    //constructor for default settings
    public SeirpinskiFill() {
        this.depth = DEFAULT_DEPTH;
        this.scale = DEFAULT_SCALE;
        this.extraTriangles = false;
        this.spinningColors = false;

        A = new Position(X_OFFSET, height() + Y_OFFSET);
        B = new Position(X_OFFSET + (side() / 2), Y_OFFSET);
        C = new Position(X_OFFSET + side(), height() + Y_OFFSET);
        a = new Position(A.getX(), A.getY());
        b = new Position(B.getX(), B.getY());
        c = new Position(C.getX(), C.getY());
        CENTROID = centroid();

        calculateRotations();

        setUpGUI();
        repaint();
    }

    //constructor for custom inputs for depth, scale, and color mode
    public SeirpinskiFill(int depth, double scale, boolean extraTriangles, boolean spinningColors) {
        this.depth = depth;
        this.scale = scale;
        this.extraTriangles = extraTriangles;
        this.spinningColors = spinningColors;

        //assigns default value is inputs aren't in accepted range
        if(depth > 10 || depth < 1) {
            depth = DEFAULT_DEPTH;
        }
        if(scale < 1) {
            scale = DEFAULT_SCALE;
        }

        A = new Position(X_OFFSET, height() + Y_OFFSET);
        B = new Position(X_OFFSET + (side() / 2), Y_OFFSET);
        C = new Position(X_OFFSET + side(), height() + Y_OFFSET);
        a = new Position(A.getX(), A.getY());
        b = new Position(B.getX(), B.getY());
        c = new Position(C.getX(), C.getY());
        CENTROID = centroid();

        calculateRotations();

        setUpGUI();
        repaint();
        if(spinningColors) {
            startAnimation();
        }
    }

    public void calculateRotations() {
        rotations = new Position[30];

        Position tempA = new Position(A.getX(), A.getY()).shiftedPosition(-CENTROID.getX(), -CENTROID.getY());
        Position tempB = new Position(B.getX(), B.getY()).shiftedPosition(-CENTROID.getX(), -CENTROID.getY());
        Position tempC = new Position(C.getX(), C.getY()).shiftedPosition(-CENTROID.getX(), -CENTROID.getY());

        for(int i = 0; i <= 9; i++) {
            rotations[i] = (tempA.rotation(ROTATION * i)).shiftedPosition(CENTROID.getX(), CENTROID.getY());
            rotations[i + 10] = (tempB.rotation(ROTATION * i)).shiftedPosition(CENTROID.getX(), CENTROID.getY());
            rotations[i + 20] = (tempC.rotation(ROTATION * i)).shiftedPosition(CENTROID.getX(), CENTROID.getY());
        }
    }

    public int side() {
        return (int) (TRIANGLE_SIDE_SIZE * scale);
    }

    public int height() {
        return (int) (TRIANGLE_SIDE_SIZE * scale * TRIANGLE_HEIGHT_RATIO);
    }

    public void setUpGUI() {
        this.setTitle("Seirpinski's Triangle - Mode: Fill - Depth: " + depth + " - Scale: " + scale);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(side() + (X_OFFSET * 2), height() + (Y_OFFSET * 2));
    }

    public void startAnimation() {
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotatePoints(count % 30);
                count++;
                repaint();
            }
        });
        timer.start();
    }

    public void paint(Graphics g) {
        g.setColor(BACKGROUND[1]);
        g.fillRect(0,0,getWidth(), getHeight());

        seirpinski(g, A, B, C, depth);
    }

    public void seirpinski(Graphics g, Position x, Position y, Position z, int depth) {
        if(depth > 0) {
            Position xy = midPoint(x, y);
            Position yz = midPoint(y, z);
            Position xz = midPoint(x, z);

            seirpinski(g, x, xy, xz, depth - 1);
            seirpinski(g, xy, y, yz, depth - 1);
            seirpinski(g, xz, yz, z, depth - 1);

            if(extraTriangles) {
                Position x2 = midPoint(xy, xz);
                Position y2 = midPoint(xy, yz);
                Position z2 = midPoint(yz, xz);

                seirpinski(g, x2, y2, z2, depth - 1);
            }
        } else if (depth == 0) {
            fillTriangle(g, x, y, z, new Color(red(y), green(x), blue(z)));
        }
    }

    public int red(Position p) {
        return (int) (255 * (1 - bound(distance(p, b) / side())));
    }

    public int green(Position p) {
        return (int) (255 * (1 - bound(distance(p, a) / side())));
    }

    public int blue(Position p) {
        return (int) (255 * (1 - bound(distance(p, c) / side())));
    }

    //if value is above 1 or below 0, sets them to 1 or 0 respectively
    public double bound(double value) {
        if(value > 1) {
            return 1;
        }
        else if(value < 0) {
            return 0;
        }
        else {
            return value;
        }
    }

    public double distance(Position p1, Position p2) {
        int dx = p1.getX() - p2.getX();
        int dy = p1.getY() - p2.getY();
        return Math.sqrt((dx*dx) + (dy*dy));
    }

    public void rotatePoints(int index) {
        a.setPosition(rotations[index % 30]);
        b.setPosition(rotations[(index + 10) % 30]);
        c.setPosition(rotations[(index + 20) % 30]);
    }

    public Position centroid() {
        int centroidX = (A.getX() + B.getX() + C.getX()) / 3;
        int centroidY = (A.getY() + B.getY() + C.getY()) / 3;
        return new Position(centroidX, centroidY);
    }

    public void fillTriangle(Graphics g, Position a, Position b, Position c, Color color) {
        ((Graphics2D) g).setStroke(new BasicStroke(LINE_SIZE));
        g.setColor(color);

        int[] xPts = {a.getX(), b.getX(), c.getX()};
        int[] yPts = {a.getY(), b.getY(), c.getY()};

        g.fillPolygon(xPts, yPts, 3);
    }

    public Position midPoint(Position p1, Position p2) {
        int newX = (p1.getX() + p2.getX()) / 2;
        int newY = (p1.getY() + p2.getY()) / 2;

        return new Position(newX, newY);
    }
}
