<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" errorPage="error/500.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="current_page" value="/jsp/login.jsp" scope="request"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<head>
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
    <title><fmt:message key="login_page"/></title>
</head>
<body>
<c:import url="modules/commonHeader.jsp"/>
<c:import url="modules/loginForm.jsp"/>
<div style="display:flex;align-items:center;justify-content: center;margin:55px;position: sticky;">
    <%@include file="/images/svg/hr_system.svg"%>
</div>
<c:import url="modules/footer.jsp"/>
</body>
</html>