package prototype;

public class Circle extends Shape {
    int radius;

    public Circle() {
        
    }
    
    public Circle(Circle circleToClone) {
        super(circleToClone);
        this.radius = circleToClone.radius;
    }

    @Override
    public Shape clone() {
        return new Circle(this);
    }
}
