<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctag" uri="custom_tag" %>

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
        <ctag:show_avatar/>
        <h2 class="logo" style="font-size: 19px; "><fmt:message key="login_placeholder"/>:${sessionScope.user.getLogin()}</h2>
        <h2 class="logo" style="font-size: 19px;"><fmt:message key="user_role"/>:${sessionScope.user.getType()}</h2>
        <ul>
            <li><a href="${pageContext.request.contextPath}/jsp/finder/welcomeFinder.jsp"><fmt:message key="home"/></a></li>
            <li>
                <a href="#"><fmt:message key="languages_list_name"/></a>
                <ul>
                    <li><c:import url="/jsp/modules/locale.jsp"/></li>
                </ul>
            </li>
            <li>
                <a href="#" class="delim"><fmt:message key="service"/></a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/jsp/common/changeAvatar.jsp"><h5 style="margin-left: 12px; line-height: 8px; font-weight: normal"><fmt:message key="upload_title"/></h5></a></li>
                    <li>
                        <a href="#">More</a>
                        <ul>
                            <li><a href="#">Sub1</a></li>
                            <li><a href="#">Sub2</a></li>
                            <li><a href="#">Sub3</a></li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li><c:import url="/jsp/modules/logoutForm.jsp"/></li>
        </ul>
    </nav>
</header>
</body>
</html>