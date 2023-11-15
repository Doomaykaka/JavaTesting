package iterator;

import java.util.Iterator;

public class SimpleListIterator<T> implements Iterator<SimpleList<T>>{
    private SimpleList<T> collection;
    private SimpleList<T> current;
    
    public SimpleListIterator(SimpleList<T> collection) {
        this.collection = collection;
        current = this.collection;
    }

    @Override
    public boolean hasNext() {
        return current.getNextNode() != null;
    }

    @Override
    public SimpleList<T> next() {
        current = current.getNextNode();
        return current;
    }

}
