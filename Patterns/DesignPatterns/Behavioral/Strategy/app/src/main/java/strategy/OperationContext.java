package strategy;

public class OperationContext {
    private Operation strategy;
    
    public OperationContext(Operation strategy) {
        this.strategy = strategy;
    }
    
    public void setOperation(Operation newStrategy) {
        strategy = newStrategy;
    }
    
    public double getResult(double a, double b) {
        return strategy.calculate(a, b);
    }
}
