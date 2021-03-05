<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty requestScope.errorMessage}">
    <p style="color: red; font-size: 15px; text-align: center;border: 1px solid red;padding: 15px;">${requestScope.errorMessage}</p>
</c:if>

<c:if test="${not empty sessionScope.error_register_list}">
    <c:forEach items="${sessionScope.error_register_list}" var="error">
        <p style="color: red; font-size: 13px; text-align: center;border: 1px solid red;padding: 10px;">${error}</p>
    </c:forEach>
</c:if>
<c:remove var="error_register_list" scope="session"/>

<c:if test="${not empty sessionScope.errorMessage}">
    <p style="color: red; font-size: 15px; text-align: center;border: 1px solid red;padding: 15px;">${sessionScope.errorMessage}</p>
</c:if>
<c:remove var="errorMessage" scope="session"/>

<c:if test="${not empty sessionScope.error_add_company_list}">
    <c:forEach items="${sessionScope.error_add_company_list}" var="error">
        <p style="color: red; font-size: 13px; text-align: center;border: 1px solid red;padding: 10px;">${error}</p>
    </c:forEach>
</c:if>
<c:remove var="error_add_company_list" scope="session"/>