package recursion.seirpinski_triangle;

import java.awt.*;

/*
Constants for Seirpinski's Triangle
 */
public interface SeirpinskiConstants {
    ///color presets///
    //index 1 = white bg/black lines, 2 = black bg/white lines, 3 = black bg/rgb lines
    Color [] BACKGROUND = {Color.WHITE, Color.BLACK, Color.BLACK};
    Color [] LINE_1 = {Color.BLACK, Color.WHITE, Color.RED};
    Color [] LINE_2 = {Color.BLACK, Color.WHITE, Color.BLUE};
    Color [] LINE_3 = {Color.BLACK, Color.WHITE, Color.GREEN};

    //fill colors
    Color [] FILL = {Color.RED, Color.GREEN, Color.BLUE};

    //theme names as strings
    String [] MODE_NAME = {"Light Mode", "Dark Mode", "RGB Mode"};

    //color theme constants
    int WHITE = 0;
    int BLACK = 1;
    int RGB = 2;

    ///constants///
    //settings for size and positioning of the graphics
    double TRIANGLE_HEIGHT_RATIO = 0.866;
    int TRIANGLE_SIDE_SIZE = 120;
    int X_OFFSET = 40;
    int Y_OFFSET = 50;
    int LINE_SIZE = 2;

    //default depth, scale, and mode settings
    int DEFAULT_DEPTH = 3;
    int DEFAULT_SCALE = 2;
    int DEFAULT_MODE = 0;

    //for color rotation animation
    double ROTATION = Math.PI * 2 / 30;
}
