<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/libs/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Billing History</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 40px; background-color: #f4f6f9; color: #333; }
        .container { max-width: 1000px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
        h2 { color: #0056b3; margin-bottom: 5px; }
        p { color: #666; margin-bottom: 25px; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { padding: 14px 12px; text-align: left; border-bottom: 1px solid #e0e0e0; }
        th { background-color: #f8f9fa; color: #495057; font-weight: 600; text-transform: uppercase; font-size: 12px; letter-spacing: 0.5px; }
        tr:hover { background-color: #fcfcfc; }
        .btn { padding: 8px 14px; background-color: #28a745; color: white; text-decoration: none; border-radius: 4px; font-size: 13px; font-weight: bold; display: inline-block; }
        .btn:hover { background-color: #218838; }
        .status-badge { padding: 4px 8px; background-color: #e2f0d9; color: #385723; border-radius: 4px; font-size: 12px; font-weight: bold; }
    </style>
</head>
<body>

<div class="container">
    <h2>Invoices & Billing History</h2>
    <p>Viewing records for Client ID Reference: <strong>${sessionScope.clientId}</strong></p>

    <table>
        <thead>
        <tr>
            <th>Invoice ID</th>
            <th>Billing Period</th>
            <th>Amount Due</th>
            <th>Generated On</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <!-- This loops through the invoiceList sent by your InvoiceListServlet -->
        <c:forEach var="invoice" items="${invoiceList}">
            <tr>
                <td><strong>INV-${invoice.invoiceId}</strong></td>
                <td>${invoice.billingPeriod}</td>
                <td>$${invoice.amount}</td>
                <td>${invoice.generatedAt}</td>
                <td>
                    <!-- Clicking this will trigger your upcoming PDF download servlet -->
                    <a href="download-invoice?id=${invoice.invoiceId}" class="btn">Download PDF</a>
                </td>
            </tr>
        </c:forEach>

        <!-- If the database table happens to be empty -->
        <c:if test="${empty invoiceList}">
            <tr>
                <td colspan="5" style="text-align: center; color: #999; padding: 30px;">No invoices found for this account.</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>

</body>
</html>