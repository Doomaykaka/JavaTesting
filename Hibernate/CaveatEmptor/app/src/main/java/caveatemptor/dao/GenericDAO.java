package caveatemptor.dao;

import java.util.List;

public interface GenericDAO<T> {
    public T get(long id);

    public List<T> getAll();

    public boolean remove(long id);

    public boolean update(T entity);

    public boolean create(T entity);
}
