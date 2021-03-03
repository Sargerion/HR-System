<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty requestScope.errorMessage}">
    <p style="color: red; font-size: 15px; text-align: center;border: 1px solid red;padding: 15px;">${requestScope.errorMessage}</p>
</c:if>

<c:if test="${not empty requestScope.error_reg_fail}">
    <p style="color: red; font-size: 13px; text-align: center;border: 1px solid red;padding: 10px;">${requestScope.error_reg_fail}</p>
</c:if>
<c:if test="${not empty requestScope.error_login}">
    <p style="color: red; font-size: 13px; text-align: center;border: 1px solid red;padding: 10px;">${requestScope.error_login}</p>
</c:if>
<c:if test="${not empty requestScope.error_dif_passws}">
    <p style="color: red; font-size: 13px; text-align: center;border: 1px solid red;padding: 10px;">${requestScope.error_dif_passws}</p>
</c:if>

<c:if test="${not empty sessionScope.errorMessage}">
    <p style="color: red; font-size: 15px; text-align: center;border: 1px solid red;padding: 15px;">${sessionScope.errorMessage}</p>
</c:if>
<c:remove var="errorMessage" scope="session"/>

<c:if test="${not empty sessionScope.error_duplicate_company_name}">
    <p style="color: red; font-size: 15px; text-align: center;border: 1px solid red;padding: 15px;">${sessionScope.error_duplicate_company_name}</p>
</c:if>
<c:remove var="error_duplicate_company_name" scope="session"/>
<c:if test="${not empty sessionScope.error_duplicate_company_hr_login}">
    <p style="color: red; font-size: 15px; text-align: center;border: 1px solid red;padding: 15px;">${sessionScope.error_duplicate_company_hr_login}</p>
</c:if>
<c:remove var="error_duplicate_company_hr_login" scope="session"/>