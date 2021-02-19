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
            <input type="text" name="login" class="form-control" required pattern="^[a-zA-Zа-яА-Я0-9_-]{6,15}$" placeholder="<fmt:message key="login_placeholder"/>"
            value=<c:if test="${requestScope.correct_login != null}">${requestScope.correct_login}</c:if>>
        </div>
        <h5><fmt:message key="password_needs"/></h5>
        <div class="form-group">
            <input type="password" name="password" class="form-control" required pattern="^[a-zA-Zа-яА-Я0-9_-]{6,15}$" placeholder="<fmt:message key="password_placeholder"/>">
        </div>
        <input type="submit" style="
    display:block;
    position: relative;
    padding:0.3em 1.2em;
    border:0.16em solid rgba(255,255,255,0);
    border-radius:2em;
    box-sizing: border-box;
    text-decoration:none;
    font-family:'Roboto',sans-serif;
    font-weight:300;
    font-size: 20px;
    line-height: 20px;
    width: 30%;
    height: 12%;
    color:#FFFFFF;
    text-shadow: 0 0.04em 0.04em rgba(0,0,0,0.35);
    text-align:center;
    transition: all 0.2s;
    background: #4ef18f;
    margin:0 auto;"
               onmouseover="this.style.borderColor='rgba(25, 181, 254, 1)';"
               onmouseout="this.style.borderColor='#4ef18f';"
               value="<fmt:message key="login"/>">
        <c:import url="/jsp/modules/part/message_part.jsp"/>
        <c:import url="/jsp/error/error_parts/error_part.jsp"/>
    </form>
</div>
</body>
</html>