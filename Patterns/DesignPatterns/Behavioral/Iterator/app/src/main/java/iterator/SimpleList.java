package iterator;

import java.util.Iterator;

public class SimpleList<T> implements Iterable<SimpleList<T>>{
    private SimpleList<T> next;
    private T value;
    
    @Override
    public Iterator<SimpleList<T>> iterator() {
        return new SimpleListIterator<>(this);
    }
    
    public void addNode(T value) {
        SimpleList<T> element = this;
        
        while(element.next != null) {
            element = element.next;
        }
        
        element.next = new SimpleList<T>();
        element.next.setValue(value);
    }
    
    public SimpleList<T> getNextNode(){
        return next;
    }
    
    public T getValue() {
        return value;
    }
    
    public void setValue(T value) {
        this.value = value;
    }
}
