package composite;

import java.util.ArrayList;
import java.util.List;

public class CompoundGraphic implements Graphic {

    List<Graphic> children;
    
    public CompoundGraphic() {
        children = new ArrayList<Graphic>();
    }

    @Override
    public void move(int x, int y) {
        for (Graphic graphic : children) {
            graphic.move(x, y);
        }
    }

    @Override
    public void draw() {
        for (Graphic graphic : children) {
            graphic.draw();
        }
        
        System.out.println("=================Scene drawed=================");
    }

    @Override
    public void clear() {
        for (Graphic graphic : children) {
            graphic.clear();
        }
        
        System.out.println("=================Scene cleared=================");
    }
    
    public void add(Graphic g) {
        children.add(g);
    }
    
    public void remove(Graphic g) {
        children.remove(g);
    }
}
