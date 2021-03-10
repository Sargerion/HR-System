<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<body>
<div class="container" style="max-height: 80%">
    <form style="height: 65%" name="addVacancyForm" method="post" action="<c:url value="/controller"/>">
        <input type="hidden" name="command" value="add_vacancy">
        <h1><fmt:message key="vacancy_form_title"/></h1>
        <br/>
        <label style="color: red">*</label>
        <h5><fmt:message key="vacancy_name_needs"/></h5>
        <div class="form-group">
            <input type="text" name="vacancy_name" class="form-control" required pattern="^[a-zA-Zа-яА-Я_-]{1,40}$" placeholder="<fmt:message key="vacancy_name_placeholder"/>">
        </div>
        <label style="color: red">*</label>
        <h5><fmt:message key="vacancy_specialty_title"/></h5>
        <div class="form-group">
        <select name="specialty" size="1" required>
            <c:forEach items="${sessionScope.specialties}" var="specialty">
                <option value="${specialty.getEntityId()}">${specialty.specialtyName}</option>
            </c:forEach>
        </select>
        </div>
        <label style="color: red">*</label>
        <h5><fmt:message key="vacancy_money_needs"/></h5>
        <div class="form-group">
            <input type="number" size="5" name="vacancy_money" class="form-control" required min="500" max="100000" value="500"
            pattern="^\\d+(?:[\\.,]\\d+)?$"
            placeholder="<fmt:message key="vacancy_money"/>">
        </div>
        <label style="color: red">*</label>
        <h5><fmt:message key="vacancy_work_experience"/></h5>
        <div class="form-group">
            <input type="number" size="5" name="vacancy_experience" class="form-control" required min="1" max="60" value="1"
            placeholder="<fmt:message key="vacancy_work_experience_placeholder"/>">
        </div>
        <br/>
        <br/>
        <input type="submit" style="
    display:block;
    position: relative;
    padding:0.3em 1.2em;
    border-radius:2em;
    box-sizing: border-box;
    text-decoration:none;
    font-family:'Roboto',sans-serif;
    font-weight:300;
    font-size: 20px;
    line-height: 20px;
    width: 50%;
    height: 9%;
    color:#FFFFFF;
    text-align:center;
    transition: all 0.2s;
    background: #4ef18f;
    margin:0 auto;"
               onmouseover="this.style.borderColor='rgba(25, 181, 254, 1)';"
               onmouseout="this.style.borderColor='#4ef18f';"
               value="<fmt:message key="form_create_vacancy"/>">
        <br/>
    </form>
    <c:import url="/jsp/hr/part/findSpecialtiesForm.jsp"/>
    <br/>
    <c:import url="/jsp/modules/part/message_part.jsp"/>
    <c:import url="/jsp/error/error_parts/error_part.jsp"/>
</div>
</body>
</html>