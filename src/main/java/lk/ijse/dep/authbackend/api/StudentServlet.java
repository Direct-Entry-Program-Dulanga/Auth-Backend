package lk.ijse.dep.authbackend.api;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "StudentServlet", value = "/students", loadOnStartup = 0)
public class StudentServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {

            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/cp");
                System.out.println(dataSource.getConnection());
            } catch (NamingException | SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Do Get");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Do Post");
    }
}
