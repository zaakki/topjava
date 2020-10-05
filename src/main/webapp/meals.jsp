<%--
  Created by IntelliJ IDEA.
  User: zaki
  Date: 02.10.2020
  Time: 0:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        .normal {
            color: forestgreen;
        }

        .excess {
            color: orangered;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h1>
    <div align="left">
        <table border="1" cellpadding="8">
            <caption style="font-size: large;color: chocolate;font-style: italic">MealTo list</caption>
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
                <tr class="${meal.excess ? 'excess' : 'normal'}">
                    <td ><javatime:format value="${meal.dateTime}"
                                                                 pattern="yyyy-MM-dd HH:mm"/></td>
                    <td><c:out value="${meal.description}"/></td>
                    <td><c:out value="${meal.calories}"/></td>
                    <td><button style="bottom: auto;background-color: saddlebrown "><a href="meals?action=update&id=${meal.id}" style="font-style: italic; color: greenyellow">Update</a></button></td>
                    <td><button style="bottom: auto;background-color: blueviolet"><a href="meals?action=delete&id=${meal.id}" style="font-style: italic; color:orangered ">Delete</a></button></td>
                </tr>
            </c:forEach>

        </table>
        <hr>
        <h3><button style="background-color: cyan"><a href="meals?action=create" style="font-style: italic;font-size: medium;color: darkolivegreen;">Create new</a> </button></h3>
    </div>
</h1>
</body>
</html>
