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

import React from 'react';
import { translate } from '../service/msgService';
import PropTypes from 'prop-types';

export class Msg extends React.Component {
  constructor(props) {
    super(props);
    this.msgKey = props.msgKey;
    this.msgOptions = props.msgOptions;
    this.state = { msg: this.msgKey };
  }

  render() {
    // let msg = translate(this.msgKey, this.msgOptions, (msgText) => {
    //     if (this.state?.msg !== msgText) {
    //         this.setState({ msg: msgText });
    //     }
    // });
    let msg = translate(this.msgKey, this.msgOptions, () => {});
    if (msg === null) {
      msg = '';
    }
    return <>{msg}</>;
  }
}

// Specifies the value types for props:
Msg.propTypes = {
  msgKey: PropTypes.string,
  msgOptions: PropTypes.array
};
