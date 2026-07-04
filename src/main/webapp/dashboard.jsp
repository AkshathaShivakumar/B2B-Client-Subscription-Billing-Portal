<%@ page import="java.util.Map, java.util.List, com.billingportal.model.Usage" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Usage Dashboard</title>
    <style>
        * { box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            margin: 0;
            padding: 40px;
            background: linear-gradient(135deg, #f5f7fa 0%, #e9edf5 100%);
            min-height: 100vh;
        }
        .container { max-width: 720px; margin: 0 auto; }

        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-end;
            margin-bottom: 28px;
        }
        .page-header h1 {
            font-size: 26px;
            color: #1a1a2e;
            margin: 0;
        }
        .page-header .subtitle {
            color: #6b7280;
            font-size: 14px;
            margin-top: 4px;
        }
        .badge {
            background: #eef2ff;
            color: #4338ca;
            font-size: 12px;
            font-weight: 600;
            padding: 6px 12px;
            border-radius: 20px;
        }

        .metric-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 18px;
            margin-bottom: 30px;
        }
        @media (max-width: 600px) {
            .metric-grid { grid-template-columns: 1fr; }
        }

        .metric-card {
            background: white;
            border-radius: 14px;
            padding: 22px;
            box-shadow: 0 4px 16px rgba(20, 20, 50, 0.06);
            border: 1px solid #eef0f4;
        }
        .metric-top {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 6px;
        }
        .metric-icon {
            width: 34px;
            height: 34px;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 16px;
            color: white;
        }
        .icon-api { background: #6366f1; }
        .icon-storage { background: #0ea5e9; }

        .metric-name {
            font-size: 13px;
            font-weight: 700;
            color: #374151;
            letter-spacing: 0.3px;
            text-transform: uppercase;
        }
        .metric-status {
            font-size: 11px;
            font-weight: 700;
            padding: 3px 9px;
            border-radius: 20px;
        }
        .status-ok { background: #dcfce7; color: #16a34a; }
        .status-warn { background: #fef3c7; color: #d97706; }
        .status-critical { background: #fee2e2; color: #dc2626; }

        .metric-numbers {
            font-size: 22px;
            font-weight: 700;
            color: #111827;
            margin: 10px 0 2px 0;
        }
        .metric-numbers span {
            font-size: 14px;
            font-weight: 400;
            color: #9ca3af;
        }

        .bar-container {
            background: #f1f2f6;
            border-radius: 8px;
            width: 100%;
            height: 10px;
            margin-top: 12px;
            overflow: hidden;
        }
        .bar-fill {
            height: 10px;
            border-radius: 8px;
            transition: width 0.4s ease;
        }
        .fill-ok { background: linear-gradient(90deg, #34d399, #10b981); }
        .fill-warn { background: linear-gradient(90deg, #fbbf24, #f59e0b); }
        .fill-critical { background: linear-gradient(90deg, #f87171, #dc2626); }

        .panel {
            background: white;
            border-radius: 14px;
            padding: 22px;
            box-shadow: 0 4px 16px rgba(20, 20, 50, 0.06);
            border: 1px solid #eef0f4;
            margin-bottom: 24px;
        }
        .panel h2 {
            font-size: 15px;
            color: #374151;
            margin: 0 0 14px 0;
        }
        table { width: 100%; border-collapse: collapse; font-size: 13px; }
        th {
            text-align: left;
            color: #9ca3af;
            font-weight: 600;
            font-size: 11px;
            text-transform: uppercase;
            padding: 8px 6px;
            border-bottom: 1px solid #eef0f4;
        }
        td {
            padding: 10px 6px;
            border-bottom: 1px solid #f5f6f8;
            color: #374151;
        }
        .tag {
            display: inline-block;
            font-size: 11px;
            font-weight: 600;
            padding: 2px 8px;
            border-radius: 5px;
            background: #f3f4f6;
            color: #4b5563;
        }
        .empty {
            color: #9ca3af;
            font-size: 13px;
            padding: 12px 0;
        }

        .actions {
            display: flex;
            gap: 16px;
            font-size: 13px;
        }
        .actions a {
            color: #4f46e5;
            text-decoration: none;
            font-weight: 600;
        }
        .actions a:hover { text-decoration: underline; }
    </style>
</head>
<body>
<div class="container">

    <div class="page-header">
        <div>
            <h1>Usage Dashboard</h1>
            <div class="subtitle">Current billing period consumption</div>
        </div>
        <div class="badge">Acme Corp</div>
    </div>

    <div class="metric-grid">
    <%
        Map<String, Integer> usage = (Map<String, Integer>) request.getAttribute("usage");
        Map<String, Integer> quotas = (Map<String, Integer>) request.getAttribute("quotas");

        if (quotas != null) {
            for (String metric : quotas.keySet()) {
                int used = (usage != null && usage.containsKey(metric)) ? usage.get(metric) : 0;
                int quota = quotas.get(metric);
                int percent = quota > 0 ? Math.min(100, (int) (((double) used / quota) * 100)) : 0;

                String statusClass, fillClass, statusLabel;
                if (percent >= 90) {
                    statusClass = "status-critical"; fillClass = "fill-critical"; statusLabel = "Critical";
                } else if (percent >= 70) {
                    statusClass = "status-warn"; fillClass = "fill-warn"; statusLabel = "Near limit";
                } else {
                    statusClass = "status-ok"; fillClass = "fill-ok"; statusLabel = "Healthy";
                }

                String iconClass = metric.equals("API_CALLS") ? "icon-api" : "icon-storage";
                String iconSymbol = metric.equals("API_CALLS") ? "&#9889;" : "&#128190;";
                String displayName = metric.replace("_", " ");
    %>
        <div class="metric-card">
            <div class="metric-top">
                <div class="metric-icon <%= iconClass %>"><%= iconSymbol %></div>
                <div class="metric-status <%= statusClass %>"><%= statusLabel %></div>
            </div>
            <div class="metric-name"><%= displayName %></div>
            <div class="metric-numbers"><%= used %> <span>/ <%= quota %></span></div>
            <div class="bar-container">
                <div class="bar-fill <%= fillClass %>" style="width: <%= percent %>%;"></div>
            </div>
        </div>
    <%
            }
        }
    %>
    </div>

    <div class="panel">
        <h2>Recent Activity</h2>
        <%
            List<Usage> history = (List<Usage>) request.getAttribute("history");
            if (history != null && !history.isEmpty()) {
        %>
        <table>
            <tr><th>Metric</th><th>Amount</th><th>Recorded At</th></tr>
            <%
                int rowLimit = 0;
                for (Usage u : history) {
                    if (rowLimit >= 8) break; // keep it short for the demo view
            %>
            <tr>
                <td><span class="tag"><%= u.getMetricType() %></span></td>
                <td><%= u.getAmount() %></td>
                <td><%= u.getRecordedAt() %></td>
            </tr>
            <%
                    rowLimit++;
                }
            %>
        </table>
        <% } else { %>
            <div class="empty">No usage recorded yet.</div>
        <% } %>
    </div>

    <div class="actions">
        <a href="dashboard">&#8635; Refresh</a>
    </div>

</div>
</body>
</html>