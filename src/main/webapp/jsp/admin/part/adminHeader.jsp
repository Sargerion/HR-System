<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctag" uri="custom_tag" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<head>
    <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
</head>
<body>
<header class="page-header">
    <nav>
        <div class="logo"><fmt:message key="logo"/></div>
        <ctag:show_avatar/>
        <h2 class="logo" style="font-size: 19px; "><fmt:message key="login_placeholder"/>:${sessionScope.user.getLogin()}</h2>
        <h2 class="logo" style="font-size: 19px;"><fmt:message key="user_role"/>:${sessionScope.user.getType()}</h2>
        <ul>
            <li><a href="${pageContext.request.contextPath}/jsp/admin/welcomeAdmin.jsp"><fmt:message key="home"/></a></li>
            <li>
                <a href="#"><fmt:message key="languages_list_name"/></a>
                <ul>
                    <li><c:import url="/jsp/modules/locale.jsp"/></li>
                </ul>
            </li>
            <li>
                <a href="#" class="delim"><fmt:message key="service"/></a>
                <ul>
                    <li><c:import url="/jsp/admin/part/userListForm.jsp"/></li>
                    <li><c:import url="/jsp/modules/forwardAvatarForm.jsp"/></li>
                    <li><c:import url="/jsp/modules/vacancyListForm.jsp"/></li>
                    <li>
                        <form style="position:relative;" name="forward_add_company_form" method="post" action="<c:url value="/controller"/>">
                            <input type="hidden" name="command" value="forward_to_add_company">
                            <input type="submit" style="margin-left: 50%;transform:translate(-50%);width: 120px;height: 76px;border: none;outline: none;background: royalblue;
    cursor: pointer; color: snow;border-radius: 4px;font-size:16px;transition: .3s;white-space: normal;" value="<fmt:message key="company_form_button_value"/>">
                        </form>
                    </li>
                    <li>
                        <form style="position:relative;" name="forward_add_specialty_form" method="post" action="<c:url value="/controller"/>">
                            <input type="hidden" name="command" value="forward_to_add_specialty">
                            <input type="submit" style="margin-left: 50%;transform:translate(-50%);width: 120px;height: 76px;border: none;outline: none;background: royalblue;
    cursor: pointer; color: snow;border-radius: 4px;font-size:16px;transition: .3s;white-space: normal;" value="<fmt:message key="specialty_form_title"/>">
                        </form>
                    </li>
                </ul>
            </li>
            <li><c:import url="/jsp/modules/logoutForm.jsp"/></li>
        </ul>
    </nav>
</header>
</body>
</html>