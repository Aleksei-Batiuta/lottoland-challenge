<!doctype html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
<div class="main">
    <div class="header">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/static/images/lottoland.png" alt="Lottoland">
        </div>
    </div>
    <div class="menu">
        <ul>
            <li>
                <a href="/welcome">Welcome</a>
            </li>
            <li>
                <a href="/users">User List</a>
            </li>
        </ul>
    </div>
    <div class="title">
        <h1>${title}</h1>
    </div>
    <div class="context">
        <jsp:include page="${body}.jsp"/>
    </div>
    <div class="footer">
        <p><a href="https://www.batyuta.com">Alexei Batiuta©</a> 2022</p>
    </div>
</div>
</body>
</html>