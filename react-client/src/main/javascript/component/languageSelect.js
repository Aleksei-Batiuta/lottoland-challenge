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

import React from "react";
import MsgService, {LANGUAGE_DEFAULT, LANGUAGES} from "../service/msgService";

export class LanguageSelect extends React.Component {
    constructor(props) {
        super(props);
        this.state = {language: LANGUAGE_DEFAULT};
        this.refresh = this.refresh.bind(this);
        this.handleChangeLanguage = this.handleChangeLanguage.bind(this);
    }

    handleChangeLanguage(event) {
        MsgService.getInstance().changeLanguage(event.target.value);
        this.setState( {language: event.target.value });
        this.props.handleChange(event);
    }
    refresh() {

    }

    componentDidMount() {
        this.refresh();
    }

    render() {
        return (
            <select name='language' value={this.state.language} onChange={this.handleChangeLanguage}>
                {LANGUAGES.map((language, i) => (
                    <option key={i} value={language.value} data-label={language.label}>
                        {language.name}
                    </option>
                ))}
            </select>
        )
    }
}
