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

import i18next from "i18next";
import {initReactI18next} from "react-i18next";

// import detector from "i18next-browser-languagedetector";
import backend from "i18next-http-backend";
import React from "react";

export const LANGUAGES = [
    {
        value:'en',
        label:'label.language.en',
        name:'English'
    },
    {
        value:'ru',
        label:'label.language.ru',
        name:'Русский'
    }
    ];
export const LANGUAGE_DEFAULT = 'en';
export default class MsgService {
    static instance = null;
    context = [];
    i18n = i18next;

    constructor() {
        this.init();
        this.t = this.t.bind(this);
        this.changeLanguage = this.changeLanguage.bind(this);
    }

    static getInstance() {
        if (MsgService.instance == null) {
            MsgService.instance = new MsgService();
        }
        return MsgService.instance;
    }

    t(msgKey, msgOptions, setter) {
        if (!this.i18n.exists(msgKey)) {
            if (!this.i18n.isInitialized) {
                this.context.push({key: msgKey, options: msgOptions, setter: setter})
            }
            return console.log('Message key was not found!', msgKey);
        }
        setter(this.i18n.t(msgKey, msgOptions));
    }

    changeLanguage(language) {
        return this.i18n.changeLanguage(language);
    }

    refresh() {
        try {
            this.context.forEach(
                (o) => {
                    try {
                        o.setter(this.i18n.t(o.key, o.options))
                    } catch (e) {
                        console.log(e);
                    }
                }
            );
        } finally {
            this.context = [];
        }

    }

    init() {
        this.i18n
            .use(initReactI18next)
            // .use(detector)
            .use(backend)
            .init({
                    fallbackLng: LANGUAGE_DEFAULT, // use en if detected lng is not available
                    lng: LANGUAGE_DEFAULT,
                    preload: LANGUAGES.map(language => language.value),
                    debug: true,
                    interpolation: {
                        escapeValue: false // react already safes from xss
                    },
                    backend: {
                        loadPath: '/api/messages/?lang={{lng}}'
                    },
                },
                (error) => {
                    if (error) {
                        return console.log('something went wrong loading', error);
                    }
                    this.refresh();
                }
            );
    }
}
