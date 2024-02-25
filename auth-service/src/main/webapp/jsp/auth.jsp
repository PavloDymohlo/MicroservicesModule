<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Authentication</title>
    <script>
        function redirectTo(url) {
            window.location.href = url;
        }
    </script>
</head>
<body>
    <h1>Authentication Page</h1>
    <p>Choose an action:</p>
    <button onclick="redirectTo('http://localhost:8081/order')">Continue without registration</button><br><br>
    <div id="registrationForm" style="display: none;">
        <h2>Registration Form</h2>
        <form action="auth" method="post">
            <input type="hidden" name="action" value="register">
            <label for="regUsername">Username:</label>
            <input type="text" id="regUsername" name="userName" required><br><br>
            <label for="regPassword">Password:</label>
            <input type="password" id="regPassword" name="password" required><br><br>
            <button type="submit">Register</button>
        </form>
    </div>
    <div id="loginForm" style="display: none;">
        <h2>Login Form</h2>
        <form action="auth" method="post">
            <input type="hidden" name="action" value="login">
            <label for="loginUsername">Username:</label>
            <input type="text" id="loginUsername" name="userName" required><br><br>
            <label for="loginPassword">Password:</label>
            <input type="password" id="loginPassword" name="password" required><br><br>
            <button type="submit">Login</button>
        </form>
    </div>
    <button id="registerBtn">Register</button><br><br>
    <button id="loginBtn">Login</button>
    <script>
        document.getElementById('registerBtn').addEventListener('click', function() {
            document.getElementById('registrationForm').style.display = 'block';
            document.getElementById('loginForm').style.display = 'none';
        });
        document.getElementById('loginBtn').addEventListener('click', function() {
            document.getElementById('loginForm').style.display = 'block';
            document.getElementById('registrationForm').style.display = 'none';
        });
    </script>
</body>
</html>