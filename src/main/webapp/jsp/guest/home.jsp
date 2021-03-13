<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="current_page" value="/jsp/guest/home.jsp" scope="request"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<head>
    <title><fmt:message key="home"/></title>
</head>
<body>
<c:import url="/jsp/modules/commonHeader.jsp"/>
<ul>
    <li style="display: inline-block"><img src="${pageContext.request.contextPath}/images/svg/apple_logo.svg"></li>
    <li style="display: inline-block"><img style="padding-left: 410px; margin: 50px" src="${pageContext.request.contextPath}/images/png/intel.png" height="200" width="512"></li>
    <li style="display: inline-block"><img style="padding-left: 410px; margin: 35px" src="${pageContext.request.contextPath}/images/svg/facebook.svg"></li>
</ul>
<c:import url="/jsp/modules/part/message_part.jsp"/>
<c:import url="/jsp/error/error_parts/error_part.jsp"/>
<br/>
<div style="display:flex;align-items:center;justify-content: center;margin:-130px;background-size: cover;">
    <img src="${pageContext.request.contextPath}/images/svg/hr_system.svg">
</div>
<c:import url="/jsp/modules/footer.jsp"/>
</body>
</html>