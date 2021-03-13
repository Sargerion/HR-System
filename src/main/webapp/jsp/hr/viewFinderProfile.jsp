<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="/jsp/error/500.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="current_page" value="/jsp/hr/viewFinderProfile.jsp" scope="request"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<head>
    <link href="${pageContext.request.contextPath}/css/profile.css" rel="stylesheet">
    <title><fmt:message key="go_to_finder_profile_title"/></title>
</head>
<body>
<c:import url="part/hrHeader.jsp"/>
<c:choose>
    <c:when test="${sessionScope.view_by_hr_finder != null}">
        <div class="wrapper" style="height: 43%; width: 54%;">
            <div class="left">
                <h3><fmt:message key="login_placeholder"/></h3>
                <p>${sessionScope.finder_login}</p>
            </div>
            <div class="right">
                <div class="info">
                    <h3><fmt:message key="admin_profile_information_title"/></h3>
                    <div class="info_data">
                        <div class="data">
                            <h4><fmt:message key="finder_current_want_salary"/></h4>
                            <p style="font-size: 16px;">${sessionScope.view_by_hr_finder.getRequireSalary()}$</p>
                            <br/>
                            <br/>
                            <h4><fmt:message key="finder_current_work_experience"/></h4>
                            <p style="font-size: 16px;">${sessionScope.view_by_hr_finder.getWorkExperience()}</p>
                            <br/>
                            <br/>
                            <h4><fmt:message key="finder_current_specialty"/></h4>
                            <p style="font-size: 16px;">${sessionScope.view_by_hr_finder.getSpecialty().getSpecialtyName()}</p>
                            <br/>
                            <br/>
                            <h4><fmt:message key="finder_current_work_status"/></h4>
                            <p style="font-size: 16px;">${sessionScope.view_by_hr_finder.getWorkStatus()}</p>
                            <br/>
                            <br/>
                            <br/>
                            <br/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <h1 style="color: royalblue; text-align: center; padding-top: 100px;"><fmt:message key="finder_not_choose_title"/></h1>
    </c:otherwise>
</c:choose>
<br/>
<br/>
<c:import url="/jsp/modules/part/message_part.jsp"/>
<c:import url="/jsp/error/error_parts/error_part.jsp"/>
<c:import url="/jsp/modules/footer.jsp"/>
</body>
</html>