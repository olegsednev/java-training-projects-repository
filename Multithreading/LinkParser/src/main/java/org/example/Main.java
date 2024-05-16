package org.example;


import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        String websiteAddress = "https://lenta.ru/";
        LinkParserRecursive linkParserRecursive = new LinkParserRecursive(websiteAddress);
        new ForkJoinPool().invoke(linkParserRecursive);
        ArrayList<String> structure = new ArrayList<>(linkParserRecursive.getLinkSet());
        Collections.sort(structure);
        System.out.println(structure.size());
        structure.forEach(link -> System.out.println(linkToString(link)));
    }

    public static String linkToString(String link) {
        int count = 0;
        char[] chars = link.toCharArray();
        for (char aChar : chars) {
            if (aChar == '/') {
                count++;
            }
        }
        return repeat("\t", count - 3) + link;
    }

    private static String repeat(String string, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(string);
        }
        return builder.toString();
    }
}