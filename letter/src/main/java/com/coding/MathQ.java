package com.coding;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.function.Function;

public class MathQ {

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.println("请输入一个大于等于3的自然数：");
        var number = scanner.nextBigInteger();//912589990684262400
        while (number != null) {
            System.out.print(number + "->");
            calcNum(number);
            System.out.println();
            System.out.println("请继续输入一个大于等于3的自然数：");
            number = scanner.nextBigInteger();
        }
        scanner.close();
    }

    static void calcNum(BigInteger x) {
        if (x.equals(BigInteger.ONE)) {
            return;
        } else {
            Function<BigInteger, BigInteger> fx = t -> {
                if (t.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                    t = t.divide(BigInteger.TWO);
                    if (t.longValue() != 1) {
                        System.out.print(t + "->");
                    } else {
                        System.out.print(t);
                    }
                    return t;
                } else {
                    t = t.multiply(BigInteger.ONE.add(BigInteger.TWO)).add(BigInteger.ONE);
                    System.out.print(t + "->");
                    return t;
                }
            };

            calcNum(fx.apply(x));
        }

    }




}
