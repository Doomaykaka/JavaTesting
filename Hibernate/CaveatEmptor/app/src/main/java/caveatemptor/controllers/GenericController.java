package caveatemptor.controllers;

import java.util.List;

public interface GenericController<T> {
    public List<String> get(long id);
    
    public List<String> getAll();
    
    public List<String> remove(long id);
    
    public List<String> update(long id);
    
    public List<String> create();
}
