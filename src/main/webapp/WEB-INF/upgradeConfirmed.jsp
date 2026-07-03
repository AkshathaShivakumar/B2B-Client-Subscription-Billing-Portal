<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Plan updated — Billing</title>
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
        <p class="eyebrow">Change plan</p>
        <h1>All set</h1>

        <div class="stepper">
            <div class="step done"><span class="dot">&#10003;</span><span class="label">Choose</span></div>
            <div class="rule"></div>
            <div class="step done"><span class="dot">&#10003;</span><span class="label">Review</span></div>
            <div class="rule"></div>
            <div class="step current"><span class="dot">&#10003;</span><span class="label">Confirmed</span></div>
        </div>

        <div class="success-banner">
            &#10003; Your plan is now <c:out value="${newSubscription.tier}"/>
        </div>

        <div class="card">
            <div class="review-row">
                <span class="label">New billing period ends</span>
                <span class="value"><fmt:formatDate value="${newSubscription.currentPeriodEnd}" pattern="MMM d, yyyy"/></span>
            </div>
            <div class="btn-row">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/subscription">Back to my subscription</a>
            </div>
        </div>
    </div>
</body>
</html>
