package bin.data.dao.daoimpl;

import bin.data.Config;
import bin.data.Const;
import bin.data.Models.Crime;
import bin.data.Models.Grouping;
import bin.data.dao.GroupingDao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupingDatabaseDao implements GroupingDao {
    Connection dbConnection;
    Config config = Config.getInstance();
    Grouping grouping = new Grouping();
    List<Grouping> groupingList = new ArrayList<>();
    List<Crime> crimesInGrouping = new ArrayList<>();
    public Connection getDbConnection() throws SQLException, ClassNotFoundException {
        String connectionString = "jdbc:mysql://"+config.dbHost+":"+config.dbPort+"/crimes?serverTimezone=Europe/Moscow";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, config.dbUser, config.dbPass);

        return dbConnection;
    }

    @Override
    public List<Grouping> getAll() {
        String sql = "SELECT * FROM " + Const.CRIMES_TABLE_GROUPING;
        ResultSet resultSet = null;
        try{
            Statement statement = getDbConnection().createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                grouping = new Grouping();
                grouping.setId(resultSet.getLong(1));
                grouping.setName(resultSet.getString(2));
                grouping.setSpecification(resultSet.getString(3));
                grouping.setLocation(resultSet.getString(4));
                grouping.setAboutGrouping(resultSet.getString(5));
                grouping.setCreationDate(resultSet.getDate(6));
                grouping.setCountry(resultSet.getString(7));
                grouping.setDangerLvl(resultSet.getString(9));
                groupingList.add(grouping);
            }
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return groupingList;
    }

    @Override
    public Grouping getById(Long id) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.CRIMES_TABLE_GROUPING + " WHERE "
                    + Const.GROUPING_ID + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                grouping.setId(resultSet.getLong(1));
                grouping.setName(resultSet.getString(2));
                grouping.setSpecification(resultSet.getString(3));
                grouping.setLocation(resultSet.getString(4));
                grouping.setAboutGrouping(resultSet.getString(5));
                grouping.setCreationDate(resultSet.getDate(6));
                grouping.setCountry(resultSet.getString(7));
                grouping.setDangerLvl(resultSet.getString(9));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return grouping;
    }

    @Override
    public GroupingDao add(Grouping model) throws SQLException{
        String sqlInto = "INSERT INTO " + Const.CRIMES_TABLE_GROUPING + "(" + Const.GROUPING_NAME + ","
                    + Const.GROUPING_SPEC + "," + Const.GROUPING_LOCATION + ","
                    + Const.GROUPING_ABOUT + "," + Const.GROUPING_DATE + ","
                    + Const.GROUPING_CITYDATE + "," + Const.GROUPING_MEMBERS + ","
                    + Const.GROUPING_DANGER + "," + Const.GROUPING_WANTED + ","
                    + Const.GROUPING_PHOTO + ")"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        String sqlFrom = "SELECT " + Const.GROUPING_NAME + ", " + Const.GROUPING_SPEC + ", " + Const.GROUPING_LOCATION + " FROM " + Const.CRIMES_TABLE_CRIMINAL +
                " WHERE " + Const.GROUPING_NAME + "=? AND " + Const.GROUPING_SPEC + "=? AND " + Const.GROUPING_LOCATION + "=?";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(sqlFrom);
            preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getSpecification());
            preparedStatement.setString(3, model.getLocation());

            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                preparedStatement = getDbConnection().prepareStatement(sqlInto);
                preparedStatement.setLong(0, model.getId());
                preparedStatement.setString(1, model.getName());
                preparedStatement.setString(2, model.getSpecification());
                preparedStatement.setString(3, model.getLocation());
                preparedStatement.setString(4, model.getAboutGrouping());
                preparedStatement.setDate(5, model.getCreationDate());
                preparedStatement.setString(6, model.getCountry());
                preparedStatement.setString(7, model.getDangerLvl());
                preparedStatement.setString(8, model.getWantedBy());
                Blob blob = dbConnection.createBlob();
                blob.setBytes(1, model.getPhoto());
                preparedStatement.setBlob(9, blob);
                blob.free();

                preparedStatement.executeUpdate();
            }
            else{
                preparedStatement.close();
                dbConnection.close();
            }
        } catch (SQLException e) {
                System.out.println("Error with SQLException");
                e.printStackTrace();
            } catch (ClassNotFoundException c) {
                System.out.println("Error with ClassNotFoundException");
                c.printStackTrace();
            }
        finally {
            try {
                dbConnection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            System.out.println("Added");
        }
        return this;
    }

    @Override
    public GroupingDao update(Grouping model) {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE " + Const.CRIMES_TABLE_GROUPING + " SET " + Const.GROUPING_LOCATION + "=?, " + Const.GROUPING_ABOUT + "=?, "
                + Const.GROUPING_DATE + " =?, " + Const.GROUPING_CITYDATE + "=?, " + Const.GROUPING_MEMBERS + "=?, " + Const.GROUPING_DANGER + "=?, "
                + Const.GROUPING_WANTED + "=?, " + Const.GROUPING_PHOTO + "=? " + " WHERE " + Const.GROUPING_NAME + "=? AND " + Const.GROUPING_SPEC + "=?";
        try{
            preparedStatement = getDbConnection().prepareStatement(sql);

            preparedStatement.setString(1, model.getLocation());
            preparedStatement.setString(2, model.getAboutGrouping());
            preparedStatement.setDate(3, model.getCreationDate());
            preparedStatement.setString(4, model.getCountry());
            preparedStatement.setInt(5, model.getMember());
            preparedStatement.setString(6, model.getDangerLvl());
            preparedStatement.setString(7, model.getWantedBy());
            Blob blob = dbConnection.createBlob();
            blob.setBytes(1, model.getPhoto());
            preparedStatement.setBlob(8, blob);
            blob.free();

            preparedStatement.setString(9, model.getName());
            preparedStatement.setString(10, model.getSpecification());

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
    public GroupingDao delete(Grouping model) {
        Statement statement = null;

        try {
            dbConnection = getDbConnection();
            statement = dbConnection.createStatement();
            String delete = "DELETE FROM " + Const.CRIMES_TABLE_GROUPING + " WHERE " +Const.GROUPING_ID+ " = " + model.getId();
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
    public List<Crime> getCrimes(Grouping model) {
        String sqlCrime = "SELECT " + Const.CRIMINAL_LASTNAME + ", " + Const.CRIMINAL_FORENAME + ", " + Const.CRIMINAL_ALIAS + ", " + Const.CRIMINAL_ID
                + " FROM " + Const.CRIMES_TABLE_CRIMINAL
                + " WHERE " + Const.CRIMINAL_GROUPING + "=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Crime crime;
        try{
            dbConnection = getDbConnection();

            preparedStatement = dbConnection.prepareStatement(sqlCrime);
            preparedStatement.setString(1, model.getName());

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                crime = new Crime();
                crime.setLastname(resultSet.getString(1));
                crime.setFirstname(resultSet.getString(2));
                crime.setAlias(resultSet.getString(3));
                crime.setId(resultSet.getLong(4));
                crimesInGrouping.add(crime);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                dbConnection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        grouping.setCrimes(crimesInGrouping);
        return crimesInGrouping;
    }

    @Override
    public GroupingDao addPrimaryInfo(String Name, String Specification, String Location) {
        grouping.setName(Name);
        grouping.setSpecification(Specification);
        grouping.setLocation(Location);
        return this;
    }

    @Override
    public GroupingDao addSecondaryInfo(String AboutGrouping, Date CreationDate, String Country, String DangerLvl) {
        grouping.setAboutGrouping(AboutGrouping);
        grouping.setCreationDate(CreationDate);
        grouping.setCountry(Country);
        grouping.setDangerLvl(DangerLvl);
        return this;
    }

    @Override
    public GroupingDao getByPrimaryInfo(Grouping model){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.CRIMES_TABLE_GROUPING + " WHERE "
                    + Const.GROUPING_NAME + "=? AND " + Const.GROUPING_SPEC + "=? AND " + Const.GROUPING_LOCATION + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getSpecification());
            preparedStatement.setString(3, model.getLocation());

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                grouping.setId(resultSet.getLong(1));
                grouping.setName(resultSet.getString(2));
                grouping.setSpecification(resultSet.getString(3));
                grouping.setLocation(resultSet.getString(4));
                grouping.setAboutGrouping(resultSet.getString(5));
                grouping.setCreationDate(resultSet.getDate(6));
                grouping.setCountry(resultSet.getString(7));
                grouping.setMember(resultSet.getInt(8));
                grouping.setDangerLvl(resultSet.getString(9));
                Blob blob = resultSet.getBlob("g_photo");
                InputStream inputStream = blob.getBinaryStream();
                grouping.setPhoto(inputStream.readAllBytes());
                blob.free();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        System.out.println("Succes by PrimaryInfo g");
        return this;
    }

    @Override
    public Grouping build() {
        return grouping;
    }
}
