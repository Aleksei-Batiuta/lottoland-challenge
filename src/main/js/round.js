/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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

import React from "react";
import {UpdateDialog} from "./updateDialog";

export class Round extends React.Component {
    constructor(props) {
        super(props);
        this.handleDelete = this.handleDelete.bind(this);
    }

    handleDelete() {
        this.props.onDelete(this.props.round);
    }

    render() {
        return (
            <tr>
                <td>{this.props.round.entity.id}</td>
                <td>{this.props.round.entity.player1}</td>
                <td>{this.props.round.entity.player2}</td>
                <td>{this.props.round.entity.status}</td>
            </tr>
        )
    }
}