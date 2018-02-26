package com.tseong.learning.basic.scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class _01_TestScanner {

    public static void main(String[] args) {
        scanner_04();
    }

    public static void scanner_01() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入字符串:");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.equals("exit"))
                break;

            System.out.println(">>>" + line);
        }
    }

    public static void scanner_02() {
        Scanner scanner = new Scanner("123 asdf sd 45 789 sdf asdfl,sdf.sdfl*asdf    ......asdfkl    las");
        scanner.useDelimiter(" |,|\\.|\\*");

        while(scanner.hasNext()) {
            System.out.println(scanner.next());
        }
    }

    public static void scanner_03() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入字符串");
        while (scanner.hasNext()) { // 一旦控制台有输入，scanner就开始从控制台读取
            String s1 = scanner.next();
            String s2 = scanner.next();
            String s3 = scanner.next();

            System.out.println(">>>" + s1);
            System.out.println(">>>" + s2);
            System.out.println(">>>" + s3);
        }
    }

    public static void scanner_04() {

        try {
            InputStream in = new FileInputStream(new File("/Users/Shawn/Documents/workspace/JavaLearning/target/copy.txt"));
            Scanner scanner = new Scanner(in);
            while(scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
