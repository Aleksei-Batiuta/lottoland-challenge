<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<div xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:jsp="http://java.sun.com/JSP/Page">
    <c:choose>
        <c:when test="${empty users}">
            <frm:message key="label.table.empty"/>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th><frm:message key="label.email"/></th>
                </tr>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.email}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</div>