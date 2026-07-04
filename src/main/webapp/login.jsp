<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

<style>

body{
    background:#f2f2f2;
    font-family:Arial;
}

.container{

    width:380px;
    margin:80px auto;
    background:white;
    padding:30px;
    border-radius:10px;
    box-shadow:0px 0px 10px gray;

}

input{

    width:100%;
    padding:10px;
    margin-top:10px;
    margin-bottom:15px;

}

button{

    width:100%;
    padding:10px;
    background:green;
    color:white;
    border:none;
    cursor:pointer;

}

button:hover{

    background:darkgreen;

}

</style>

</head>

<body>

<div class="container">

<c:if test="${not empty error}">
    <div style="
        background-color:#f8d7da;
        color:#721c24;
        border:1px solid #f5c6cb;
        padding:10px;
        margin-bottom:15px;
        border-radius:5px;
        text-align:center;">
        ${error}
    </div>
</c:if>

<h2>Login</h2>

<form action="login" method="post">

Email

<input type="email" name="email" required>

Password

<input type="password" name="password" required>

<button type="submit">

Login

</button>

</form>

<br>

<a href="register.jsp">

Create New Account

</a>

</div>

</body>

</html>