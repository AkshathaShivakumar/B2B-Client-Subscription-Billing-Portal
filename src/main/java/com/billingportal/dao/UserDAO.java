package com.billingportal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

import com.billingportal.model.User;

public class UserDAO {

    public boolean registerUser(User user) {

        String sql = "INSERT INTO users(client_id,email,password_hash,role) VALUES(?,?,?,?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String hashedPassword = BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt());

            ps.setInt(1, user.getClientId());
            ps.setString(2, user.getEmail());
            ps.setString(3, hashedPassword);
            ps.setString(4, user.getRole());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public User login(String email, String password) {

        String sql = "SELECT * FROM users WHERE email=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    String dbPassword = rs.getString("password_hash");

                    if (BCrypt.checkpw(password, dbPassword)) {

                        User user = new User();

                        user.setUserId(rs.getInt("user_id"));
                        user.setClientId(rs.getInt("client_id"));
                        user.setEmail(rs.getString("email"));
                        user.setRole(rs.getString("role"));

                        return user;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}