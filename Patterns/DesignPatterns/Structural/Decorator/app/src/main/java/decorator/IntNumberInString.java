package decorator;

public class IntNumberInString implements NumberInString {
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
        int num = Integer.parseInt(value);
        num++;
        value = Integer.toString(num);
    }

    @Override
    public void decrement() {
        int num = Integer.parseInt(value);
        num--;
        value = Integer.toString(num);
    }

}
