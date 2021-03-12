<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="/jsp/error/500.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctag" uri="custom_tag" %>

<c:set var="current_page" value="/jsp/common/viewCompany.jsp" scope="request"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<head>
    <title><fmt:message key="company_info_title"/></title>
    <link href="${pageContext.request.contextPath}/css/profile.css" rel="stylesheet">
</head>
<body>
<header class="page-header">
    <nav>
        <div class="logo"><fmt:message key="logo"/></div>
        <ctag:show_avatar/>
        <h2 class="logo" style="font-size: 19px; "><fmt:message key="login_placeholder"/>:${sessionScope.user.getLogin()}</h2>
        <h2 class="logo" style="font-size: 19px;"><fmt:message key="user_role"/>:${sessionScope.user.getType()}</h2>
        <ul>
            <li><a href=<c:choose>
                   <c:when test="${sessionScope.user.getType() eq 'ADMIN'}">
                           "${pageContext.request.contextPath}/jsp/admin/welcomeAdmin.jsp"
                </c:when>
                <c:when test="${sessionScope.user.getType() eq 'COMPANY_HR'}">
                    "${pageContext.request.contextPath}/jsp/hr/welcomeHR.jsp"
                </c:when>
                <c:otherwise>
                    "${pageContext.request.contextPath}/jsp/finder/welcomeFinder.jsp"
                </c:otherwise>
                </c:choose>>
                <fmt:message key="home"/>
                </a></li>
            <li>
                <a href="#"><fmt:message key="languages_list_name"/></a>
                <ul>
                    <li><c:import url="/jsp/modules/locale.jsp"/></li>
                </ul>
            </li>
            <li><c:import url="/jsp/modules/logoutForm.jsp"/></li>
        </ul>
    </nav>
</header>
    <c:choose>
    <c:when test="${sessionScope.company != null}">
    <div class="wrapper" style="height: 40%; width: 54%;">
        <div class="left">
            <h3><fmt:message key="company_form_name_placeholder"/></h3>
            <p>${sessionScope.company.getName()}</p>
        </div>
        <div class="right">
            <div class="info">
                <h3><fmt:message key="company_info_title"/></h3>
                <div class="info_data">
                    <div class="data">
                        <h4><fmt:message key="company_form_owner_placeholder"/></h4>
                        <p style="font-size: 16px;">${sessionScope.company.getOwner()}</p>
                        <br/>
                        <br/>
                        <h4><fmt:message key="company_form_town_placeholder"/></h4>
                        <p style="font-size: 16px;">${sessionScope.company.getAddress()}</p>
                        <br/>
                        <br/>
                        <h4><fmt:message key="company_form_hr_login_placeholder"/></h4>
                        <p style="font-size: 16px;">${sessionScope.company.getHrLogin()}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </c:when>
    <c:otherwise>
        <h1 style="color: royalblue; text-align: center; padding-top: 100px;"><fmt:message key="company_not_choose_title"/></h1>
    </c:otherwise>
    </c:choose>
    <br/>
    <br/>
    <c:import url="/jsp/modules/part/message_part.jsp"/>
    <c:import url="/jsp/error/error_parts/error_part.jsp"/>
    <c:import url="/jsp/modules/footer.jsp"/>
</body>
</html>