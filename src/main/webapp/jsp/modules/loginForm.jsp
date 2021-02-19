<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<body>
<div class="container">
    <form style="position:relative;" name="logInForm" method="post" action="<c:url value="/controller"/>">
        <input type="hidden" name="command" value="log_in">
        <h1><fmt:message key="login_page"/></h1>
        <h5><fmt:message key="login_needs"/></h5>
        <div class="form-group">
            <input type="text" name="login" class="form-control" required pattern="^[a-zA-Z0-9_-]{6,15}$" placeholder="<fmt:message key="login_placeholder"/>"
            value=<c:if test="${requestScope.correct_login != null}">${requestScope.correct_login}</c:if>>
        </div>
        <h5><fmt:message key="password_needs"/></h5>
        <div class="form-group">
            <input type="password" name="password" class="form-control" required pattern="^[a-zA-Z0-9_-]{6,15}$" placeholder="<fmt:message key="password_placeholder"/>">
        </div>
        <input type="submit" class="btn" value="<fmt:message key="login"/>">
        <br/>
        <c:import url="/jsp/modules/part/message_part.jsp"/>
        <br/>
        <c:import url="/jsp/error/error_parts/error_part.jsp"/>
    </form>
</div>
</body>
</html>