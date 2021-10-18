package lk.ijse.dep.authbackend.api;

import jakarta.annotation.Resource;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep.authbackend.dto.UserDTO;
import lk.ijse.dep.authbackend.service.UserService;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "UserServlet", value = "/users")
public class UserServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/cp")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getContentType() == null || !request.getContentType().startsWith("application/json")){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Request (only support JSON)");
            return;
        }

        try {
            UserDTO userDTO = JsonbBuilder.create().fromJson(request.getReader(), UserDTO.class);

            if(userDTO.getUsername() == null || userDTO.getUsername().trim().length() < 3){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Username");
                return;
            }else if(userDTO.getFullName() == null || !userDTO.getFullName().trim().matches("^[A-Za-z]+$")){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Full Name");
                return;
            }else if(userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Password");
                return;
            }

            try (Connection connection = dataSource.getConnection()){
                UserService userService = new UserService(connection);
                userService.saveUser(userDTO);

                response.setStatus(HttpServletResponse.SC_CREATED);
            }catch (SQLException e){
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
            }

        }catch (JsonbException e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        }
    }
}
