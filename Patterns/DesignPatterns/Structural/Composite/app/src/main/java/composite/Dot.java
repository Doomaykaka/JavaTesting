package composite;

public class Dot implements Graphic {
    protected int x;
    protected int y;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw() {
        if ((x >= 0) && (y >= 0)) {
            System.out.println("Dot with coords " + x + "," + y);
        }
    }

    @Override
    public void clear() {
        x = -1;
        y = -1;
    }

}
