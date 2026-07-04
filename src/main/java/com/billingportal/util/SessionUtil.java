package com.billingportal.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    private SessionUtil() {
    }

    public static Integer getCurrentClientId(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }

        return (Integer) session.getAttribute("clientId");
    }

    public static Integer getCurrentUserId(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }

        return (Integer) session.getAttribute("userId");
    }

    public static String getCurrentUserEmail(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }

        return (String) session.getAttribute("email");
    }
}