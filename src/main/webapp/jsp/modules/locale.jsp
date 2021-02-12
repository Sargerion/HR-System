<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<head>
    <link href="${pageContext.request.contextPath}/css/locale.css" rel="stylesheet">
</head>
<body>
<form action="<c:url value="/controller"/>" method="post">
    <input type="hidden" name="command" value="change_language">
    <input type="hidden" name="current_page" value="${requestScope.current_page}">
    <select class="select" name="locale" onchange="submit()">
        <option value="ru_RU" <c:if test="${locale eq 'ru_RU'}"> selected</c:if> >><fmt:message key="lang_ru"/></option>
        <option value="en_US" <c:if test="${locale eq 'en_US'}"> selected</c:if> >><fmt:message key="lang_en"/></option>
        <option value="be_BY" <c:if test="${locale eq 'be_BY'}"> selected</c:if> >><fmt:message key="lang_by"/></option>
    </select>
</form>
</body>
</html>
