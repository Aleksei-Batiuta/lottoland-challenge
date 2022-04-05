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
'use strict';

const React = require('react');
import {Statistics} from "./statistics";
import {Game} from "./game";

const {Msg} = require("../component/msg");

const TAB_GAME = 'game';
const TAB_STATISTICS = 'statistics';
export class Main extends React.Component { // <1>
    constructor(props) {
        super(props);

        this.game = React.createRef();
        this.statistics = React.createRef();
        this.state = {active: TAB_GAME};

        this.activate = this.activate.bind(this);
        this.switchToGame = this.switchToGame.bind(this);
        this.switchToStatistics = this.switchToStatistics.bind(this);
    }

    activate(t) {
        this.setState({active: t})
    }

    switchToGame() {
        this.activate(TAB_GAME);
    }

    switchToStatistics() {
        this.activate(TAB_STATISTICS);
    }

    render() {
        let projectUrl = "#";
        let gameMenuClassName;
        let statisticsMenuClassName;
        let page;
        if (this.state.active === TAB_GAME) {
            gameMenuClassName = 'active';
            statisticsMenuClassName = '';
            page = <Game ref={this.game}/>;
        } else {
            gameMenuClassName = '';
            statisticsMenuClassName = 'active';
            page = <Statistics ref={this.statistics}/>;
        }
        return (
            <div className="main">
                <div className="menu">
                <div className="menu-context">
                    <div className="menu-item">
                        <ul>

                            <li className={gameMenuClassName}>
                                <a href="#" onClick={() => this.switchToGame()}>
                                    <Msg msgKey='title.game'/>
                                </a>
                            </li>
                            <li className={statisticsMenuClassName}>
                                <a href="#" onClick={() => this.switchToStatistics()}>
                                    <Msg msgKey='title.statistics'/>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div className="logo"/>
                </div>
                </div>
                <div className="context">
                    {page}
                </div>
                <div className="footer">
                    <div className="footer-context">
                        <div className="copyrights">
                            <p><a href={projectUrl}>©
                                <Msg msgKey="project.author"/>
                            </a>&nbsp;2022
                            </p>
                        </div>
                        <div className="version">
                            <p>
                                <Msg msgKey="project.version"/>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}