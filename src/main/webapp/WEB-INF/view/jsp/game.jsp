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
    <div>
        <label for="round"><frm:message key="label.round"/>:</label>
        <output id="round">${round}</output>
    </div>
    <div>
        <form action="new" method="post">
            <button type="submit"><frm:message key="label.play.round"/></button>
        </form>
    </div>
    <div>
        <form action="reset" method="post">
            <button type="submit"><frm:message key="label.restart.game"/></button>
        </form>
    </div>
    <div class="scroll-container">
        <div class="scroll-content">
            <table>
                <tr>
                    <th><frm:message key="label.id"/></th>
                    <th><frm:message key="label.player.1"/></th>
                    <th><frm:message key="label.player.2"/></th>
                    <th><frm:message key="label.round.result"/></th>
                </tr>
                <c:choose>
                    <c:when test="${empty rounds}">
                        <tr>
                            <td colspan="4">
                                <frm:message key="label.table.empty"/>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${rounds}" varStatus="index">
                            <tr>
                                <td>${round - index.index}</td>
                                <td><frm:message key="${index.current.player1.messageKey}"/></td>
                                <td><frm:message key="${index.current.player2.messageKey}"/></td>
                                <td><frm:message key="${index.current.status.messageKey}"/></td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </table>
        </div>
    </div>
    <div>
        <div>
            <label for="total_rounds"><frm:message key="label.rounds.total"/>:</label>
            <output id="total_rounds">${totalrounds}</output>
        </div>
        <div>
            <label for="first_wins"><frm:message key="label.rounds.first"/>:</label>
            <output id="first_wins">${firstrounds}</output>
        </div>
        <div>
            <label for="second_wins"><frm:message key="label.rounds.second"/>:</label>
            <output id="second_wins">${secondrounds}</output>
        </div>
        <div>
            <label for="total_draws"><frm:message key="label.rounds.draws"/>:</label>
            <output id="total_draws">${totaldraws}</output>
        </div>
    </div>
</div>