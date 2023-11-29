package idioms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArraysAsListUsing {
    public static void createArrayAsList() {
        List words = new ArrayList<>(Arrays.asList("Hello", "World"));
        
        words.stream().forEach(text -> System.out.println(text));
    }
}
