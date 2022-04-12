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

import { Pagination } from './pagination';
import { Round } from './round';
import { Msg } from './msg';
import PropTypes from 'prop-types';
import React from 'react';

export class RoundList extends React.Component {
    constructor(props) {
        super(props);
        RoundList.propTypes = {
            rounds: PropTypes.shape(
                { totalElements: PropTypes.number },
                {
                    content: PropTypes.arrayOf({
                        round: PropTypes.shape(
                            { id: PropTypes.string },
                            {
                                status: PropTypes.shape({
                                    name: PropTypes.string,
                                    messageKey: PropTypes.string
                                })
                            },
                            {
                                player1: PropTypes.shape({
                                    name: PropTypes.string,
                                    messageKey: PropTypes.string
                                })
                            },
                            {
                                player2: PropTypes.shape({
                                    name: PropTypes.string,
                                    messageKey: PropTypes.string
                                })
                            }
                        )
                    })
                }
            )
        };
        this.setState(props.rounds);
        this.pagination = React.createRef();
    }

    render() {
        let rounds;
        let roundLength = this.state.rounds?.totalElements;

        if (roundLength > 0) {
            rounds = this.state.rounds?.content?.map((round) => (
                <Round key={round.id} round={round} />
            ));
        } else {
            rounds = (
                <tr>
                    <td colSpan="4">
                        <Msg msgKey="label.table.empty" />
                    </td>
                </tr>
            );
        }

        this.pagination?.current?.update(this.state.rounds);

        return (
            <div className="scroll-table">
                <div className="pagination-panel">
                    <Pagination
                        ref={this.pagination}
                        options={this.state.rounds}
                        refreshTable={this.state.refreshTable}
                    />
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>
                                <Msg msgKey="label.id" />
                            </th>
                            <th>
                                <Msg msgKey="label.player1" />
                            </th>
                            <th>
                                <Msg msgKey="label.player2" />
                            </th>
                            <th>
                                <Msg msgKey="label.round.result" />
                            </th>
                        </tr>
                    </thead>
                </table>
                <div className="scroll-table-body">
                    <table>
                        <tbody>{rounds}</tbody>
                    </table>
                </div>
                <table>
                    <tfoot>
                        <tr>
                            <td colSpan="3">
                                <label htmlFor="round">
                                    <Msg msgKey="label.round" />
                                    :&nbsp;
                                </label>
                            </td>
                            <td>
                                <output id="round">{roundLength}</output>
                            </td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        );
    }
}
