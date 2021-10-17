package lk.ijse.dep.authbackend.service;

import lk.ijse.dep.authbackend.dto.StudentDTO;
import lk.ijse.dep.authbackend.dto.UserDTO;
import lk.ijse.dep.authbackend.security.SecurityContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private static StudentService studentService;
    private static Connection connection;

    @BeforeAll
    static void beforeAll() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dep_auth_sms", "root", "test");
        studentService = new StudentService(connection);
        SecurityContext.setPrincipal(new UserDTO("admin", "admin", "Administrator"));
    }

    @AfterAll
    static void afterAll() throws SQLException {
        connection.close();
    }

    @Test
    void saveStudent() {
        String studentId = studentService.saveStudent(new StudentDTO("Dulanga", "Galle"));
        assertTrue(studentId.matches("^SID-\\d{3}$"));
    }

    @Test
    void getAllStudent(){
        assertDoesNotThrow(() -> {
            List<StudentDTO> students = studentService.getAllStudents();
            students.forEach(System.out::println);
        });
    }
}