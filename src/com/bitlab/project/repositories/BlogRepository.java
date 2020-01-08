package com.bitlab.project.repositories;

import com.bitlab.project.db.DBConnection;
import com.bitlab.project.entities.Blogs;
import com.bitlab.project.entities.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BlogRepository {

    private DBConnection dbConnection;

    public BlogRepository() {
        dbConnection = new DBConnection();
    }

    public boolean addBlog(Blogs blog) {

        int rows = 0;

        try {

            PreparedStatement statement = dbConnection.getConnection().prepareStatement("" +
                    "" +
                    "INSERT INTO blogs(user_id, title, short_content, content, post_date) " +
                    "VALUES (?, ?, ?, ?, NOW())");

            statement.setLong(1, blog.getUser().getId());
            statement.setString(2, blog.getTitle());
            statement.setString(3, blog.getShortContent());
            statement.setString(4, blog.getContent());

            rows = statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;

    }

    public ArrayList<Blogs> getAllBlogs() {

        ArrayList<Blogs> blogs = new ArrayList<>();

        try {

            PreparedStatement statement =
                    dbConnection.getConnection().prepareStatement("" +
                            "" +
                            "SELECT b.id, b.title, b.short_content, b.content, u.full_name, b.user_id, u.email, b.post_date " +
                            "FROM blogs b " +
                            "LEFT OUTER JOIN users u ON u.id = b.user_id " +
                            "ORDER BY b.post_date DESC ");

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                blogs.add(new Blogs(
                        resultSet.getLong("id"),
                        new Users(
                                resultSet.getLong("user_id"),
                                resultSet.getString("email"),
                                null,
                                resultSet.getString("full_name")
                                ),
                        resultSet.getString("title"),
                        resultSet.getString("short_content"),
                        resultSet.getString("content"),
                        resultSet.getDate("post_date")
                ));
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return blogs;
    }

    public Blogs getBlog(Long id) {

        Blogs blog = null;

        try {

            PreparedStatement statement =
                    dbConnection.getConnection().prepareStatement("" +
                            "" +
                            "SELECT b.id, b.title, b.short_content, b.content, u.full_name, b.user_id, u.email, b.post_date " +
                            "FROM blogs b " +
                            "LEFT OUTER JOIN users u ON u.id = b.user_id " +
                            "WHERE b.id = ? ");
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                blog = new Blogs(
                        resultSet.getLong("id"),
                        new Users(
                                resultSet.getLong("user_id"),
                                resultSet.getString("email"),
                                null,
                                resultSet.getString("full_name")
                        ),
                        resultSet.getString("title"),
                        resultSet.getString("short_content"),
                        resultSet.getString("content"),
                        resultSet.getDate("post_date")
                );
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return blog;
    }

    public boolean updateBlog(Blogs blog) {

        int rows = 0;

        try {

            PreparedStatement statement = dbConnection.getConnection().prepareStatement("" +
                    "UPDATE blogs SET title = ?, short_content = ?, content = ? " +
                    "WHERE id = ? AND user_id = ?");

            statement.setString(1, blog.getTitle());
            statement.setString(2, blog.getShortContent());
            statement.setString(3, blog.getContent());
            statement.setLong(4, blog.getId());
            statement.setLong(5, blog.getUser().getId());

            rows = statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;

    }

    public boolean deleteBlog(Long id, Long userId) {

        int rows = 0;

        try {

            PreparedStatement statement = dbConnection.getConnection().prepareStatement("" +
                    "DELETE FROM blogs " +
                    "WHERE id = ? AND user_id = ?");

            statement.setLong(1, id);
            statement.setLong(2, userId);

            rows = statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;

    }
}
