<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register Company</title>

<style>
body{
    font-family:Arial;
    background:#f2f2f2;
}

.container{
    width:400px;
    margin:60px auto;
    background:white;
    padding:30px;
    border-radius:10px;
    box-shadow:0 0 10px gray;
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
    background:#0066cc;
    color:white;
    border:none;
    cursor:pointer;
}

button:hover{
    background:#004999;
}
</style>

</head>

<body>

<div class="container">

<h2>Company Registration</h2>

<form action="register" method="post">

<label>Company Name</label>
<input type="text" name="companyName" required>

<label>Email</label>
<input type="email" name="email" required>

<label>Password</label>
<input type="password" name="password" required>

<button type="submit">
Register
</button>

</form>

<br>

Already have an account?

<a href="login.jsp">Login</a>

</div>

</body>
</html>