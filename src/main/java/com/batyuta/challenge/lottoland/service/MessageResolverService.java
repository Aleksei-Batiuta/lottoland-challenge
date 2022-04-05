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

package com.batyuta.challenge.lottoland.service;

import com.batyuta.challenge.lottoland.config.ExposedResourceMessageBundleSource;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

@Service
public class MessageResolverService {
    private final MessageSource messageSource;

    public MessageResolverService(MessageSource sources) {
        this.messageSource = sources;
    }

    public Map<String, String> getMessages(Locale locale) {
        Properties properties = ((ExposedResourceMessageBundleSource) messageSource)
                .getMessages(locale);
        Map<String, String> messagesMap = new HashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            messagesMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return messagesMap;
    }
}