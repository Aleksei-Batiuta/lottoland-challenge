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

import { Msg } from './msg';
import PropTypes from 'prop-types';
import React from 'react';

export class Round extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    let round = this.props.round;

    let rowClassName = 'legend icon sprite bg-' + round.status.name.toLowerCase();
    let player1ClassName = 'legend icon sprite bg-' + round.player1.name.toLowerCase();
    let player2ClassName = 'legend icon sprite bg-' + round.player2.name.toLowerCase();
    let resultClassName = rowClassName + ' ' + this.getResultClassName(round);

    return (
      <tr>
        <td>{this.props.round.id}</td>
        <td>
          <div className="item">
            <div className={player1ClassName} />
            <div className="name">
              <Msg msgKey={this.props.round.player1.messageKey} />
            </div>
          </div>
        </td>
        <td>
          <div className="item">
            <div className={player2ClassName} />
            <div className="name">
              <Msg msgKey={this.props.round.player2.messageKey} />
            </div>
          </div>
        </td>
        <td>
          <div className="item">
            <div className={'legend-container'}>
              <div className={resultClassName} />
            </div>
            <div className={'name ' + 'fg-' + this.props.round.status.name.toLowerCase()}>
              <Msg msgKey={this.props.round.status.messageKey} />
            </div>
          </div>
        </td>
      </tr>
    );
  }

  getResultClassName(round) {
    let resultClassName = '';

    let rockAndPaper = 's1x2';
    let rockAndScissors = 's0x2';
    let scissorsAndPaper = 's2x2';
    let handShake = 's3x0';
    switch (round.player1.name) {
      case 'ROCK':
        switch (round.player2.name) {
          case 'PAPER':
            resultClassName += rockAndPaper;
            break;
          case 'SCISSORS':
            resultClassName += rockAndScissors;
            break;
          default:
            resultClassName += handShake;
        }
        break;
      case 'SCISSORS':
        switch (round.player2.name) {
          case 'ROCK':
            resultClassName += rockAndScissors;
            break;
          case 'PAPER':
            resultClassName += scissorsAndPaper;
            break;
          default:
            resultClassName += handShake;
        }
        break;
      case 'PAPER':
        switch (round.player2.name) {
          case 'ROCK':
            resultClassName += rockAndPaper;
            break;
          case 'SCISSORS':
            resultClassName += scissorsAndPaper;
            break;
          default:
            resultClassName += handShake;
        }
        break;
    }
    return resultClassName;
  }
}

Round.propCustomEnum = {
  name: PropTypes.string,
  messageKey: PropTypes.string
};
// Specifies the value types for props:
Round.propTypes = {
  round: PropTypes.shape({
    id: PropTypes.number,
    status: PropTypes.shape(Round.propCustomEnum),
    player1: PropTypes.shape(Round.propCustomEnum),
    player2: PropTypes.shape(Round.propCustomEnum)
  })
};
