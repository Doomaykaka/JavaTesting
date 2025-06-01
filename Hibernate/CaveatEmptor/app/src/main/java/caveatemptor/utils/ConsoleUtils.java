package caveatemptor.utils;

import java.util.List;
import java.util.Scanner;

public class ConsoleUtils {
    private static final String QUESTION_SEPARATOR = ":";

    public static String readLineWithQuestion(Scanner scan, String question) {
        String result = "";

        if (scan == null || question == null) {
            return result;
        }

        System.out.print(question + QUESTION_SEPARATOR);

        if (scan.hasNextLine()) {
            result = scan.nextLine();
        }

        return result;
    }

    public static void printText(List<String> text) {
        for (String line : text) {
            System.out.println(line);
        }
    }

    public static void printText(String text) {
        System.out.println(text);
    }
}
