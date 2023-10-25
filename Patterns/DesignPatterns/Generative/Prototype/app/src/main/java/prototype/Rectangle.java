package prototype;

public class Rectangle extends Shape{
    int width;
    int height;
    
    public Rectangle() {
        
    }
    
    public Rectangle(Rectangle rectangleToClone) {
        super(rectangleToClone);
        this.width = rectangleToClone.width;
        this.height = rectangleToClone.height;
    }

    @Override
    public Shape clone() {
        return new Rectangle(this);
    }
}
