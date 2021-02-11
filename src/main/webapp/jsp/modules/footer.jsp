<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
</head>
<body>
<footer class="page-footer">
    <div class=" page-footer_center">
        <p class="copyright"><fmt:message key="copyright"/></p>
    </div>
    <div style="margin: 35px 35px 35px 0.9cm;">
        <%@include file="/images/svg/icon-email.svg"%>
    </div>
    <a class="page-footer-email" href="mailto:sergey.galyan16@gmail.com"><fmt:message key="contact_email"/></a>
</footer>
</body>
</html>