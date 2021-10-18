package lk.ijse.dep.authbackend.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.setHeader("Access-Control-Allow-Origin", "*");

        if(req.getMethod().equals("OPTIONS")){
            resp.setHeader("Access-Control-Allow-Origin", "Content-Type");
            resp.setHeader("Access-Control-Allow-Methods", "POST, PUT, DELETE, GET");

        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
