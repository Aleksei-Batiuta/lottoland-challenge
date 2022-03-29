<%@ taglib prefix="frm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <th><frm:message key="label.id"/></th>
                <th><frm:message key="label.player1"/></th>
                <th><frm:message key="label.player2"/></th>
                <th><frm:message key="label.round.result"/></th>
            </tr>
            </thead>
        </table>
        <div class="scroll-table-body">
            <table>
                <tbody>
                <c:choose>
                    <c:when test="${empty page.data}">
                        <tr>
                            <td colspan="4">
                                <frm:message key="label.table.empty"/>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${page.data}" varStatus="index">
                            <tr>
                                    <%--                                <td>${page.data.size() - index.index}</td>--%>
                                <td>${index.current.id}</td>
                                <td><frm:message key="${index.current.player1.messageKey}"/></td>
                                <td><frm:message key="${index.current.player2.messageKey}"/></td>
                                <td><frm:message key="${index.current.status.messageKey}"/></td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
        <table>
            <tfoot>
            <tr>
                <td colspan="3">
                    <label for="round"><frm:message key="label.round"/>:&nbsp;</label>
                </td>
                <td>
                    <output id="round">${page.data.size()}</output>
                </td>
            </tr>
            </tfoot>
        </table>
    </div>

    <div class="button-panel">
        <div class="button">
            <form action="new" method="post">
                <button type="submit"><frm:message key="label.play.round"/></button>
            </form>
        </div>
        <div class="button">
            <form action="reset" method="post">
                <button type="submit"><frm:message key="label.restart.game"/></button>
            </form>
        </div>
    </div>
</div>