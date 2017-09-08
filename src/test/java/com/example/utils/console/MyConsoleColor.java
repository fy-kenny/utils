package com.example.utils.console;

/**
 * You can define your console color like the class.
 *
 * @author Kenny Fang
 * @version 1.0
 * @date 2017/9/8 15:22
 */
public class MyConsoleColor extends ConsoleColor {

    public static final ConsoleColor UNKNOW = new ConsoleColor(COLOR.BLACK);

    private MyConsoleColor(int code) {
        super(code);
    }
}
