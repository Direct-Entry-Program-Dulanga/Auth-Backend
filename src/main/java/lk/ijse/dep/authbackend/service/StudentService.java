package lk.ijse.dep.authbackend.service;

import lk.ijse.dep.authbackend.dto.StudentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentService {

    private Connection connection;

    public StudentService(Connection connection){
        this.connection = connection;
    }

    public String saveStudent(StudentDTO student){
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO student (name, address, username) VALUES (?, ?, ?)");
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getAddress());
            //Todo: Inject username somehow

            if(stmt.executeUpdate() == 1){
                ResultSet rst = stmt.getGeneratedKeys();
                rst.next();
                return String.format("SID-%03d", rst.getInt(1));
            }else{
                throw new RuntimeException("Failed to save the student");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<StudentDTO> getAllStudents(){
        return null;
    }
}
