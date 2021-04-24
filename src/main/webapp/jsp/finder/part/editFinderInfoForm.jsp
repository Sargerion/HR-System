<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<body>
    <c:choose>
        <c:when test="${sessionScope.finder != null}">
    <div class="container" style="max-height: 62%">
    <form style="height: 59%" name="editFinderInfoForm" method="post" action="<c:url value="/controller"/>">
        <input type="hidden" name="command" value="edit_finder_info">
        <h1><fmt:message key="edit_finder_title"/></h1>
        <br/>
            <div class="form-group">
                        <h4 style="color: royalblue"><fmt:message key="finder_current_want_salary"/>: ${finder.getRequireSalary()}$</h4>
                <br/>
                <h5><fmt:message key="add_finder_info_salary_needs"/></h5>
                <input type="number" size="5" name="finder_require_salary" class="form-control" required min="500" max="100000"
                       pattern="^\\d+(?:[\\.,]\\d+)?$"
                       placeholder="${finder.getRequireSalary()}">
            </div>
            <div class="form-group">
                        <h4 style="color: royalblue"><fmt:message key="finder_current_work_experience"/>: ${finder.getWorkExperience()}</h4>
                <br/>
                <h5><fmt:message key="add_finder_info_work_experience"/></h5>
                <input type="number" size="5" name="finder_work_experience" class="form-control" required min="1" max="60"
                       placeholder="${finder.getWorkExperience()}">
            </div>
            <div class="form-group">
                        <h4 style="color: royalblue"><fmt:message key="finder_current_specialty"/>: ${finder.getSpecialty().getSpecialtyName()}</h4>
                <br/>
                <h5><fmt:message key="vacancy_specialty_title"/></h5>
                <select name="specialty" size="1" required>
                    <c:forEach items="${sessionScope.specialties}" var="specialty">
                        <option value="${specialty.getEntityId()}">${specialty.specialtyName}</option>
                    </c:forEach>
                </select>
            </div>
            <br/>
            <h4 style="color: royalblue"><fmt:message key="finder_current_work_status"/>: ${finder.getWorkStatus()}</h4>
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
                type="submit" value="<fmt:message key="finder_edit_form_button_value"/>">
        <br/>
    </form>
        <br/>
<%--        <c:import url="/jsp/modules/part/message_part.jsp"/>--%>
        <c:import url="/jsp/error/error_parts/error_part.jsp"/>
    </div>
    </c:when>
    <c:otherwise>
        <h1 style="text-align: center"><fmt:message key="finder_ask_fill_profile"/></h1>
        <br/>
        <br/>
        <div style="display:flex;align-items:center;justify-content: center;margin:140px;background-size: cover;">
            <%@include file="/images/svg/hr_system.svg"%>
        </div>
    </c:otherwise>
    </c:choose>
</body>
</html>