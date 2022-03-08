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
                <th><frm:message key="label.chart"/></th>
                <th colspan="3"><frm:message key="label.data"/></th>
            </tr>
            </thead>
        </table>
        <div class="no-scroll-table-body">
            <table>
                <tbody>
                <c:choose>
                    <c:when test="${page.data.totalRounds == 0}">
                        <tr>
                            <td colspan="4">
                                <frm:message key="label.table.empty"/>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td rowspan="3">
                                <div id="chart" class="chart">
                                    <script type="text/javascript"
                                            src="${pageContext.request.contextPath}/js/gcharts.js"></script>

                                    <script>
                                        google.charts.load('current', {'packages': ['corechart']});
                                        google.charts.setOnLoadCallback(drawChart);

                                        function drawChart() {
                                            var data = google.visualization.arrayToDataTable([
                                                ['<frm:message key="label.rounds"/>', '<frm:message key="label.total"/>'],
                                                ['<frm:message key="label.rounds.first"/>', ${page.data.firstRounds}],
                                                ['<frm:message key="label.rounds.second"/>', ${page.data.secondRounds}],
                                                ['<frm:message key="label.rounds.draws"/>', ${page.data.totalDraws}]
                                            ]);

                                            var options = {
                                                backgroundColor: 'transparent',
                                                legend: {
                                                    position: 'none'
                                                },
                                                slices: {
                                                    0: {color: '#69a507'},
                                                    1: {color: '#a50769'},
                                                    2: {color: '#0769a5'}
                                                }
                                            };

                                            var chart = new google.visualization.PieChart(document.getElementById('chart'));
                                            chart.draw(data, options);
                                        }
                                    </script>
                                </div>
                            </td>
                            <td>
                                <div class="legend-first">&nbsp;</div>
                            </td>
                            <td><label for="first_wins"><frm:message key="label.rounds.first"/></label></td>
                            <td>
                                <output id="first_wins">${page.data.firstRounds}</output>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="legend-second">&nbsp;</div>
                            </td>
                            <td><label for="second_wins"><frm:message key="label.rounds.second"/></label></td>
                            <td>
                                <output id="second_wins">${page.data.secondRounds}</output>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="legend-draws">&nbsp;</div>
                            </td>
                            <td><label for="total_draws"><frm:message key="label.rounds.draws"/></label></td>
                            <td>
                                <output id="total_draws">${page.data.totalDraws}</output>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
        <table>
            <tfoot>
            <tr>
                <td></td>
                <td colspan="2">
                    <label for="total_rounds"><frm:message key="label.rounds.total"/></label>
                </td>
                <td>
                    <output id="total_rounds">${page.data.totalRounds}</output>
                </td>
            </tr>
            </tfoot>
        </table>
    </div>
</div>