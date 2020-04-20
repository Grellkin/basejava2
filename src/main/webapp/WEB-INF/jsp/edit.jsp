<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="main.ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="main.ru.javawebinar.basejava.model.SectionType" %>
<%--
  Created by IntelliJ IDEA.
  User: varga
  Date: 12.04.2020
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Editing resume</title>
    <link rel="stylesheet" href="css/tableStyle.css">
</head>
<body>
<jsp:include page="snippets/header.jsp"/>
<h1>Edit or create your resume</h1>
<jsp:useBean id="resume" scope="request" type="main.ru.javawebinar.basejava.model.Resume"/>
<form action="resume" method="post">
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <dl>
        <dt>Full Name</dt>
        <dd><input type="text" name="fullName" id="fullName" value="${resume.fullName}"></dd>
    </dl>
    <c:forEach items="${ContactType.values()}" var="cont">
        <dl>
            <dt>${cont.title}</dt>
            <dd><input type="text" name="${cont}" value="${resume.contacts.get(cont)}"></dd>
        </dl>
    </c:forEach>
    <h3>Персональная информация</h3>
    <c:forEach items="${SectionType.values()}" var="sect">
        <h4>${sect.title}</h4>
        <c:set var="resumeSect" value="${resume.sections.get(sect)}"/>
        <c:choose>
            <c:when test="${sect.equals(SectionType.PERSONAL) || sect.equals(SectionType.OBJECTIVE)}">
                <p><textarea rows="3" cols="40" name="${sect}"><c:if
                        test="${resumeSect != null}">${resumeSect}</c:if></textarea></p>
            </c:when>
            <c:when test="${sect.equals(SectionType.QUALIFICATIONS) || sect.equals(SectionType.ACHIEVEMENT)}">
                <p><textarea rows="3" cols="40" name="${sect}"> <c:if test="${resumeSect != null}"><c:set var="listSec"
                                                                                                          value="${resume.sections.get(sect)}"/><jsp:useBean id="listSec" type="main.ru.javawebinar.basejava.model.ListSection"/><c:forEach items="${listSec.content}" var="item">${item}</c:forEach></c:if></textarea></p>
            </c:when>

            <c:when test="${sect.equals(SectionType.EXPERIENCE) || sect.equals(SectionType.EDUCATION)}">
                <c:set var="orgSec" value="${resume.sections.get(sect)}"/>
                <jsp:useBean id="orgSec" class="main.ru.javawebinar.basejava.model.OrganizationSection" />
                <c:forEach var="org" items="${orgSec.content}" varStatus="counter">
                    <dl>
                        <dt>Название учереждения:</dt>
                        <dd><input type="text" name='${sect}' size=100 value="${org.organizationName.name}"></dd>
                    </dl>
                    <dl>
                        <dt>Сайт учереждения:</dt>
                        <dd><input type="text" name='${sect}url' size=100 value="${org.organizationName.url}"></dd>
                    </dl>
                    <br>
                    <div style="margin-left: 30px">
                        <c:forEach var="pos" items="${org.positions}">
                            <jsp:useBean id="pos" type="main.ru.javawebinar.basejava.model.Organization.Position"/>
                            <dl>
                                <dt>Начальная дата:</dt>
                                <dd>
                                    <input type="date" name="${sect}${counter.index}startDate" size=10
                                           value="${pos.dateOfStart}">
                                </dd>
                            </dl>
                            <dl>
                                <dt>Конечная дата:</dt>
                                <dd>
                                    <input type="date" name="${sect}${counter.index}endDate" size=10
                                           value="${pos.dateOfEnd}">
                            </dl>
                            <dl>
                                <dt>Должность:</dt>
                                <dd><input type="text" name='${sect}${counter.index}position' size=75
                                           value="${pos.position}">
                            </dl>
                            <dl>
                                <dt>Описание:</dt>
                                <dd><textarea name="${sect}${counter.index}info" rows=5
                                              cols=75>${pos.info}</textarea></dd>
                            </dl>
                        </c:forEach>
                    </div>
                </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach>
    <input type="submit" value="Submit">
    <input type="reset" value="Reset">
</form>
<button onclick="window.history.back()">Go back</button>
<jsp:include page="snippets/footer.jsp"/>
</body>
</html>
