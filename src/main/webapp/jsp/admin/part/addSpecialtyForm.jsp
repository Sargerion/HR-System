<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<body>
<div class="container" style="max-height: 50%">
    <form style="height: 38%" name="addSpecialtyForm"  method="post" action="<c:url value="/controller"/>">
        <input type="hidden" name="command" value="add_specialty">
        <h1><fmt:message key="specialty_form_title"/></h1>
        <label style="color: red">*</label>
        <h5><fmt:message key="specialty_form_name_needs"/></h5>
        <div class="form-group">
            <input type="text" name="specialty_name" class="form-control" required pattern="^[a-zA-Zа-яА-Я_-]{1,15}$" placeholder="<fmt:message key="specialty_form_name_placeholder"/>">
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
    width: 62%;
    height: 18%;
    color:#FFFFFF;
    text-align:center;
    transition: all 0.2s;
    background: #4ef18f;
    margin:0 auto;"
               onmouseover="this.style.borderColor='rgba(25, 181, 254, 1)';"
               onmouseout="this.style.borderColor='#4ef18f';"
               type="submit" value="<fmt:message key="specialty_form_button_value"/>">
    </form>
    <br/>
    <c:import url="/jsp/error/error_parts/error_part.jsp"/>
    <c:import url="/jsp/modules/part/message_part.jsp"/>
</div>
</body>
</html>