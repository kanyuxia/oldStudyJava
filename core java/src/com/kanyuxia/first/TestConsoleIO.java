package com.kanyuxia.first;

import java.util.Scanner;

/**
 * Created by kanyuxia on 2017/7/1.
 */
public class TestConsoleIO {

    public static void testInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入一段话，然后回车");
        String inputStr = scanner.nextLine();
        System.out.println(inputStr);
    }

    public static void testOutput() {
        String outputStr = "hello world!";
        System.out.println(outputStr);
        System.out.println("System.out: " + System.out);
    }
}
