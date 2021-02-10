<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty requestScope.confirmMessage}">
    <p style="color: deepskyblue; font-size: 20px; text-align: center;border: 1px solid green;padding: 35px;">${requestScope.confirmMessage}</p>
</c:if>
<c:if test="${not empty requestScope.confirmMailMessage}">
    <p style="color: deepskyblue; font-size: 20px; text-align: center;border: 1px solid green;padding: 35px;">${requestScope.confirmMailMessage}</p>
</c:if>