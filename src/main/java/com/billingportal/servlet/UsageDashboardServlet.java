package com.billingportal.servlet;

import com.billingportal.dao.UsageDAO;
import com.billingportal.model.Usage;
import com.billingportal.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/dashboard")
public class UsageDashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Map<String, Integer> QUOTAS = Map.of(
        "API_CALLS", 10000,
        "STORAGE_MB", 5000
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int clientId = SessionUtil.getCurrentClientId(req);

        try {
            UsageDAO dao = new UsageDAO();

            dao.insertUsageRecord(clientId, "API_CALLS", 1);

            Map<String, Integer> usage = new java.util.HashMap<>(dao.getUsageByClient(clientId));

            int recordCount = dao.getRecordCount(clientId);
            usage.put("STORAGE_MB", recordCount * 4);

            List<Usage> history = dao.getUsageHistory(clientId);
            req.setAttribute("usage", usage);
            req.setAttribute("quotas", QUOTAS);
            req.setAttribute("history", history);
            req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Could not load usage dashboard: " + e.getMessage());
        }
    }
}