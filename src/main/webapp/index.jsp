<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Billing Portal</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@500;600;700&family=Inter:wght@400;500;600&family=IBM+Plex+Mono:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">
</head>
<body>
    <div class="topbar">
        <div class="wordmark">Acme<span>.</span>Billing</div>
        <div class="context">Account &amp; Billing</div>
    </div>

    <div class="page">
        <p class="eyebrow">Member B module</p>
        <h1>Billing Portal</h1>
        <div class="card">
            <p style="margin-top:0;">This module currently only implements the subscription &amp; checkout slice.</p>
            <div class="btn-row">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/subscription">View my subscription</a>
            </div>
        </div>
    </div>
</body>
</html>
