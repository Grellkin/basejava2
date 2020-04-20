<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="main.ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="java.time.LocalDate" %><%--
  Created by IntelliJ IDEA.
  User: varga
  Date: 12.04.2020
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>View of resume</title>
    <link rel="stylesheet" href="css/tableStyle.css">
</head>
<body>
<jsp:include page="snippets/header.jsp"/>
<jsp:useBean id="resume" scope="request" type="main.ru.javawebinar.basejava.model.Resume"/>
<h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="images/edit.png"
                                                                                  alt="Create new resume" width="20dp"
                                                                                  height="20dp"></a></h2>
<address>
    <c:forEach items="${resume.contacts}" var="cont">
        ${cont.key.title} : ${cont.value}<br>
    </c:forEach>
</address>
<hr>
<h3>Персональная информация</h3>
<c:forEach items="${resume.sections}" var="sect">
<c:set var="sectionType" value="${sect.key}"/>
<h4>${sectionType.title}</h4>
<p>
    <c:choose>
    <c:when test="${sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)}">
        ${sect.value}
    </c:when>
    <c:when test="${sectionType.equals(SectionType.QUALIFICATIONS) || sectionType.equals(SectionType.ACHIEVEMENT)}">
        <c:set var="listSec" value="${sect.value}"/>
        <jsp:useBean id="listSec" type="main.ru.javawebinar.basejava.model.ListSection"/>
    <c:forEach items="${listSec.content}" var="item">
<li>${item}</li>
</c:forEach>
</c:when>

<c:when test="${sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)}">
<c:set var="orgSec" value="${sect.value}"/>
<jsp:useBean id="orgSec" type="main.ru.javawebinar.basejava.model.OrganizationSection"/>
<c:forEach items="${orgSec.content}" var="org">
    <hr/>
<div style="margin-left: 50px">
<dl>
    <dt><i>Организация:</i></dt>
    <dd><b>${org.organizationName.name}</b> &nbsp; ಠ_ಠ &nbsp; ${org.organizationName.url}</dd>
</dl>
</div>

<div style="margin-left: 60px">
    <c:forEach var="pos" items="${org.positions}">
        <jsp:useBean id="pos" type="main.ru.javawebinar.basejava.model.Organization.Position"/>
    <dl>
        <dt>Период работы:</dt>
        <dd>
                ${pos.dateOfStart} - <c:choose>
            <c:when test="${pos.dateOfEnd.equals(LocalDate.of(3000, 1, 1))}">
                <i>still work</i>
            </c:when>
            <c:when test="${!pos.dateOfEnd.equals(LocalDate.of(3000, 1, 1))}">
                ${pos.dateOfEnd}
            </c:when></c:choose><br>
        </dd>
    </dl>
    <dl>
        <dt>Должность:</dt>
        <dd>${pos.position}</dd>
    </dl>
    <dl>
        <dt>Описание:</dt>
        <dd>${pos.info}</dd>
    </dl><br>
    </c:forEach>
</div>
    <hr/>

    </c:forEach>
    </c:when>
</c:choose>
</c:forEach>
    <button onclick="window.history.back()">Return</button>
    <jsp:include page="snippets/footer.jsp"/>
</body>
</html>
