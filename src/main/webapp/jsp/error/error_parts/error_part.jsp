<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty requestScope.errorMessage}">
    <p style="color: red; font-size: 20px; text-align: center;border: 1px solid red;padding: 35px;">${requestScope.errorMessage}</p>
</c:if>