package com.example.utils.console;

/**
 * Console color constant class.
 * <br>
 *     you can extends the class to customize your own ConsoleColor class.
 *
 *<pre>
 *   ANSI Table : wikipedia - ANSI escape codes

     For quick reference:

     30 black
     31 red
     32 green
     33 yellow
     34 blue
     35 magenta
     36 cyan
     37 white
     40 black background
     41 red background
     42 green background
     43 yellow background
     44 blue background
     45 magenta background
     46 cyan background
     47 white background
     1 make bright (usually just bold)
     21 stop bright (normalizes boldness)
     4 underline
     24 stop underline
     0 clear all formatting
 * </pre>
 *
 * @author Kenny Fang
 * @version 1.0
 * @date 2017/9/8 15:07
 */
public class  ConsoleColor {
    public static final ConsoleColor BLACK = new ConsoleColor(COLOR.BLACK);
    public static final ConsoleColor RED = new ConsoleColor(COLOR.RED);
    public static final ConsoleColor GREEN = new ConsoleColor(COLOR.GREEN);
    public static final ConsoleColor YELLOW = new ConsoleColor(COLOR.YELLOW);
    public static final ConsoleColor BLUE = new ConsoleColor(COLOR.BLUE);
    public static final ConsoleColor MAGENTA = new ConsoleColor(COLOR.MAGENTA);
    public static final ConsoleColor CYAN = new ConsoleColor(COLOR.CYAN);
    public static final ConsoleColor WHITE = new ConsoleColor(COLOR.WHITE);
    public static final ConsoleColor BLACK_BACKGROUND = new ConsoleColor(COLOR.BLACK_BACKGROUND);
    public static final ConsoleColor RED_BACKGROUND = new ConsoleColor(COLOR.RED_BACKGROUND);
    public static final ConsoleColor GREEN_BACKGROUND = new ConsoleColor(COLOR.GREEN_BACKGROUND);
    public static final ConsoleColor YELLOW_BACKGROUND = new ConsoleColor(COLOR.YELLOW_BACKGROUND);
    public static final ConsoleColor BLUE_BACKGROUND = new ConsoleColor(COLOR.BLUE_BACKGROUND);
    public static final ConsoleColor MAGENTA_BACKGROUND = new ConsoleColor(COLOR.MAGENTA_BACKGROUND);
    public static final ConsoleColor CYAN_BACKGROUND = new ConsoleColor(COLOR.CYAN_BACKGROUND);
    public static final ConsoleColor WHITE_BACKGROUND = new ConsoleColor(COLOR.WHITE_BACKGROUND);
    public static final ConsoleColor MAKE_BRIGHT = new ConsoleColor(COLOR.MAKE_BRIGHT);
    public static final ConsoleColor STOP_BRIGHT = new ConsoleColor(COLOR.STOP_BRIGHT);
    public static final ConsoleColor UNDERLINE = new ConsoleColor(COLOR.UNDERLINE);
    public static final ConsoleColor STOP_UNDERLINE = new ConsoleColor(COLOR.STOP_UNDERLINE);
    public static final ConsoleColor CLEAR_ALL_FORMATTING = new ConsoleColor(COLOR.CLEAR_ALL_FORMATTING);

    private String code;

    protected ConsoleColor(int code) {
        this.code = (char) 27 + "[" + code + "m";
    }

    public String code() {
        return this.code;
    }

    @SuppressWarnings("unused")
    public static ConsoleColor NEW(int code) {
        return new ConsoleColor(code);
    }

    class COLOR {
        static final int BLACK = 30; // black
        static final int RED = 31; // red
        static final int GREEN = 32; // green
        static final int YELLOW = 33; // yellow
        static final int BLUE = 34; // blue
        static final int MAGENTA = 35; // magenta
        static final int CYAN = 36; // cyan
        static final int WHITE = 37; // white
        static final int BLACK_BACKGROUND = 40; // black background
        static final int RED_BACKGROUND = 41; // red background
        static final int GREEN_BACKGROUND = 42; // green background
        static final int YELLOW_BACKGROUND = 43; // yellow background
        static final int BLUE_BACKGROUND = 44; // blue background
        static final int MAGENTA_BACKGROUND = 45; // magenta background
        static final int CYAN_BACKGROUND = 46; // cyan background
        static final int WHITE_BACKGROUND = 47; // white background
        static final int MAKE_BRIGHT = 1;  // make bright (usually just bold)
        static final int STOP_BRIGHT = 21; // stop bright (normalizes boldness)
        static final int UNDERLINE = 4;  // underline
        static final int STOP_UNDERLINE = 24; // stop underline
        static final int CLEAR_ALL_FORMATTING = 0;  // clear all formatting
    }
}