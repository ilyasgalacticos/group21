package com.bitlab.project.repositories;

import com.bitlab.project.db.DBConnection;
import com.bitlab.project.entities.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository {

    private DBConnection dbConnection;

    public UserRepository(){
        dbConnection = new DBConnection();
    }

    public Users getUser(String email){
        Users user = null;
        try{

            PreparedStatement statement = dbConnection.getConnection().prepareStatement("" +
                    "SELECT * FROM users WHERE email = ?");
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                user = new Users(
                        resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("full_name")
                );
            }
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public boolean addUser(Users user){

        int rows = 0;

        try{

            PreparedStatement statement = dbConnection.getConnection().prepareStatement("" +
                    "INSERT INTO users (email, password, full_name) " +
                    "VALUES (?, ?, ?)" +
                    "");

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullName());

            rows = statement.executeUpdate();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return rows>0;
    }

    public boolean updatePassword(Users user){

        int rows = 0;

        try{

            PreparedStatement statement = dbConnection.getConnection().prepareStatement("" +
                    "UPDATE users SET password = ? WHERE id = ? " +
                    "");

            statement.setString(1, user.getPassword());
            statement.setLong(2, user.getId());

            rows = statement.executeUpdate();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return rows>0;
    }

    public boolean updateUser(Users user){

        int rows = 0;

        try{

            PreparedStatement statement = dbConnection.getConnection().prepareStatement("" +
                    "UPDATE users SET full_name = ? WHERE id = ? " +
                    "");

            statement.setString(1, user.getFullName());
            statement.setLong(2, user.getId());

            rows = statement.executeUpdate();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return rows>0;
    }

}
