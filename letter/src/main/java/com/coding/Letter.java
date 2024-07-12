package com.coding;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Letter {
    private static final int COUNT = 3;

    public static String replaceChars(String originStr) {
        originStr = originStr.toLowerCase();
        int count = 0;
        List<String> list = new LinkedList<>();
        for (int i = 0; i < originStr.length(); i++) {
            for (int j = i; j < originStr.length(); j++) {
                char ch1 = originStr.charAt(i);
                char ch2 = originStr.charAt(j);
                if (ch1 == ch2) {
                    count++;
                } else {
                    i = j - 1;
                    count = 0;
                    break;
                }
                if (count >= COUNT) {
                    list.add(String.valueOf(ch1).repeat(count));
                }
            }
        }
        if (!list.isEmpty()) {
            for (String str : list) {
                originStr = originStr.replace(str, "");
            }
            return replaceChars(originStr);
        }
        return originStr;
    }


    public static String replaceChars2(String originStr) {
        originStr = originStr.toLowerCase();
        int count = 0;
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < originStr.length(); i++) {
            for (int j = i; j < originStr.length(); j++) {
                char ch1 = originStr.charAt(i);
                char ch2 = originStr.charAt(j);
                if (ch1 == ch2) {
                    count++;
                } else {
                    i = j - 1;
                    count = 0;
                    break;
                }
                if (count >= COUNT) {
                    char chr = (char) (ch1 - 1);
                    if (chr < 'a') {
                        map.put(String.valueOf(ch1).repeat(count), "");
                    } else {
                        map.put(String.valueOf(ch1).repeat(count), String.valueOf(chr));
                    }
                }
            }
        }
        if (!map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                originStr = originStr.replace(entry.getKey(), entry.getValue());
            }
            return replaceChars2(originStr);
        }
        return originStr;
    }

    public static void main(String[] args) {
        System.out.println(replaceChars("aabcccbbad"));
        System.out.println(replaceChars2("abcccbad"));
        String str = "hello,你好";

        int len = str.codePointCount(0, str.length());
        System.out.println(len);
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes.length);

        Clazz clazz = new Clazz();
        Field wildcardField;
        try {
            wildcardField = clazz.getClass().getField("lst");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        Type wildcardType = wildcardField.getGenericType();
        if (wildcardType instanceof ParameterizedType paramType) {
            paramType = (ParameterizedType) wildcardType;
            Type[] actualTypeArguments = paramType.getActualTypeArguments();
            for (Type arg : actualTypeArguments) {
                System.out.println("Actual type argument: " + arg);
            }
        }

    }

    private static class Clazz {
        public List<? extends Number> lst;
    }
}
