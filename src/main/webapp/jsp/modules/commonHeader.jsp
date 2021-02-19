<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<head>
    <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
</head>
<body>
<header class="page-header">
    <nav>
        <div class="logo"><fmt:message key="logo"/></div>
        <ul>
            <li><a href="${pageContext.request.contextPath}/jsp/home.jsp"><fmt:message key="home"/></a></li>
            <li>
                <a href="#"><fmt:message key="languages_list_name"/></a>
                <ul>
                    <li><c:import url="/jsp/modules/locale.jsp"/></li>
                </ul>
            </li>
            <li>
                <a href="#" ><fmt:message key="service"/></a>
                <ul>
                    <li><a href="#" >Something</a></li>
                </ul>
            </li>
            <li><a href="${pageContext.request.contextPath}/jsp/login.jsp" class="delim"><fmt:message key="login"/></a></li>
            <li><a href="${pageContext.request.contextPath}/jsp/register.jsp"><fmt:message key="register"/></a></li>
        </ul>
    </nav>
</header>
</body>
</html>
