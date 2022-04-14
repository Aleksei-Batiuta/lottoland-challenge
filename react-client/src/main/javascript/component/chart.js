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
import PropTypes from 'prop-types';

export class Chart extends React.Component {
  constructor(props) {
    super(props);
  }

  calculatePercent(value, total) {
    if (total === 0) {
      return 0;
    }
    return (value / total) * 100;
  }

  render() {
    let conic = 'conic-gradient(';
    let total = 0;
    this.props.data.forEach((element) => {
      total = total + element.count;
    });
    let previousPercent = 0;
    this.props.data.forEach((element, index) => {
      let currentPercent = this.calculatePercent(element.count, total);
      previousPercent = previousPercent + currentPercent;
      conic +=
        (index !== 0 ? ' ,' : '') +
        element.color +
        (index !== 0 ? ' 0 ' : ' ') +
        previousPercent.toFixed(0) +
        '%';
    });
    conic += ')';
    let style = {
      background: conic,
      borderRadius: '50%',
      width: '10vw',
      height: '10vw',
      padding: '20px'
    };
    return <div className="chart" style={style} />;
  }
}

// Specifies the value types for props:
Chart.propTypes = {
  data: PropTypes.arrayOf(PropTypes.shape({ color: PropTypes.string, count: PropTypes.number }))
};
