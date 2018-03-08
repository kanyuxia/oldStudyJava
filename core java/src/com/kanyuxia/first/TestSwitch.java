package com.kanyuxia.first;

import java.util.Scanner;

/**
 * Created by kanyuxia on 2017/7/1.
 */
public class TestSwitch {

    public static void test() {
        System.out.println("请输入");
        Scanner scanner = new Scanner(System.in);
        String inputStr = scanner.nextLine();
        switch (inputStr) {
            case "hello":
                System.out.println("hello");
                break;
            case "world":
                System.out.println("world");
                break;
            default :
                System.out.println("default");
        }
    }
}
