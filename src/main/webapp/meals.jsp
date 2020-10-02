<%--@elvariable id="meal" type=""--%>
<%--
  Created by IntelliJ IDEA.
  User: zaki
  Date: 02.10.2020
  Time: 0:18
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<hr>
<h1>
    <div align="center">
        <table border="1" cellpadding="15">
            <caption>MealTo list</caption>
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
            </tr>
            <%--@elvariable id="mealsToList" type="java.util.List"--%>
            <c:forEach items="${mealsToList}" var="mealTo">
                <c:set var="color" value="red"/>
                <c:if test="${!mealTo.excess}">
                    <c:set var="color" value="green"/>
                </c:if>
                <tr>
                    <td style="color: ${color}"><javatime:format value="${mealTo.dateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
                    <td style="color: ${color}"><c:out value="${mealTo.description}"/></td>
                    <td style="color: ${color}"><c:out value="${mealTo.calories}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</h1>
</body>
</html>
