<%--@elvariable id="meal" type=""--%>
<%--
  Created by IntelliJ IDEA.
  User: zaki
  Date: 02.10.2020
  Time: 0:18
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>
    <%--@elvariable id="mealsList" type="java.util.List"--%>
    <h1>Meals list without filters</h1>
    <c:forEach items="${mealsList}" var="meal">
        <p>${meal}</p>
    </c:forEach>
<%--    Meals--%>
</h2>
<hr>
<h2>
    <h1>Meals list with excess filter</h1>
    <%--@elvariable id="mealsToList" type="java.util.List"--%>
    <c:forEach items="${mealsToList}" var="mealTo">
        <p>${mealTo}</p>
    </c:forEach>
</h2>
</body>
</html>
