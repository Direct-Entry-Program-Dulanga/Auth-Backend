package lk.ijse.dep.authbackend.service;

import lk.ijse.dep.authbackend.dto.UserDTO;

import java.sql.*;

public class UserService {
    private Connection connection;

    public UserService(Connection connection){
        this.connection = connection;
    }

    public void saveUser(UserDTO user){
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("INSERT INTO user VALUES (?, ?, ?)");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getPassword());

            if(stmt.executeUpdate() == 1){
                   throw  new RuntimeException("Failed to save the user");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDTO authenticate(String username, String password){
        try {
            Statement stm = connection.createStatement();
            String sql = String.format("SELECT full_name FROM user WHERE username='%s' AND password='%s'", username, password);
            ResultSet rst = stm.executeQuery(sql);

            if (rst.next()){
                return new UserDTO(username, password, rst.getString("full_name"));
            }

            throw new RuntimeException("Invalid login credentials");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to authenticate", e);
        }


    }
}
