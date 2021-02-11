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
            <li><a href="${pageContext.request.contextPath}/jsp/home.jsp"><fmt:message key="home"/></a></li>
            <li>
                <a href="#"><fmt:message key="languages_list_name"/></a href="#">
                <ul>
                    <li><c:import url="/jsp/modules/locale.jsp"/></li>
                </ul>
            </li>
            <li>
                <a href="#" class="delim"><fmt:message key="service"/></a>
                <ul>
                    <li><a href="#" ><fmt:message key="see_hrs"/></a></li>
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
            <li>
                <form method="get" action="<c:url value="/controller"/>">
                    <input type="hidden" name="command" value="log_out">
                    <input type="submit" value="<fmt:message key="logout"/>" class="delim" style="margin-left: 50%;transform:  translate(-50%);width: 120px;height: 34px;border: none;outline: none;background: aliceblue;cursor: pointer;font-size: 16px;color: royalblue;border-radius: 4px;transition: .3s; margin-top: 18px">
                </form>
            </li>
        </ul>
    </nav>
</header>
<c:import url="/jsp/modules/footer.jsp"/>
</body>
</html>