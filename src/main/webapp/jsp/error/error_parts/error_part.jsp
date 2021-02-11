<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty requestScope.errorMessage}">
    <p style="color: red; font-size: 15px; text-align: center;border: 1px solid red;padding: 15px;">${requestScope.errorMessage}</p>
</c:if>