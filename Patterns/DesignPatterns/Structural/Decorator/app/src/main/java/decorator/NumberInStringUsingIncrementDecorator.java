package decorator;

public class NumberInStringUsingIncrementDecorator extends NumberInStringDecorator {

    public NumberInStringUsingIncrementDecorator(NumberInString wrapee) {
        super(wrapee);
    }

    @Override
    public String getValue() {
        long num = Long.valueOf(wrapee.getValue());
        num++;
        String numString = Long.toString(num);
        return numString;
    }

}
