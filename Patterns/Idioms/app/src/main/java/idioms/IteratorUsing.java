package idioms;

public class IteratorUsing {
    String[] words;

    public IteratorUsing(String[] words) {
        this.words = words;
    }

    public void print() {
        for (String word : words) {
            System.out.println(word);
        }
    }
}
