<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="current_page" value="/jsp/home.jsp" scope="request"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<head>
    <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
    <title><fmt:message key="home"/></title>
</head>
<body>
<header class="page-header">
    <nav>
    <div class="logo"><fmt:message key="logo"/></div>
    <ul>
        <li><a href="${pageContext.request.contextPath}/jsp/home.jsp"><fmt:message key="home"/></a></li>
        <li>
            <a href="#"><fmt:message key="languages_list_name"/></a href="#">
            <ul>
                <li><c:import url="modules/locale.jsp"/></li>
            </ul>
        </li>
        <li>
            <a href="#" ><fmt:message key="service"/></a>
            <ul>
                <li><a href="#" >Something</a></li>
                <li>
                    <a href="#">More<span class="picture"></span></a>
                    <ul>
                        <li><a href="#">Sub1</a></li>
                        <li><a href="#">Sub2</a></li>
                        <li><a href="#">Sub3</a></li>
                    </ul>
                </li>
            </ul>
        </li>
        <li><a href="${pageContext.request.contextPath}/jsp/login.jsp" class="delim"><fmt:message key="login"/></a></li>
        <li><a href="${pageContext.request.contextPath}/jsp/register.jsp"><fmt:message key="register"/></a></li>
    </ul>
    </nav>
</header>
<c:import url="/jsp/modules/part/message_part.jsp"/>
<c:import url="/jsp/error/error_parts/error_part.jsp"/>
<br/>
<div style="display:flex;align-items:center;justify-content: center;margin:140px;">
    <%@include file="/images/svg/hr_system.svg"%>
</div>
<c:import url="/jsp/modules/footer.jsp"/>
</body>
</html>