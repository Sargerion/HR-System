<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="current_page" value="/jsp/error/500.jsp" scope="request"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
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
            <h3>500</h3>
        </div>
        <h4><fmt:message key="error505_request_name_Part1"/> ${pageContext.errorData.requestURI} <fmt:message key="error505_request_name_Part2"/></h4>
        <br/>
        <h4><fmt:message key="error505_servlet_name"/> ${pageContext.errorData.servletName}</h4>
        <br/>
        <h4><fmt:message key="error505_status_code"/> ${pageContext.errorData.statusCode}</h4>
        <br/>
        <h4><fmt:message key="error505_exception"/> ${pageContext.exception}</h4>
        <br/>
        <h4><fmt:message key="error505_mess_from_exc"/> ${pageContext.exception.message}</h4>
        <a href=<c:choose>
                    <c:when test="${sessionScope.user.getType() eq 'ADMIN'}">
                        "${pageContext.request.contextPath}/jsp/admin/welcomeAdmin.jsp"
                    </c:when>
                    <c:when test="${sessionScope.user.getType() eq 'COMPANY_HR'}">
                        "${pageContext.request.contextPath}/jsp/hr/welcomeHR.jsp"
                    </c:when>
                    <c:when test="${sessionScope.user.getType() eq 'FINDER'}">
                        "${pageContext.request.contextPath}/jsp/welcomeFinder.jsp"
                    </c:when>
                    <c:otherwise>
                        "${pageContext.request.contextPath}/jsp/home.jsp"
                    </c:otherwise>
        </c:choose>><fmt:message key="move_from_error_page_button"/></a>
    </div>
</div>
</body>
</html>
