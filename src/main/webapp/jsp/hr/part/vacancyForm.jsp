<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<body>
<div class="container" style="max-height: 100%">
    <form style="height: 80%" name="addVacancyForm" method="post" action="<c:url value="/controller"/>">
        <input type="hidden" name="command" value="add_vacancy">
        <h1><fmt:message key="vacancy_form_title"/></h1>
        <c:import url="/jsp/error/error_parts/error_part.jsp"/>
        <c:import url="/jsp/modules/part/message_part.jsp"/>
        <br/>
        <label style="color: red">*</label>
        <h5><fmt:message key="vacancy_specialty_title"/></h5>
        <div class="form-group">
        <select name="specialty" size="1" required>
            <c:forEach items="${specialties}" var="specialty" varStatus="counter">
                <option value="${specialty.specialtyId}">${specialty.specialtyName}</option>
            </c:forEach>
        </select>
        </div>
        <label style="color: red">*</label>
        <h5><fmt:message key="vacancy_money_needs"/></h5>
        <div class="form-group">
            <input type="number" size="5" name="vacancy_money" class="form-control" required min="500" max="100000" value="500"
            placeholder="<fmt:message key="vacancy_money"/>">
        </div>
        <label style="color: red">*</label>
        <h5><fmt:message key="vacancy_work_experience"/></h5>
        <div class="form-group">
            <input type="number" size="5" name="vacancy_experience" class="form-control" required min="1" max="60" value="1"
            placeholder="<fmt:message key="vacancy_work_experience_placeholder"/>">
        </div>

    </form>
</div>
</body>
</html>