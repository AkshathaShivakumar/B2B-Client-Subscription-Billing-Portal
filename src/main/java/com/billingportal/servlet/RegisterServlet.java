package com.billingportal.servlet;

import java.io.IOException;

import com.billingportal.dao.ClientDAO;
import com.billingportal.dao.UserDAO;
import com.billingportal.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	@Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String companyName = request.getParameter("companyName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        ClientDAO clientDAO = new ClientDAO();

        int clientId = clientDAO.createClient(companyName);

        if(clientId==-1){
            response.getWriter().println("User Registration Failed");
            return;
        }

        User user = new User();

        user.setClientId(clientId);
        user.setEmail(email);
        user.setPasswordHash(password);
        user.setRole("ADMIN");

        UserDAO userDAO = new UserDAO();

        boolean success = userDAO.registerUser(user);

        if(success){
            response.sendRedirect("login.jsp");
        }
        else{
            response.getWriter().println("User Registration Failed");
        }
    }
}