<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/topjava.common.js" defer></script>
<script type="text/javascript" src="resources/js/topjava.meals.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="meal.title"/></h3>
        <%--https://getbootstrap.com/docs/4.0/components/card/--%>
        <div class="card border-dark">
            <div class="card-body pb-0">
                <form id="filter">
                    <div class="row">
                        <div class="col-3">
                            <label for="startDate"><spring:message code="meal.startDate"/></label>
                            <input class="form-control" type="date" name="startDate" id="startDate">
                        </div>
                        <div class="col-3">
                            <label for="endDate"><spring:message code="meal.endDate"/></label>
                            <input class="form-control" type="date" name="endDate" id="endDate">
                        </div>
                        <div class="offset-2 col-2">
                            <label for="startTime"><spring:message code="meal.startTime"/></label>
                            <input class="form-control" type="time" name="startTime" id="startTime">
                        </div>
                        <div class="col-2">
                            <label for="endTime"><spring:message code="meal.endTime"/></label>
                            <input class="form-control" type="time" name="endTime" id="endTime">
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-footer text-right">
                <button class="btn btn-dark" onclick="clearFilter()">
                    <span class="fa fa-remove"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button class="btn btn-primary" onclick="updateFilteredTable()">
                    <span class="fa fa-filter"></span>
                    <spring:message code="meal.filter"/>
                </button>
            </div>
        </div>
        <br/>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="common.add"/>
        </button>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="meal.dateTime"/> </th>
                <th><spring:message code="meal.description"/> </th>
                <th><spring:message code="meal.calories"/> </th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
                <tr data-mealExcess="${meal.excess}">
                    <td>
                            <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                            <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                            <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                            ${fn:formatDateTime(meal.dateTime)}
                    </td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a><span class="fa fa-pencil"></span></a></td>
                    <td><a onclick="deleteRow(${meal.id})"><span class="fa fa-remove"></span></a></td>
<%--                    <td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>--%>
<%--                    <td><a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a></td>--%>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<%--<section>--%>
<%--    <h3><spring:message code="meal.title"/></h3>--%>

<%--    <form method="get" action="meals/filter">--%>
<%--        <dl>--%>
<%--            <dt><spring:message code="meal.startDate"/>:</dt>--%>
<%--            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>--%>
<%--        </dl>--%>
<%--        <dl>--%>
<%--            <dt><spring:message code="meal.endDate"/>:</dt>--%>
<%--            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>--%>
<%--        </dl>--%>
<%--        <dl>--%>
<%--            <dt><spring:message code="meal.startTime"/>:</dt>--%>
<%--            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>--%>
<%--        </dl>--%>
<%--        <dl>--%>
<%--            <dt><spring:message code="meal.endTime"/>:</dt>--%>
<%--            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>--%>
<%--        </dl>--%>
<%--        <button type="submit"><spring:message code="meal.filter"/></button>--%>
<%--    </form>--%>
<%--    <hr>--%>
<%--    <a href="meals/create"><spring:message code="meal.add"/></a>--%>
<%--    <hr>--%>
<%--    <table border="1" cellpadding="8" cellspacing="0">--%>
<%--        <thead>--%>
<%--        <tr>--%>
<%--            <th><spring:message code="meal.dateTime"/></th>--%>
<%--            <th><spring:message code="meal.description"/></th>--%>
<%--            <th><spring:message code="meal.calories"/></th>--%>
<%--            <th></th>--%>
<%--            <th></th>--%>
<%--        </tr>--%>
<%--        </thead>--%>
<%--        <c:forEach items="${meals}" var="meal">--%>
<%--            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>--%>
<%--            <tr data-mealExcess="${meal.excess}">--%>
<%--                <td>--%>
<%--                        &lt;%&ndash;${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}&ndash;%&gt;--%>
<%--                        &lt;%&ndash;<%=TimeUtil.toString(meal.getDateTime())%>&ndash;%&gt;--%>
<%--                        &lt;%&ndash;${fn:replace(meal.dateTime, 'T', ' ')}&ndash;%&gt;--%>
<%--                        ${fn:formatDateTime(meal.dateTime)}--%>
<%--                </td>--%>
<%--                <td>${meal.description}</td>--%>
<%--                <td>${meal.calories}</td>--%>
<%--                <td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>--%>
<%--                <td><a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a></td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>
<%--    </table>--%>
<%--</section>--%>
<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="meal.add"/></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="meal.dateTime"/></label>
                        <input type="datetime-local" class="form-control" id="dateTime" name="dateTime"
                               placeholder="<spring:message code="meal.dateTime"/>">
                    </div>

                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message code="meal.description"/></label>
                        <input type="text" class="form-control" id="description" name="description"
                               placeholder="<spring:message code="meal.description"/>">
                    </div>

                    <div class="form-group">
                        <label for="calories" class="col-form-label"><spring:message code="meal.calories"/></label>
                        <input type="number" class="form-control" id="calories" name="calories"
                               placeholder="1000">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>