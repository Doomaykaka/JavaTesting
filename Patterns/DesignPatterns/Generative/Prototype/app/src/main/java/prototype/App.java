/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package prototype;

import java.util.ArrayList;
import java.util.List;

public class App {
    static List<Shape> shapes;

    public static void main(String[] args) {
        shapes = new ArrayList<Shape>();
        
        Circle circle = new Circle();
        circle.X = 10;
        circle.Y = 10;
        circle.radius = 20;
        shapes.add(circle);

        Circle anotherCircle = (Circle) circle.clone();
        shapes.add(anotherCircle);

        Rectangle rectangle = new Rectangle();
        rectangle.width = 10;
        rectangle.height = 20;
        shapes.add(rectangle);
        
        businessLogic();
    }
    
    public static void businessLogic() {
        List<Shape> shapesCopy = new ArrayList<Shape>();

        for(Shape s:shapes) {
            shapesCopy.add(s.clone());
        }   
        
        System.out.println();
    }
}
