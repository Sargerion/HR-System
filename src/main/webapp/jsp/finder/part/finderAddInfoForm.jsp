<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<body>
    <c:choose>
        <c:when test="${sessionScope.finder != null}">
            <br/>
            <br/>
            <br/>
            <br/>
            <div class="wrapper" style="height: 60%; width: 50%;">
                <div class="left">
                    <img src="${pageContext.request.contextPath}/user_avatars/" width="200">
                    <p>${sessionScope.user.getLogin()}</p>
                </div>
                <div class="right">
                    <div class="info">
                        <h3><fmt:message key="admin_profile_information_title"/></h3>
                        <div class="info_data">
                            <div class="data">
                                <h4><fmt:message key="finder_current_want_salary"/></h4>
                                <p style="font-size: 16px;">${finder.getRequireSalary()}$</p>
                                <br/>
                                <br/>
                                <h4><fmt:message key="finder_current_work_experience"/></h4>
                                <p style="font-size: 16px;">${finder.getWorkExperience()}</p>
                                <br/>
                                <br/>
                                <h4><fmt:message key="finder_current_specialty"/></h4>
                                <p style="font-size: 16px;">${finder.getSpecialty().getSpecialtyName()}</p>
                                <br/>
                                <br/>
                                <h4><fmt:message key="finder_current_work_status"/></h4>
                                <p style="font-size: 16px;">${finder.getWorkStatus()}</p>
                                <br/>
                                <br/>
                                <br/>
                                <br/>
                                <form method="post" action="<c:url value="/controller"/>">
                                    <input type="hidden" name="command" value="forward_to_edit_info">
                                    <input class="form-control" style="display:block;
                                           position: relative;
                                           padding:0.3em 1.2em;
                                           border-radius:2em;
                                           box-sizing: border-box;
                                           text-decoration:none;
                                           font-family:'Roboto',sans-serif;
                                           font-weight:300;
                                           font-size: 15px;
                                           line-height: 20px;
                                           width: 100%;
                                           height: 12%;
                                           color:#FFFFFF;
                                           text-align:center;
                                           transition: all 0.2s;
                                           background: #4ef18f;
                                           margin:0 auto;"
                                           onmouseover="this.style.borderColor='rgba(25, 181, 254, 1)';"
                                           onmouseout="this.style.borderColor='#4ef18f';"
                                           type="submit" value="<fmt:message key="edit_finder_title"/>">
                                    <br/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="container" style="max-height: 70%">
                <form style="height: 51%" name="addFinderInfoForm" method="post" action="<c:url value="/controller"/>">
                    <h1><fmt:message key="add_finder_info_title"/></h1>
                    <input type="hidden" name="command" value="add_finder_info">
                    <br/>
                    <div class="form-group">
                        <label style="color: red">*</label>
                        <h5><fmt:message key="add_finder_info_salary_needs"/></h5>
                        <input type="number" size="5" name="finder_require_salary" class="form-control" required min="500" max="100000"
                               pattern="^\\d+(?:[\\.,]\\d+)?$"
                               placeholder="<fmt:message key="vacancy_money"/>"
                               value="500">
                    </div>
                    <div class="form-group">
                        <label style="color: red">*</label>
                        <h5><fmt:message key="add_finder_info_work_experience"/></h5>
                        <input type="number" size="5" name="finder_work_experience" class="form-control" required min="1" max="60"
                               placeholder="<fmt:message key="vacancy_work_experience_placeholder"/>"
                               value="1">
                    </div>
                    <div class="form-group">
                        <label style="color: red">*</label>
                        <h5><fmt:message key="vacancy_specialty_title"/></h5>
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
        </c:otherwise>
    </c:choose>
</body>
</html>