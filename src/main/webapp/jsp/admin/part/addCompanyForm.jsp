<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<body>
<div class="container" style="max-height: 76%">
<form style="height: 66%" name="addCompanyForm" method="post" action="<c:url value="/controller"/>">
    <input type="hidden" name="command" value="add_company">
    <h1><fmt:message key="company_form_title"/></h1>
    <br/>
    <label style="color: red">*</label>
    <h5><fmt:message key="company_form_name_needs"/></h5>
    <div class="form-group">
        <input type="text" name="company_name" class="form-control" required pattern="^[a-zA-Zа-яА-Я_-]{1,10}$" placeholder="<fmt:message key="company_form_name_placeholder"/>"
               value=<c:if test="${sessionScope.correct_company_name != null}">${sessionScope.correct_company_name}</c:if>>
    </div>
    <label style="color: red">*</label>
    <h5><fmt:message key="company_form_owner_needs"/></h5>
    <div class="form-group">
        <input type="text" name="company_owner" class="form-control" required pattern="^[a-zA-Zа-яА-Я_-]{1,25}$" placeholder="<fmt:message key="company_form_owner_placeholder"/>"
               value=<c:if test="${sessionScope.correct_company_owner != null}">${sessionScope.correct_company_owner}</c:if>>
    </div>
    <label style="color: red">*</label>
    <h5><fmt:message key="company_form_town_needs"/></h5>
    <div class="form-group">
        <input type="text" name="company_town" class="form-control" required pattern="^[a-zA-Zа-яА-Я]{1,20}$" placeholder="<fmt:message key="company_form_town_placeholder"/>"
               value=<c:if test="${sessionScope.correct_company_town != null}">${sessionScope.correct_company_town}</c:if>>
    </div>
    <label style="color: red">*</label>
    <h5><fmt:message key="login_needs"/></h5>
    <div class="form-group">
        <input type="text" name="company_hr_login" class="form-control" required pattern="^[a-zA-Zа-яА-Я0-9_-]{6,15}$" placeholder="<fmt:message key="company_form_hr_login_placeholder"/>"
               value=<c:if test="${sessionScope.correct_company_hr_login != null}">${sessionScope.correct_company_hr_login}</c:if>>
    </div>
    <br/>
    <input class="form-control" style="display:block;
    position: relative;
    padding:0.3em 1.2em;
    border-radius:2em;
    box-sizing: border-box;
    text-decoration:none;
    font-family:'Roboto',sans-serif;
    font-weight:300;
    font-size: 20px;
    line-height: 20px;
    width: 60%;
    height: 8%;
    color:#FFFFFF;
    text-align:center;
    transition: all 0.2s;
    background: #4ef18f;
    margin:0 auto;"
           onmouseover="this.style.borderColor='rgba(25, 181, 254, 1)';"
           onmouseout="this.style.borderColor='#4ef18f';"
           type="submit" value="<fmt:message key="company_form_button_value"/>">
</form>
    <br/>
    <c:import url="/jsp/error/error_parts/error_part.jsp"/>
    <c:import url="/jsp/modules/part/message_part.jsp"/>
    <c:remove var="correct_company_name" scope="session"/>
    <c:remove var="correct_company_owner" scope="session"/>
    <c:remove var="correct_company_town" scope="session"/>
    <c:remove var="correct_company_hr_login" scope="session"/>
</div>
</body>
</html>
