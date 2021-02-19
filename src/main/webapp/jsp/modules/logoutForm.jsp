<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<body>
<form method="post" action="<c:url value="/controller"/>">
    <input type="hidden" name="command" value="log_out">
    <input type="submit" value="<fmt:message key="logout"/>" class="delim" style="margin-left: 50%;transform:  translate(-50%);width: 120px;height: 34px;border: none;outline: none;background: aliceblue;cursor: pointer;font-size: 16px;color: royalblue;border-radius: 4px;transition: .3s; margin-top: 18px">
</form>
</body>
</html>