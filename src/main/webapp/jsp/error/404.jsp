<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="current_page" value="/jsp/error/404.jsp" scope="request"/>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<head>
    <link href="${pageContext.request.contextPath}/css/error.css" rel="stylesheet">
    <title><fmt:message key="error_page_title"/></title>
</head>
<body>
<div id="notfound">
    <div class="notfound">
        <div class="notfound-404">
            <h1>404</h1>
        </div>
        <h2><fmt:message key="error_page_message_oops"/></h2>
        <p><fmt:message key="error_page_message_sorry"/></p>
        <a href="${pageContext.request.contextPath}/jsp/home.jsp"><fmt:message key="move_from_error_page_button"/></a>
    </div>
</div>
</body>
</html>
