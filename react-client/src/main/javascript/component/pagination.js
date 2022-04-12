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
        this.state = {
            first: props.options?.first,
            last: props.options?.last,
            empty: props.options?.empty,
            totalPages: props.options?.totalPages,
            number: props.options?.number,
            size: props.options?.size,
            refreshTable: props.refreshTable
        };
        this.update = this.update.bind(this);
        this.setNumber = this.setNumber.bind(this);
        this.setPageSize = this.setPageSize.bind(this);
        this.refreshTable = this.refreshTable.bind(this);
    }

    update(props) {
        this.setState({
            first: props.first,
            last: props.last,
            empty: props.empty,
            totalPages: props.totalPages,
            number: props.number,
            size: props.size,
            refreshTable: props.refreshTable
        });
    }

    setNumber(page) {
        this.setState({
            first: this.state.first,
            last: this.state.last,
            empty: this.state.empty,
            totalPages: this.state.totalPages,
            number: page,
            size: this.state.size,
            refreshTable: this.state.refreshTable
        });
        this.refreshTable();
    }

    setPageSize(pageSize) {
        this.setState({
            first: this.state.first,
            last: this.state.last,
            empty: this.state.empty,
            totalPages: this.state.totalPages,
            number: 0,
            size: pageSize,
            refreshTable: this.state.refreshTable
        });
        this.refreshTable();
    }

    refreshTable() {
        this.state.refreshTable(this.state.number, this.state.size);
    }

    render() {
        const first = this.state.first;
        const last = this.state.last;
        const empty = this.state.empty;
        const totalPages = this.state.totalPages;
        const number = this.state.number;
        const size = this.state.size;

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
                        {number + 1} <Msg msgKey="label.page.of" />{' '}
                        {totalPages !== 0 ? totalPages : 1}
                    </strong>{' '}
                </span>
                <span>
                    | <Msg msgKey="label.page.goto" />:{' '}
                    <input
                        type="number"
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
