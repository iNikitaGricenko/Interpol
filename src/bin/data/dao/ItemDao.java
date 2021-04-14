package bin.data.dao;

import bin.data.Models.Model;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao <T extends Model>{

    public List<T> getAll();
    public T getById(Long id);
    public ItemDao add(T model) throws SQLException;
    public ItemDao update (T model);
    public ItemDao delete(T model);
}
