<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<body>
<div class="container" style="max-height: 78%">
    <form style="height: 52%" name="addFinderInfoForm" method="post" action="<c:url value="/controller"/>">
        <input type="hidden" name="command" value="add_finder_info">
        <h1><fmt:message key="add_finder_info_title"/></h1>
        <br/>
        <label style="color: red">*</label>
        <h5><fmt:message key="add_finder_info_salary_needs"/></h5>
        <div class="form-group">
            <input type="number" size="5" name="finder_require_salary" class="form-control" required min="500" max="100000" value="500"
                   placeholder="<fmt:message key="vacancy_money"/>">
        </div>
        <label style="color: red">*</label>
        <h5><fmt:message key="add_finder_info_work_experience"/></h5>
        <div class="form-group">
            <input type="number" size="5" name="finder_work_experience" class="form-control" required min="1" max="60" value="1"
                   placeholder="<fmt:message key="vacancy_work_experience_placeholder"/>">
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
    width: 45%;
    height: 10%;
    color:#FFFFFF;
    text-align:center;
    transition: all 0.2s;
    background: #4ef18f;
    margin:0 auto;"
               onmouseover="this.style.borderColor='rgba(25, 181, 254, 1)';"
               onmouseout="this.style.borderColor='#4ef18f';"
               type="submit" value="<fmt:message key="form_save"/>">
        <br/>
    </form>
    <br/>
    <c:import url="/jsp/modules/part/message_part.jsp"/>
    <c:import url="/jsp/error/error_parts/error_part.jsp"/>
</div>
</body>
</html>