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

package com.batyuta.challenge.lottoland.enums;

import com.batyuta.challenge.lottoland.exception.ApplicationException;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Interface uses for message localization
 * by key.
 */
public interface I18n {
    /**
     * Gets i18n message key.
     *
     * @return i18n message key
     */
    String getMessageKey();

    /**
     * Localization for standalone runs.
     *
     * @return Localized name
     */
    @Deprecated
    default String getLocalized() {
        try {
            ResourceBundle bundle =
                    ResourceBundle.getBundle("classpath:/messages/messages");
            return bundle.getString(getMessageKey());
        } catch (Exception e) {
            try {
                Properties p = new Properties();
                p.load(getClass().getClassLoader()
                        .getResourceAsStream("messages/messages.properties"));
                return p.getProperty(getMessageKey());
            } catch (IOException ex) {
                throw new ApplicationException(
                        "error.read.property", getMessageKey()
                );
            }
        }
    }
}
