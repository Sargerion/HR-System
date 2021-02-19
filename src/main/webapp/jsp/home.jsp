<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="current_page" value="/jsp/home.jsp" scope="request"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<head>
    <title><fmt:message key="home"/></title>
</head>
<body>
<c:import url="modules/commonHeader.jsp"/>
<c:import url="/jsp/modules/part/message_part.jsp"/>
<c:import url="/jsp/error/error_parts/error_part.jsp"/>
<br/>
<div style="display:flex;align-items:center;justify-content: center;margin:140px;background-size: cover;">
    <%@include file="/images/svg/hr_system.svg"%>
</div>
<c:import url="/jsp/modules/footer.jsp"/>
</body>
</html>