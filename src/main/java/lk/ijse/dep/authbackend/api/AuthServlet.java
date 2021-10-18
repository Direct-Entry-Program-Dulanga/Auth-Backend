package lk.ijse.dep.authbackend.api;

import jakarta.annotation.Resource;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep.authbackend.dto.UserDTO;
import lk.ijse.dep.authbackend.security.SecurityContext;
import lk.ijse.dep.authbackend.service.UserService;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "AuthServlet", value = "/authenticate", loadOnStartup = 0)
public class AuthServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/cp")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getContentType() == null || !request.getContentType().startsWith("application/x-www-form-urlencoded")){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request (Not a valid content-type)");
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bad login credentials");
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            UserService userService = new UserService(connection);

            try {
                UserDTO user = userService.authenticate(username, password);
                SecurityContext.setPrincipal(user);
                response.setContentType("application/json");
                response.getWriter().println(JsonbBuilder.create().toJson(user));
            }catch (RuntimeException e){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bad login credentials");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
