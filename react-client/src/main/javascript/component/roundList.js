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

import {Pagination} from "./pagination";
import {Round} from "./round";
import {Msg} from "./msg";

const React = require('react');

export class RoundList extends React.Component {
    constructor(props) {
        super(props);
        this.props = props;
        this.pagination = React.createRef()
    }

    componentDidMount() {
        // this.pagination.current.update(this.props.rounds);
        if (true) {
            let i = 0;
            i++;
            i++;
        }
    }

    render() {
        let rounds;
        let roundLength = this.props.rounds?.totalElements;

        if (roundLength > 0) {
            rounds = this.props.rounds?.content?.map(round =>
                <Round key={round.id} round={round}/>
            );
        } else {
            rounds = <tr>
                <td colSpan="4">
                    <Msg msgKey="label.table.empty"/>
                </td>
            </tr>;
        }

        this.pagination?.current?.update(this.props.rounds);

        return (
            <div className="scroll-table">
                <div className="pagination-panel">
                    <Pagination
                        ref={this.pagination}
                        options={this.props.rounds}
                        refreshTable={this.props.refreshTable}
                    />
                </div>
                <table>
                    <thead>
                    <tr>
                        <th><Msg msgKey='label.id'/></th>
                        <th><Msg msgKey='label.player1'/></th>
                        <th><Msg msgKey='label.player2'/></th>
                        <th><Msg msgKey='label.round.result'/></th>
                    </tr>
                    </thead>
                </table>
                <div className="scroll-table-body">
                    <table>
                        <tbody>
                        {rounds}
                        </tbody>
                    </table>
                </div>
                <table>
                    <tfoot>
                    <tr>
                        <td colSpan="3">
                            <label htmlFor="round">
                                <Msg msgKey="label.round"/>
                                :&nbsp;</label>
                        </td>
                        <td>
                            <output id="round">{roundLength}</output>
                        </td>
                    </tr>
                    </tfoot>
                </table>
            </div>
        )
    }
}