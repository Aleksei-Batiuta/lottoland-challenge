/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.batyuta.challenge.lottoland.config;

import static com.batyuta.challenge.lottoland.enums.I18n.CLASSPATH_MESSAGES;

import com.batyuta.challenge.lottoland.enums.I18n;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Locale Custom Configuration. */
@Configuration
@EnableAutoConfiguration
public class LocaleConfiguration {

  /**
   * Custom configuration of {@link MessageSource}. Sets the localized message
   * location, localization encoding and flag to show message key value if this
   * message was not found.
   *
   * @return message source
   */
  @Bean
  public MessageSource messageSource() {
    ExposedResourceMessageBundleSource messageSource =
        new ExposedResourceMessageBundleSource();
    messageSource.setBasename(CLASSPATH_MESSAGES);
    messageSource.setDefaultEncoding(I18n.ENCODING_MESSAGES);
    messageSource.setUseCodeAsDefaultMessage(true);
    return messageSource;
  }
}
