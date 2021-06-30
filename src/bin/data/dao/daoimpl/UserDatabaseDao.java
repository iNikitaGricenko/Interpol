package bin.data.dao.daoimpl;

import bin.data.Config;
import bin.data.Const;
import bin.data.Models.User;
import bin.data.dao.UserDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDatabaseDao implements UserDao {
    Connection dbConnection;
    Config config = Config.getInstance();
    User user = new User();
    List<User> userList = new ArrayList<>();
    public Connection getDbConnection() throws SQLException, ClassNotFoundException {
        String connectionString = "jdbc:mysql://"+config.dbHost+":"+config.dbPort+"/user?serverTimezone=Europe/Moscow";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, config.dbUser, config.dbPass);

        return dbConnection;
    }
    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM " + Const.USERS_TABLE;
        ResultSet resultSet = null;
        try{
            Connection connection = getDbConnection();
            try{
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                while(resultSet.next()){
                    user = new User();
                    user.setId(resultSet.getLong(1));
                    user.setLogin(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setFirstname(resultSet.getString(4));
                    user.setLastname(resultSet.getString(5));
                    userList.add(user);
                }
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public User getById(Long id) {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.USERS_TABLE + " WHERE "
                + Const.USERS_ID + "=?";
        try {
            Connection connection = getDbConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(select);
                preparedStatement.setLong(1, id);

                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    user.setId(resultSet.getLong(1));
                    user.setLogin(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setFirstname(resultSet.getString(4));
                    user.setLastname(resultSet.getString(5));
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        catch (SQLException e) {
                e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public UserDao add(User model) throws SQLException {
    	String sqlInto = "INSERT INTO " + Const.USERS_TABLE + " (" + Const.USERS_LOGIN+", " +Const.USERS_PASSWORD+","+ Const.USERS_FORENAME+","+Const.USERS_SURNAME+")" + "VALUES(?,?,?,?)";
        String sqlFrom = "SELECT "+Const.USERS_LOGIN+" FROM "+Const.USERS_TABLE+" WHERE " + Const.USERS_LOGIN + "=?";
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = getDbConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlFrom);
                preparedStatement.setString(1, model.getLogin());
                resultSet = preparedStatement.executeQuery();
                if(!resultSet.next()){
                    preparedStatement = connection.prepareStatement(sqlInto);

                    preparedStatement.setString(1, model.getLogin());
                    preparedStatement.setString(2, model.getPassword());
                    preparedStatement.setString(3, model.getFirstname());
                    preparedStatement.setString(4, model.getLastname());

                    preparedStatement.executeUpdate();
                }
                else {
                    resultSet.close();
                    connection.close();
                }
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public UserDao update(User model) {
        return this;
    }

    @Override
    public UserDao delete(User model) {
        Statement statement = null;
        try {
            statement = dbConnection.createStatement();
            String delete = "DELETE FROM " + Const.USERS_TABLE + " WHERE " +Const.USERS_ID+ " = " + model.getId();
            statement.executeUpdate(delete);
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                dbConnection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public UserDao getByLogin(User model) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM " + Const.USERS_TABLE + " WHERE "
                    + Const.USERS_LOGIN + "=? AND " + Const.USERS_PASSWORD + "=?";
        try {
            Connection connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, model.getLogin());
            preparedStatement.setString(2, model.getPassword());

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                    user.setId(resultSet.getLong(1));
                    user.setLogin(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setFirstname(resultSet.getString(4));
                    user.setLastname(resultSet.getString(5));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        }
        return this;
    }

    @Override
    public User build()
    {
        return user;
    }
}
