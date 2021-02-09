<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="current_page" value="/jsp/welcomeAdmin.jsp" scope="request"/>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}" scope="session"/>
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
        <h2 class="logo" style="font-size: 19px; "><fmt:message key="login_placeholder"/>:${sessionScope.user.getLogin()}</h2>
        <h2 class="logo" style="font-size: 19px;"><fmt:message key="user_role"/>:${sessionScope.user.getType()}</h2>
        <ul>
            <li><a href="#"><fmt:message key="home"/></a></li>
            <li>
                <a href="#"><fmt:message key="languages_list_name"/></a href="#">
                <ul>
                    <li><c:import url="modules/locale.jsp"/></li>
                </ul>
            </li>
            <li>
                <a href="#" >Services</a>
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
            <li><a href="${pageContext.request.contextPath}/jsp/home.jsp" class="delim"><fmt:message key="logout"/></a></li>
        </ul>
    </nav>
</header>
<c:import url="modules/footer.jsp"/>
</body>
</html>