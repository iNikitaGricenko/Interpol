package bin.data.dao;

import bin.data.Models.Model;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao <T extends Model>{

     List<T> getAll();
     T getById(Long id);
     ItemDao add(T model) throws SQLException;
     ItemDao update (T model);
     ItemDao delete(T model);
}
