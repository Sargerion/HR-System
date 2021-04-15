<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty requestScope.confirmMessage}">
    <p style="color: deepskyblue; font-size: 20px; text-align: center;border: 1px solid green;padding: 35px;">${requestScope.confirmMessage}</p>
</c:if>
<c:if test="${not empty requestScope.confirmMailMessage}">
    <p style="position: absolute;right: 732px; text-align: center; color: royalblue; font-size: 15px; border: 1px solid green; padding: 10px;">${requestScope.confirmMailMessage}</p>
</c:if>

<c:if test="${not empty sessionScope.confirmMessage}">
    <p style="color: deepskyblue; font-size: 20px; text-align: center;border: 1px solid green;padding: 35px;">${sessionScope.confirmMessage}</p>
</c:if>
<c:remove var="confirmMessage" scope="session"/>