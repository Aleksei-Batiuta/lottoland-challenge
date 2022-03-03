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

<ul>
    <li>
        <a href="${pageContext.request.contextPath}/game"><frm:message key="title.game"/></a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/users"><frm:message key="title.users"/></a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/exception"><frm:message key="title.error"/></a>
    </li>
</ul>