package command;

import java.util.List;

public class CollectionEditor<T> {
    private List<T> list;
    
    public CollectionEditor(List<T> newList){
        list = newList;
    }
    
    public List<T> getCollection() {
        return list;
    }
}
