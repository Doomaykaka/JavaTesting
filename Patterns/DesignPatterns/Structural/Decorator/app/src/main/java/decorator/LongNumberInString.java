package decorator;

public class LongNumberInString implements NumberInString {
    private String value;

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void increment() {
        long num = Long.parseLong(value);
        num++;
        value = Long.toString(num);
    }

    @Override
    public void decrement() {
        long num = Long.parseLong(value);
        num--;
        value = Long.toString(num);
    }
}
