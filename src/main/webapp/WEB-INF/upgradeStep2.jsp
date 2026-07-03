<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Review change — Billing</title>
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
        <h1>Review your change</h1>

        <div class="stepper">
            <div class="step done"><span class="dot">&#10003;</span><span class="label">Choose</span></div>
            <div class="rule"></div>
            <div class="step current"><span class="dot">2</span><span class="label">Review</span></div>
            <div class="rule"></div>
            <div class="step"><span class="dot">3</span><span class="label">Confirmed</span></div>
        </div>

        <div class="card">
            <div class="review-row">
                <span class="label">Current plan</span>
                <span class="value"><c:out value="${pendingUpgrade.currentTier}"/></span>
            </div>
            <div class="review-row">
                <span class="label">New plan</span>
                <span class="value"><c:out value="${pendingUpgrade.newTier}"/></span>
            </div>
            <div class="review-row">
                <span class="label">Current price</span>
                <span class="value">$<fmt:formatNumber value="${pendingUpgrade.currentPrice}" minFractionDigits="2"/>/mo</span>
            </div>
            <div class="review-row">
                <span class="label">New price</span>
                <span class="value">$<fmt:formatNumber value="${pendingUpgrade.newPrice}" minFractionDigits="2"/>/mo</span>
            </div>
            <div class="review-row diff">
                <span class="label">Difference</span>
                <span class="value ${pendingUpgrade.priceDifference >= 0 ? 'up' : 'down'}">
                    <c:if test="${pendingUpgrade.priceDifference >= 0}">+</c:if>$<fmt:formatNumber value="${pendingUpgrade.priceDifference}" minFractionDigits="2"/>/mo
                </span>
            </div>

            <p class="hint">This change takes effect immediately and starts a new billing period.</p>

            <form method="post" action="${pageContext.request.contextPath}/upgrade/confirm">
                <div class="btn-row">
                    <button class="btn btn-primary" type="submit">Confirm change</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/upgrade/step1">Choose a different plan</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
