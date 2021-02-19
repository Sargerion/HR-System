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
    <input type="submit" value="<fmt:message key="upload_form.button_value"/>">
    <br/>
    <c:import url="/jsp/modules/part/message_part.jsp"/>
    <br/>
    <c:import url="/jsp/error/error_parts/error_part.jsp"/>
</form>
</body>
</html>