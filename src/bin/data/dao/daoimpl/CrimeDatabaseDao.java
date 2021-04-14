package bin.data.dao.daoimpl;

import bin.data.Config;
import bin.data.Const;
import bin.data.dao.CrimeDao;
import bin.data.Models.Crime;

import java.sql.*;
import java.util.List;

public class CrimeDatabaseDao implements CrimeDao {
    Connection dbConnection;
    Config config = Config.getInstance();
    Crime crime = new Crime();
    public Connection getDbConnection() throws SQLException, ClassNotFoundException {
        String connectionString = "jdbc:mysql://"+config.dbHost+":"+config.dbPort+"/crimes?serverTimezone=Europe/Moscow";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, config.dbUser, config.dbPass);

        return dbConnection;
    }

    @Override
    public List<Crime> getAll() {

        return null;
    }

    @Override
    public Crime getById(Long id) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.CRIMES_TABLE_CRIMINAL + " WHERE "
                    + Const.CRIMINAL_ID + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                crime.setId(resultSet.getLong(1));
                crime.setLastname(resultSet.getString(2));
                crime.setFirstname(resultSet.getString(3));
                crime.setNationality(resultSet.getString(4));
                crime.setAlias(resultSet.getString(5));
                crime.setAbout(resultSet.getString(13));
                crime.setBirthday(resultSet.getDate(6));
                crime.setCountry(resultSet.getString(7));
                crime.setHeight(resultSet.getInt(8));
                crime.setSex(resultSet.getString(9));
                crime.setEye(resultSet.getString(10));
                crime.setHair(resultSet.getString(11));
                crime.setWantedBy(resultSet.getString(12));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return crime;
    }

    @Override
    public CrimeDao add(Crime model) throws SQLException{
        String sqlInto = "INSERT INTO " + Const.CRIMES_TABLE_CRIMINAL + "(" + Const.CRIMINAL_LASTNAME + ","
                    + Const.CRIMINAL_FORENAME + "," + Const.CRIMINAL_NATIONALITY + ","
                    + Const.CRIMINAL_ALIAS + "," + Const.CRIMINAL_ABOUT + ","
                    + Const.CRIMINAL_BIRTHDAY + "," + Const.CRIMINAL_CITYBIRTH + ","
                    + Const.CRIMINAL_HEIGHT + "," + Const.CRIMINAL_SEX + ","
                    + Const.CRIMINAL_EYE + "," + Const.CRIMINAL_HAIR + ","
                    + Const.CRIMINAL_WANTED + "," + Const.CRIMINAL_GROUPING + ")" + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(sqlInto);
            preparedStatement.setString(1, model.getLastname());
            preparedStatement.setString(2, model.getFirstname());
            preparedStatement.setString(3, model.getNationality());
            preparedStatement.setString(4, model.getAlias());
            preparedStatement.setString(5, model.getAbout());
            preparedStatement.setDate(6, (Date) model.getBirthday());
            preparedStatement.setString(7, model.getCountry());
            preparedStatement.setInt(8, model.getHeight());
            preparedStatement.setString(9, model.getSex());
            preparedStatement.setString(10, model.getEye());
            preparedStatement.setString(11, model.getHair());
            preparedStatement.setString(12, model.getWantedBy());
            preparedStatement.setString(13, model.getGrouping());

            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException c) {
                c.printStackTrace();
        }
        finally {
            dbConnection.close();
            System.out.println("Added");
        }
        return this;
    }

    @Override
    public CrimeDao update(Crime model) {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE "+Const.CRIMES_TABLE_CRIMINAL+" SET "+Const.CRIMINAL_ALIAS+" =?, "+Const.CRIMINAL_BIRTHDAY+" =?, "
                +Const.CRIMINAL_CITYBIRTH+" =?, "+Const.CRIMINAL_HEIGHT+" =?, "+Const.CRIMINAL_SEX+" =?, "
                +Const.CRIMINAL_EYE+" =?, "+Const.CRIMINAL_HAIR+" =?, "+Const.CRIMINAL_WANTED+" = ?, "
                +Const.CRIMINAL_ABOUT+" =?" +
                " WHERE "+Const.CRIMINAL_LASTNAME+" =? AND "+Const.CRIMINAL_FORENAME+" =? AND "+Const.CRIMINAL_NATIONALITY+" =?";
        try {
            preparedStatement = getDbConnection().prepareStatement(sql);

            preparedStatement.setString(1, model.getAlias());
            preparedStatement.setDate(2, model.getBirthday());
            preparedStatement.setString(3, model.getCountry());
            preparedStatement.setInt(4, model.getHeight());
            preparedStatement.setString(5, model.getSex());
            preparedStatement.setString(6, model.getEye());
            preparedStatement.setString(7, model.getHair());
            preparedStatement.setString(8, model.getWantedBy());
            preparedStatement.setString(9, model.getAbout());

            preparedStatement.setString(10, model.getLastname());
            preparedStatement.setString(11, model.getFirstname());
            preparedStatement.setString(12, model.getNationality());

            preparedStatement.addBatch();
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
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
    public CrimeDao delete(Crime model) {
        Statement statement = null;
        try {
            statement = getDbConnection().createStatement();
            String delete = "DELETE FROM " + Const.CRIMES_TABLE_CRIMINAL + " WHERE " +Const.CRIMINAL_ID+ " = " + model.getId();
            statement.executeUpdate(delete);
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(statement != null)
            try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public CrimeDao addPrimaryInfo(String LastName, String ForeName, String Nationality) {
        crime.setLastname(LastName);
        crime.setFirstname(ForeName);
        crime.setNationality(Nationality);

        return this;
    }

    @Override
    public CrimeDao addSecondaryInfo(String Alias, String AboutPerson, Date BirthDay, String CityOfBirth, Integer Height, String Sex, String Eye, String Hair, String WantedBy) {
        crime.setAlias(Alias);
        crime.setAbout(AboutPerson);
        crime.setBirthday(BirthDay);
        crime.setCountry(CityOfBirth);
        crime.setHeight(Height);
        crime.setSex(Sex);
        crime.setEye(Eye);
        crime.setHair(Hair);
        crime.setWantedBy(WantedBy);
        return this;
    }

    @Override
    public CrimeDao getByPrimaryInfo(Crime model){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.CRIMES_TABLE_CRIMINAL + " WHERE "
                    + Const.CRIMINAL_FORENAME + "=? AND " + Const.CRIMINAL_LASTNAME + "=? AND " + Const.CRIMINAL_NATIONALITY + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, model.getFirstname());
            preparedStatement.setString(2, model.getLastname());
            preparedStatement.setString(3, model.getNationality());

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                crime.setId(resultSet.getLong(1));
                crime.setLastname(resultSet.getString(2));
                crime.setFirstname(resultSet.getString(3));
                crime.setNationality(resultSet.getString(4));
                crime.setAlias(resultSet.getString(5));
                crime.setAbout(resultSet.getString(13));
                crime.setBirthday(resultSet.getDate(6));
                crime.setCountry(resultSet.getString(7));
                crime.setHeight(resultSet.getInt(8));
                crime.setSex(resultSet.getString(9));
                crime.setEye(resultSet.getString(10));
                crime.setHair(resultSet.getString(11));
                crime.setWantedBy(resultSet.getString(12));
                crime.setGrouping(resultSet.getString(13));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public Crime build() {
        return crime;
    }
}
