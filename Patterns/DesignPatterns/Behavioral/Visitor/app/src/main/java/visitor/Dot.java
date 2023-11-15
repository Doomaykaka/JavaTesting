package visitor;

public class Dot implements Shape {
    private int x;
    private int y;
    
    public Dot(int x, int y) {
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
        System.out.println("Dot with coords x=" + x + ",y=" + y);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitDot(this);
    }
}
