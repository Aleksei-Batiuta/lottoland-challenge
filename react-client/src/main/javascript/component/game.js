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

import RestService from '../service/restService';
import React from 'react';
import { RoundList } from './roundList';
import { Msg } from './msg';

export class Game extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            rounds: [],
            pagination: {
                page: 0,
                size: 10,
                sort: 'desc,id'
            }
        };
        this.restService = RestService.getInstance();

        this.refresh = this.refresh.bind(this);
        this.generate = this.generate.bind(this);
        this.cleanRounds = this.cleanRounds.bind(this);
        this.setError = this.setError.bind(this);
    }

    setError(error) {
        this.setState({ errorMessage: error });
    }

    componentDidMount() {
        this.refresh();
    }

    render() {
        if (this.state?.errorMessage !== undefined) {
            setTimeout(() => {
                this.setError(undefined);
                //this.refresh(0, 10, 'desc,id');
            }, 2000);
        }

        let errorMessage = this.state.errorMessage?.message;
        return (
            <div>
                {errorMessage && <div className="error"> {errorMessage} </div>}
                <RoundList rounds={this.state.rounds} refreshTable={this.refresh} />
                <div className="button-panel">
                    <div className="button">
                        <button onClick={this.generate}>
                            <Msg msgKey="label.play.round" />
                        </button>
                    </div>
                    <div className="button">
                        <button onClick={this.cleanRounds}>
                            <Msg msgKey="label.restart.game" />
                        </button>
                    </div>
                </div>
            </div>
        );
    }

    refresh(page, size, sort) {
        page =
            page !== undefined
                ? page
                : this.state.pagination?.page
                ? this.state.pagination.page
                : 0;
        size =
            size !== undefined
                ? size
                : this.state.pagination?.size
                ? this.state.pagination.size
                : 10;
        sort =
            sort !== undefined
                ? sort
                : this.state.pagination?.sort
                ? this.state.pagination.sort
                : 'desc,id';

        this.setState({
            rounds: this.state.rounds,
            pagination: {
                page: page,
                size: size,
                sort: sort
            }
        });
        this.restService.findAllRounds(
            page,
            size,
            sort,
            (data) => {
                this.setState({
                    rounds: data,
                    pagination: this.state.pagination
                });
            },
            (error) => {
                this.setState({
                    rounds: this.state.rounds,
                    pagination: {
                        page: 0,
                        size: 10,
                        sort: 'desc,id'
                    }
                });
                this.setError(error);
            }
        );
    }

    generate() {
        this.restService.newRound(
            () => {
                this.refresh();
            },
            (error) => {
                this.setError(error);
            }
        );
    }

    cleanRounds() {
        this.restService.cleanRounds(
            () => {
                this.refresh();
            },
            (error) => {
                this.setError(error);
            }
        );
    }
}
