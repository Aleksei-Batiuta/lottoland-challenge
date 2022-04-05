/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

const React = require("react");

const {Chart} = require("react-google-charts");
import {Msg} from "./msg";

export class Statistics extends React.Component {
    constructor(props) {
        super(props);
        this.props = props;
        console.log(this.props);
        this.state = {
            chartData: [
                ["Name", "Data"],
                ["First", 1],
                ["Second", 1],
                ["Draws", 1]
            ],
            data: {
                total: 3,
                first: 1,
                second: 1,
                draws: 1
            }
        };
        this.refresh = this.refresh.bind(this);
    }

    componentDidMount() {
        this.refresh();
    }

    refresh() {
        // <2>
        fetch("/api/rounds/statistics", {
            method: 'GET',
            headers: new Headers(),
        })
            .then(
                (res) => res.json()
            )
            .then(
                (data) => {
                    const chartData = [
                        ["Name", "Data"],
                        ["First", data.first],
                        ["Second", data.second],
                        ["Draws", data.draws]
                    ];
                    this.setState({chartData: chartData, data: data});
                    console.log(data);
                }
            )
            .catch((err) => console.log(err));
    }

    render() {
        const options = {
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
        let tableContext;
        if (this.state.data.total === 0) {
            tableContext =
                <tbody>
                <tr>
                    <td colSpan="4">
                        <Msg msgKey="label.table.empty"/>
                    </td>
                </tr>
                </tbody>
            ;
        } else {
            tableContext =
                <tbody>
                <tr>
                    <td rowSpan="3">
                        <div id="chart">
                        <Chart
                            chartVersion="current"
                            chartType="PieChart"
                            data={this.state.chartData}
                            options={options}
                        />
                        </div>
                    </td>
                    <td>
                        <div className="legend-first">&nbsp;</div>
                    </td>
                    <td><label htmlFor="first_wins">
                        <Msg msgKey="label.rounds.first"/>
                    </label></td>
                    <td>
                        <output id="first_wins">{this.state.data.first}</output>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div className="legend-second">&nbsp;</div>
                    </td>
                    <td><label htmlFor="second_wins">
                        <Msg msgKey="label.rounds.second"/>
                    </label></td>
                    <td>
                        <output id="second_wins">{this.state.data.second}</output>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div className="legend-draws">&nbsp;</div>
                    </td>
                    <td><label htmlFor="total_draws">
                        <Msg msgKey="label.rounds.draws"/>
                    </label></td>
                    <td>
                        <output id="total_draws">{this.state.data.draws}</output>
                    </td>
                </tr>
                </tbody>
            ;
        }
        return (
            <div>
                <div className="scroll-table">
                    <table>
                        <thead>
                        <tr>
                            <th>
                                <Msg msgKey="label.chart"/>
                            </th>
                            <th colSpan="3">
                                <Msg msgKey="label.data"/>
                            </th>
                        </tr>
                        </thead>
                    </table>
                    <div className="no-scroll-table-body">
                        <table>
                            {tableContext}
                        </table>
                    </div>
                    <table>
                        <tfoot>
                        <tr>
                            <td/>
                            <td colSpan="2">
                                <label htmlFor="total_rounds">
                                    <Msg msgKey="label.rounds.total"/>
                                </label>
                            </td>
                            <td>
                                <output id="total_rounds">{this.state.data.total}</output>
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        )
    }
}