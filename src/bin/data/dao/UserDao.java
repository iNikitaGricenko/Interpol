package bin.data.dao;

import bin.data.Models.User;

public interface UserDao extends ItemDao<User>{
    UserDao getByLogin(User model);
    User build();
}
