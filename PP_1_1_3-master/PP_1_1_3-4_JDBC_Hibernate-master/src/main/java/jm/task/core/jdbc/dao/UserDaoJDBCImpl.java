package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.getConnectionToDataBase();
    Statement statement;
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String mySQL = "CREATE TABLE IF NOT EXISTS users(" +
                "id int not null auto_increment," +
                "name VARCHAR(50)," +
                "lastName VARCHAR(50)," +
                "age int," + "PRIMARY KEY(id))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(mySQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String mySQL = "DROP TABLE IF EXISTS users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(mySQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String mySQL = "INSERT INTO users(name,lastname,age) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement=connection.prepareStatement(mySQL)) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name +" добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String mySQL = "DELETE FROM users WHERE id=?";
        try (PreparedStatement preparedStatement=connection.prepareStatement(mySQL)) {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String mySQL = "SELECT * FROM users";
        List <User> list = new ArrayList<>();
        try (PreparedStatement preparedStatement=connection.prepareStatement(mySQL)) {
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                User user = new User();
                user.setId(res.getLong("id"));
                user.setName(res.getString("name"));
                user.setLastName(res.getString("lastName"));
                user.setAge(res.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        String mySQL = "DELETE FROM users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(mySQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}