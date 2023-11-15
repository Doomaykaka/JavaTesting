package interpreter;

public class BinaryNumber implements NumberExpression {
    private double value;
    
    public BinaryNumber(String value) {
        this.value = Integer.parseInt(value, 2);
    }
    
    @Override
    public double interpret() {
        return value;
    }

}
