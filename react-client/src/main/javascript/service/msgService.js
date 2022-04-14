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

import i18next from 'i18next';
import { initReactI18next } from 'react-i18next';
import backend from 'i18next-http-backend';

export const LANGUAGES = [
  {
    value: 'en',
    label: 'label.language.en',
    name: 'English'
  },
  {
    value: 'ru',
    label: 'label.language.ru',
    name: 'Русский'
  }
];
export const LANGUAGE_DEFAULT = 'en';

export function initLanguages(callback) {
  i18next
    .use(initReactI18next)
    .use(backend)
    .init(
      {
        fallbackLng: LANGUAGE_DEFAULT,
        // use en if detected lng is not available
        lng: LANGUAGE_DEFAULT,
        preload: LANGUAGES.map((language) => language.value),
        debug: true,
        interpolation: {
          escapeValue: false // react already safes from xss
        },
        backend: {
          loadPath: '/api/messages/?lang={{lng}}'
        }
      },
      (error) => {
        if (error) {
          return console.log('something went wrong loading', error);
        }
        callback(LANGUAGE_DEFAULT);
      }
    );
}

export function translate(msgKey, msgOptions, setter) {
  if (!i18next.exists(msgKey)) {
    return console.log('Message key was not found!', msgKey);
  }
  let msg = i18next.t(msgKey, msgOptions);
  setter(msg);
  return msg;
}

export function changeLanguage(language, callback) {
  return i18next.changeLanguage(language, () => callback(language));
}
