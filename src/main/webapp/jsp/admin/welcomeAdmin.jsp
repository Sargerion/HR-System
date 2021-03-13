<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="/jsp/error/500.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="current_page" value="/jsp/admin/welcomeAdmin.jsp" scope="request"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<head>
    <link href="${pageContext.request.contextPath}/css/profile.css" rel="stylesheet">
    <title><fmt:message key="home"/></title>
</head>
<body>
<c:import url="part/adminHeader.jsp"/>
<br/>
<br/>
<br/>
<br/>
<div class="wrapper" style="height: 36%; width: 54%;">
    <div class="left">
        <img src="${pageContext.request.contextPath}/user_avatars/" width="200">
        <p>${sessionScope.user.getLogin()}</p>
    </div>
    <div class="right">
        <div class="info">
            <h3><fmt:message key="admin_profile_information_title"/></h3>
            <div class="info_data">
                <div class="data">
                    <h4><fmt:message key="email_placeholder"/></h4>
                    <p>${sessionScope.user.getEmail()}</p>
                    <br/>
                    <br/>
                    <h4><fmt:message key="admin_profile_explain"/></h4>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="/jsp/modules/footer.jsp"/>
</body>
</html>