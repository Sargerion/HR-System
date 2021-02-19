<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="/jsp/error/500.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctag" uri="custom_tag" %>

<c:set var="current_page" value="/jsp/admin/hrRequestsView.jsp" scope="request"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<head>
    <title><fmt:message key="see_hrs"/></title>
    <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
</head>
<body>
<c:import url="part/adminHeader.jsp"/>



<ctag:not_active_hr_list/>
<c:import url="/jsp/modules/footer.jsp"/>
</body>
</html>
