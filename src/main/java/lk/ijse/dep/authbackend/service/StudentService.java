package lk.ijse.dep.authbackend.service;

import lk.ijse.dep.authbackend.dto.StudentDTO;
import lk.ijse.dep.authbackend.security.SecurityContext;

import java.sql.*;
import java.util.ArrayList;
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
            stmt.setString(3, SecurityContext.getPrincipal().getUsername());
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
        List<StudentDTO> students = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT id, name, address FROM student");

            while(rst.next()){
                students.add(new StudentDTO(String.format("SID-%03d", rst.getInt("id")),
                rst.getString("name"),
                rst.getString("address")));
            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
