package visitor;

public class Circle implements Shape {
    private int x;
    private int y;
    private int r;
    
    public Circle(int x, int y, int r) {
        this.r = r;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }
    @Override
    public void draw() {
        System.out.println("Circle with coords x=" + x + ",y=" + y + " and radius=" + r);
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visitCircle(this);
    }
    
}
