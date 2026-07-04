package com.billingportal.servlet;

import java.io.IOException;

import com.billingportal.dao.UserDAO;
import com.billingportal.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();

        User user = dao.login(email, password);

        if (user != null) {

            HttpSession session = request.getSession();

            session.setAttribute("clientId", user.getClientId());
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("role", user.getRole());

            response.sendRedirect(request.getContextPath() + "/subscription");

        } else {

            request.setAttribute("error", "Invalid Email or Password");
            request.getRequestDispatcher("/login.jsp").forward(request, response);

        }
    }

}