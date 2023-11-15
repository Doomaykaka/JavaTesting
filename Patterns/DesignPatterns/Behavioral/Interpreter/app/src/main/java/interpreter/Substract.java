package interpreter;

public class Substract implements NumberExpression {
    private NumberExpression left;
    private NumberExpression right;
    
    public Substract(NumberExpression left, NumberExpression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public double interpret() {
        return left.interpret() - right.interpret();
    }

}
