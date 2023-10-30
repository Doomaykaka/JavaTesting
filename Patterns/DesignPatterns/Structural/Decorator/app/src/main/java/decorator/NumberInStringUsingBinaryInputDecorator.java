package decorator;

public class NumberInStringUsingBinaryInputDecorator extends NumberInStringDecorator {

    public NumberInStringUsingBinaryInputDecorator(NumberInString wrapee) {
        super(wrapee);
    }

    @Override
    public void setValue(String value) {
        String num = Long.toString(Long.valueOf(value, 2));
        wrapee.setValue(num);
    }
}
