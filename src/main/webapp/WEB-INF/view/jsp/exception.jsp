<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
<div xmlns:jsp="http://java.sun.com/JSP/Page">
    <div class="scroll-table">
        <table>
            <thead>
            <tr>
                <th colspan="2"></th>
            </tr>
            </thead>
        </table>
        <div class="scroll-table-body">
            <table>
                <tbody>
                <c:choose>
                    <c:when test="${empty page.data}">
                        <tr>
                            <td>
                                <div class="alert"></div>
                            </td>
                            <td>
                                <fmt:message key="error.unknown">
                                    <fmt:param value=""/>
                                </fmt:message>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>
                                <div class="alert"></div>
                            </td>
                            <td>${page.data}</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
        <table>
            <tfoot>
            <tr>
                <td colspan="2"></td>
            </tr>
            </tfoot>
        </table>
    </div>
</div>