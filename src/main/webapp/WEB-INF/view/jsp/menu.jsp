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
<div class="menu-context">
    <div class="menu-item">
        <ul>
            <li class='${page.body == "game"? "active": ""}'>
                <a href="${pageContext.request.contextPath}/"><frm:message key="title.game"/></a>
            </li>
            <li class='${page.body == "statistics"? "active": ""}'>
                <a href="${pageContext.request.contextPath}/statistics"><frm:message key="title.statistics"/></a>
            </li>
            <li class='${(empty page or page.body == "exception")? "active": "hidden"}'>
                <a href="${pageContext.request.contextPath}/error"><frm:message key="title.error"/></a>
            </li>
        </ul>
    </div>
    <div class="logo"></div>
</div>
