package ru.freeomsk.util;

public class Utils {

    /**
     * Выводит строку с форматированием, состоящую из повторяющихся символов.
     *
     * @param character Символ для повторения.
     * @param count     Количество повторений символа.
     */
    public static void printFormatted(char character, int count) {
        String repeatedString = String.valueOf(character).repeat(count);
        System.out.printf("%s%n", repeatedString);
    }
}