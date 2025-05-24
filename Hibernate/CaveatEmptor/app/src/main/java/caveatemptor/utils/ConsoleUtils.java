package caveatemptor.utils;

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
}
