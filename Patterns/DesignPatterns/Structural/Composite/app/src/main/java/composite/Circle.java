package composite;

public class Circle extends Dot {
    protected int radius;

    public Circle(int x, int y, int radius) {
        super(x, y);
        this.radius = radius;
    }

    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw() {
        if ((radius != 0) && (x >= 0) && (y >= 0)) {
            System.out.println("Circle with radius " + radius + " and center " + x + "," + y);
        }
    }

    @Override
    public void clear() {
        x = -1;
        y = -1;
        radius = 0;
    }
}
