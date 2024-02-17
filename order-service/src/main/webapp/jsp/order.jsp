<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Form</title>
</head>
<body>
    <h1>Order Form</h1>
    <form method="post" action="order">
        <label for="distance">Distance:</label>
        <input type="number" id="distance" name="distance" required><br><br>

        <label for="time">Time (hours:minutes):</label>
        <input type="time" id="time" name="time" required><br><br>

        <label for="category">Category:</label>
        <select id="category" name="service" required>
            <option value="economy">Economy</option>
            <option value="standard">Standard</option>
            <option value="comfort">Comfort</option>
        </select><br><br>

        <label for="token">Token:</label>
        <input type="text" id="token" name="token"><br><br>

        <button type="submit">Submit Order</button>
    </form>

    <div>
        <c:if test="${not empty price}">
            <p>Price: ${price}</p>
            <p>Car category: ${category}</p>
            <p>Car brand: ${brand}</p>
        </c:if>
    </div>
</body>
</html>
