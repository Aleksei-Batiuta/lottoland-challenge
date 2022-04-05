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
const {RoundList} = require("./roundList");
const {Msg} = require("./msg");

export class Game extends React.Component { // <1>
    constructor(props) {
        super(props);

        this.state = {rounds: []};

        this.refresh = this.refresh.bind(this);
        this.generate = this.generate.bind(this);
        this.cleanRounds = this.cleanRounds.bind(this);
    }

    componentDidMount() { // <2>
        this.refresh();
    }

    render() { // <3>
        return (
            <div>
                <RoundList rounds={this.state.rounds}/>
                <div className="button-panel">
                    <div className="button">
                        <button onClick={this.generate}><Msg msgKey='label.play.round'/></button>
                    </div>
                    <div className="button">
                        <button onClick={this.cleanRounds}><Msg msgKey='label.restart.game'/></button>
                    </div>
                </div>
            </div>
        )
    }

    refresh() {
        fetch("/api/rounds/?page=1&size=10&sortString=desc%2Cid", {
            method: 'GET',
            headers: new Headers(),
        })
            .then(
                (res) => res.json()
            )
            .then(
                (data) => {
                    this.setState({rounds: data});
                    console.log(data);
                }
            )
            .catch((err) => console.log(err))
            .finally();
    }

    generate() {
        fetch(
            "/api/rounds/generate/1",
            {
                method: 'POST',
                headers: new Headers(),
            }
        )
            .then(
                () => this.refresh(this)
            )
            .catch((err) => console.log(err));
    }

    cleanRounds() {
        fetch("/api/rounds/delete/1", {
            method: 'DELETE',
            headers: new Headers(),
        })
            .then(
                () => this.refresh(this)
            )
            .catch((err) => console.log(err));
    }
}