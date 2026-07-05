<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Your Subscription — Billing</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@500;600;700&family=Inter:wght@400;500;600&family=IBM+Plex+Mono:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">
</head>
<body>
    <div class="topbar">
        <div class="wordmark">Acme<span>.</span>Billing</div>
        <div class="context">Account &amp; Billing</div>
    </div>
    
    <div style="text-align:right; margin-bottom:15px;">
    <a href="logout"
       style="text-decoration:none;
              background:#dc3545;
              color:white;
              padding:8px 15px;
              border-radius:5px;">
        Logout
    </a>
</div>
    <!-- Generate Invoice Form Stacked Under Logout -->
    <div style="text-align: right; margin-top: 10px;">
        <form action="generate-invoice" method="POST" style="display: inline-block;">
            <!-- Mock data passing the details shown in your active plan dashboard layout -->
            <input type="hidden" name="subscriptionId" value="101" />
            <input type="hidden" name="amount" value="29.00" />
            <input type="hidden" name="billingPeriod" value="Current Period Ending Aug 2026" />

            <button type="submit" style="padding: 8px 16px; background-color: #0056b3; color: white; border: none; border-radius: 4px; font-weight: bold; font-size: 13px; cursor: pointer; transition: background 0.2s;">
                ⚡ Generate Invoice
            </button>
        </form>
    </div>

    <div class="page">
        <p class="eyebrow">Subscription</p>
        <h1>Your plan</h1>

        <c:choose>
            <c:when test="${not empty errorMessage}">
                <div class="error-banner"><c:out value="${errorMessage}"/></div>
            </c:when>
            <c:otherwise>
                <div class="card">
                    <span class="status-pill ${subscription.status == 'ACTIVE' ? 'active' : 'other'}">
                        <c:out value="${subscription.status}"/>
                    </span>
                    <div class="tier-name"><c:out value="${subscription.tier}"/> plan</div>

                    <div class="meta-row">
                        <span class="label">Monthly price</span>
                        <span class="value">$<fmt:formatNumber value="${tierInfo.monthlyPrice}" minFractionDigits="2"/> / mo</span>
                    </div>
                    <div class="meta-row">
                        <span class="label">Current period ends</span>
                        <span class="value"><fmt:formatDate value="${subscription.currentPeriodEnd}" pattern="MMM d, yyyy"/></span>
                    </div>
                    <div class="meta-row">
                        <span class="label">API calls included</span>
                        <span class="value"><fmt:formatNumber value="${tierInfo.apiCallQuota}"/> / mo</span>
                    </div>
                    <div class="meta-row">
                        <span class="label">Storage included</span>
                        <span class="value"><fmt:formatNumber value="${tierInfo.storageQuotaMb}"/> MB</span>
                    </div>

                    <div class="btn-row">
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/upgrade/step1">Change plan</a>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
