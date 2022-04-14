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
import { Msg } from './msg';
import PropTypes from 'prop-types';

export class Pagination extends React.Component {
  constructor(props) {
    super(props);
    this.setNumber = this.setNumber.bind(this);
    this.setPageSize = this.setPageSize.bind(this);
  }

  setNumber(page) {
    this.props.refreshTable(page, this.props.options.size);
  }

  setPageSize(pageSize) {
    this.props.refreshTable(0, pageSize);
  }

  render() {
    const first = this.props.options.first;
    const last = this.props.options.last;
    const empty = this.props.options.empty;
    const totalPages = this.props.options.totalPages ? this.props.options.totalPages : 1;
    const number = this.props.options.number ? this.props.options.number : 0;
    const size = this.props.options.size;

    const setNumber = this.setNumber;
    const setPageSize = this.setPageSize;

    const pageSizeOptions = [10, 20, 30, 40, 50].map((pageSize) => (
      <option key={pageSize} value={pageSize}>
        {pageSize}
      </option>
    ));
    return (
      <div className="pagination">
        <button onClick={() => setNumber(0)} disabled={first || empty}>
          {'<<'}
        </button>{' '}
        <button onClick={() => setNumber(number - 1)} disabled={first || empty}>
          {'<'}
        </button>{' '}
        <button onClick={() => setNumber(number + 1)} disabled={last || empty}>
          {'>'}
        </button>{' '}
        <button onClick={() => setNumber(totalPages - 1)} disabled={last || empty}>
          {'>>'}
        </button>{' '}
        <span>
          <Msg msgKey="label.page" />{' '}
          <strong>
            {number !== 0 ? number + 1 : 1} <Msg msgKey="label.page.of" /> {totalPages}
          </strong>{' '}
        </span>
        <span>
          | <Msg msgKey="label.page.goto" />:{' '}
          <input
            type="number"
            min={1}
            max={totalPages}
            value={number + 1}
            onChange={(e) => {
              if (e.target.value) {
                setNumber(Number(e.target.value) - 1);
              } else {
                setNumber(0);
              }
            }}
            style={{ width: '100px' }}
          />{' '}
        </span>
        <span>
          | <Msg msgKey="label.page.show" />:{' '}
          <select
            value={size}
            onChange={(e) => {
              setPageSize(Number(e.target.value));
            }}>
            {pageSizeOptions}
          </select>
        </span>
      </div>
    );
  }
}

// Specifies the value types for props:
Pagination.propTypes = {
  options: PropTypes.shape({
    first: PropTypes.bool,
    last: PropTypes.bool,
    empty: PropTypes.bool,
    totalPages: PropTypes.number,
    number: PropTypes.number,
    size: PropTypes.number
  }),
  refreshTable: PropTypes.func
};
