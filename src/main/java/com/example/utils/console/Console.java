package com.example.utils.console;

/**
 * Console Utils.<br>
 * <p>
 * You can customize your own console style by the utils including foreground and background.
 *
 * @author Kenny Fang
 * @version 1.0
 * @date 2017/9/7 22:30
 * @see ConsoleColor
 */
public class Console {

    /**
     * Print <code>charSequence</code> to System.out with color red..
     *
     * @param charSequence
     */
    public static void printlnRed(String charSequence) {
        println(charSequence, ConsoleColor.RED);

        clearAllFormatting();
    }

    /**
     * Print <code>charSequence</code> to System.out with color green.
     *
     * @param charSequence
     */
    public static void printlnGreen(String charSequence) {
        println(charSequence, ConsoleColor.GREEN);

        clearAllFormatting();
    }

    /**
     * Print <code>charSequence</code> to System.out with color blue.
     *
     * @param charSequence
     */
    public static void printlnBlue(String charSequence) {
        println(charSequence, ConsoleColor.BLUE);

        clearAllFormatting();
    }

    public static void println(String charSequence, ConsoleColor consoleColor) {
        color(consoleColor);

        System.out.println(charSequence);
    }

    /**
     * Set console color.
     *
     * @param color
     * @see ConsoleColor
     */
    public static void color(ConsoleColor color) {
        System.out.print(color.code());
    }

    /**
     * Set console color.
     *
     * @param colorCode
     * @see ConsoleColor
     */
    public static void color(int colorCode) {
        System.out.print(ConsoleColor.NEW(colorCode).code());
    }

    /**
     * Clear all formatting
     */
    public static void clearAllFormatting() {
        System.out.print(ConsoleColor.CLEAR_ALL_FORMATTING.code());
    }

}
