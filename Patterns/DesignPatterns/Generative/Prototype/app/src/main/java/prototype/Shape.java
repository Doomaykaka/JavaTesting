package prototype;

public abstract class Shape {
    int X;
    int Y;
    String color;

    public Shape() {

    }

    public Shape(Shape shapeToClone) {
        this();
        this.X = shapeToClone.X;
        this.Y = shapeToClone.Y;
        this.color = shapeToClone.color;
    }

    public abstract Shape clone();
}
