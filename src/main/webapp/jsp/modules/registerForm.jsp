<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<body>
<div class="container" style="max-height: 90%">
    <form style="height: 86%" name="registerForm" method="post" action="<c:url value="/controller"/>">
        <input type="hidden" name="command" value="register">
        <h1><fmt:message key="registerTitle"/></h1>
        <c:import url="/jsp/error/error_parts/error_part.jsp"/>
        <br/>
        <label style="color: red">*</label>
        <h5><fmt:message key="login_needs"/></h5>
        <div class="form-group">
            <input type="text" name="login" class="form-control" required pattern="^[a-zA-Z0-9_-]{6,15}$" placeholder="<fmt:message key="login_placeholder"/>"
                   value=<c:if test="${requestScope.correct_login != null}">${requestScope.correct_login}</c:if>>
        </div>
        <label style="color: red">*</label>
        <h5><fmt:message key="password_needs"/></h5>
        <div class="form-group">
            <input type="password" name="password" class="form-control" required pattern="^[a-zA-Z0-9_-]{6,15}$" placeholder="<fmt:message key="password_placeholder"/>"
                   value=<c:if test="${requestScope.correct_password != null}">${requestScope.correct_password}</c:if>>
        </div>
        <label style="color: red">*</label>
        <div class="form-group">
            <input type="password" name="repeat_password" class="form-control" required pattern="^[a-zA-Z0-9_-]{6,15}$" placeholder="<fmt:message key="password_repeat"/>"
                   value=<c:if test="${requestScope.correct_rep_password != null}">${requestScope.correct_rep_password}</c:if>>
        </div>
        <label style="color: red">*</label>
        <h5><fmt:message key="email_example"/></h5>
        <div class="form-group">
            <input type="email" name="email" class="form-control" required pattern="^([a-z0-9_\.-]+)@([a-z0-9_\.-]+)\.([a-z\.]{2,6})$" placeholder="<fmt:message key="email_placeholder"/>"
                   value=<c:if test="${requestScope.correct_email != null}">${requestScope.correct_email}</c:if>>>
        </div>
        <input type="checkbox" name="hr_option" class="form-control" <c:if test="${requestScope.hr_check != null}">${requestScope.hr_check}</c:if>><h5 style="text-align: center"><fmt:message key="like_hr"/></h5><br/>
        <input style="width: 40%;" type="submit" class="btn" value=<fmt:message key="register"/>>
        <br/>
    </form>
</div>
</body>
</html>