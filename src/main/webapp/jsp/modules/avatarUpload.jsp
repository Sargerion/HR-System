<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<head>
    <link href="${pageContext.request.contextPath}/css/upload.css" rel="stylesheet">
</head>
<body>
<form enctype="multipart/form-data" method="post" action="<c:url value="/controller"/>">
    <input type="hidden" name="command" value="upload_avatar">
    <label class="custom-file-upload">
        <input type="file" class="custom-file-input" name="user_avatar_name" accept="image/*">
        <fmt:message key="upload_title"/>
    </label>
    <input type="submit" value="<fmt:message key="upload_form.button_value"/>"
    style="position: relative;
    width: 210px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border: 1px dashed #BBB;
    text-align: center;
    background-color: #DDD;
    cursor: pointer;">
    <br/>
    <c:import url="/jsp/modules/part/message_part.jsp"/>
    <br/>
    <c:import url="/jsp/error/error_parts/error_part.jsp"/>
    <br/>
    <br/>
    <br/>
    <br/>
    <h1 style="text-align: center;"><fmt:message key="current_avatar"/></h1>
    <img style="display: block;margin: 0 auto;" src="${pageContext.request.contextPath}/user_avatars/${requestScope.view_image}" width="400" height="400">
</form>
</body>
</html>