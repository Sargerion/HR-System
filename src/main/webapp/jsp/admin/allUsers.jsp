<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="/jsp/error/500.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctag" uri="custom_tag" %>

<c:set var="current_page" value="/jsp/admin/allUsers.jsp" scope="request"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<head>
    <title><fmt:message key="all_users"/></title>
    <link href="${pageContext.request.contextPath}/css/tables.css" rel="stylesheet">
</head>
<body>
<c:import url="part/adminHeader.jsp"/>
<h2 style="text-align: center; font-weight: bold"><fmt:message key="all_users"/></h2>
<ctag:all_users_list/>
<c:import url="/jsp/modules/part/message_part.jsp"/>
<c:import url="/jsp/error/error_parts/error_part.jsp"/>
<c:import url="/jsp/modules/footer.jsp"/>
</body>
</html>
