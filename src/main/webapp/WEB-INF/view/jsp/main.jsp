<%--
  ~ Copyright 2022 the original author or authors.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ https://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing,
  ~ software distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
  ~ either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<!doctype html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="frm" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  ~ Copyright 2022 the original author or authors.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ https://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing,
  ~ software distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
  ~ either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>
<c:choose>
    <c:when test="${not empty param}">
        <c:set var="title" value="${param.title}"/>
        <c:set var="body" value="${param.body}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="${page.title}"/>
        <c:set var="body" value="${page.body}"/>
    </c:otherwise>
</c:choose>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><frm:message key="project.name"/>: <frm:message key="${title}"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/main.css">
</head>
<body>
<div class="main">
    <div class="menu">
        <jsp:include page="menu.jsp"/>
    </div>
    <div class="context">
        <jsp:include page="${body}.jsp"/>
    </div>
    <div class="footer">
        <div class="footer-context">
            <div class="copyrights"><p><a href="<frm:message key="project.url"/>">©<frm:message key="project.author"/></a>&nbsp;2022</p>
            </div>
            <div class="version"><p><frm:message key="project.version"/></p></div>
        </div>
    </div>
</div>
</body>
</html>
