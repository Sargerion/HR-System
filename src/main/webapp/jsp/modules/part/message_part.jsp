<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty requestScope.confirmMessage}">
    <p class="mist" style="color: seagreen">${requestScope.confirmMessage}</p>
</c:if>
<c:if test="${not empty requestScope.confirmMailMessage}">
    <p class="mist" style="color: seagreen">${requestScope.confirmMailMessage}</p>
</c:if>