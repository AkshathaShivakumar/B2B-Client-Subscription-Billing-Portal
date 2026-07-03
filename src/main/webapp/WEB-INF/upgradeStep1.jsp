<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Choose a plan — Billing</title>
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
        <h1>Choose a plan</h1>

        <div class="stepper">
            <div class="step current"><span class="dot">1</span><span class="label">Choose</span></div>
            <div class="rule"></div>
            <div class="step"><span class="dot">2</span><span class="label">Review</span></div>
            <div class="rule"></div>
            <div class="step"><span class="dot">3</span><span class="label">Confirmed</span></div>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="error-banner"><c:out value="${errorMessage}"/></div>
        </c:if>

        <c:if test="${not empty currentSubscription}">
            <div class="tier-grid">
                <c:forEach var="entry" items="${allTiers}">
                    <c:set var="isCurrent" value="${entry.key == currentSubscription.tier}" />
                    <form method="post" action="${pageContext.request.contextPath}/upgrade/step1">
                        <div class="tier-option ${isCurrent ? 'current' : ''}">
                            <div>
                                <span class="name"><c:out value="${entry.key}"/></span>
                                <c:if test="${isCurrent}"><span class="tag">Current</span></c:if>
                                <div class="specs">
                                    <fmt:formatNumber value="${entry.value.apiCallQuota}"/> API calls &middot;
                                    <fmt:formatNumber value="${entry.value.storageQuotaMb}"/> MB storage
                                </div>
                            </div>
                            <div style="text-align:right;">
                                <div class="price">
                                    $<fmt:formatNumber value="${entry.value.monthlyPrice}" minFractionDigits="2"/>
                                    <span class="period">/mo</span>
                                </div>
                                <input type="hidden" name="selectedTier" value="${entry.key}" />
                                <button class="btn ${isCurrent ? 'btn-secondary' : 'btn-primary'}"
                                        style="margin-top:8px;" type="submit" ${isCurrent ? 'disabled' : ''}>
                                    <c:choose>
                                        <c:when test="${isCurrent}">Current plan</c:when>
                                        <c:otherwise>Select</c:otherwise>
                                    </c:choose>
                                </button>
                            </div>
                        </div>
                    </form>
                </c:forEach>
            </div>
        </c:if>

        <a class="back-link" href="${pageContext.request.contextPath}/subscription">&larr; Back to my subscription</a>
    </div>
</body>
</html>
