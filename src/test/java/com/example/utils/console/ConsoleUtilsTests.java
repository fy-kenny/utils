package com.example.utils.console;

/**
 * Test class of Console.
 *
 * @author Kenny Fang
 * @version 1.0
 * @date 2017/9/8 13:43
 */
public class ConsoleUtilsTests {

    public static void main(String[] args) {
        Console.printlnRed("This is stranger world! ~v~");
        Console.printlnGreen("This is stranger world! ~v~");
        Console.printlnBlue("This is stranger world! ~v~");

        System.out.println("This is stranger world! ~v~");

        Console.color(ConsoleColor.RED);
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");

        Console.color(ConsoleColor.GREEN);
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");

        Console.color(ConsoleColor.BLUE);
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");

        Console.color(ConsoleColor.YELLOW);
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");

        Console.color(ConsoleColor.YELLOW_BACKGROUND);
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");

        Console.color(ConsoleColor.BLACK_BACKGROUND);
        Console.color(ConsoleColor.GREEN);
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");

        Console.color(ConsoleColor.MAKE_BRIGHT);
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");

        Console.color(ConsoleColor.STOP_BRIGHT);
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");

        Console.color(ConsoleColor.UNDERLINE);
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");

        Console.color(ConsoleColor.STOP_UNDERLINE);
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");

        Console.color(MyConsoleColor.UNKNOW);
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");

        Console.color(101);
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");
        System.out.println("This is stranger world! ~v~");

        Console.clearAllFormatting();

        System.out.println("default text style");
    }
}
