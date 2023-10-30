package decorator;

public class NumberInStringDecorator implements NumberInString {
    protected NumberInString wrapee;

    public NumberInStringDecorator(NumberInString wrapee) {
        this.wrapee = wrapee;
    }

    @Override
    public void setValue(String value) {
        wrapee.setValue(value);
    }

    @Override
    public String getValue() {
        return wrapee.getValue();
    }

    @Override
    public void increment() {
        wrapee.increment();
    }

    @Override
    public void decrement() {
        wrapee.decrement();
    }

}
