package com.billingportal.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {
	    "/subscription",
	    "/upgradeStep1",
	    "/upgradeStep2",
	    "/confirmUpgrade",
	    "/logout"
	})
public class AuthFilter extends HttpFilter implements Filter {

    @Override
    public void doFilter(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("clientId") != null) {

            chain.doFilter(request, response);

        } else {

            request.setAttribute("error", "Please login to continue.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);

        }
    }
}