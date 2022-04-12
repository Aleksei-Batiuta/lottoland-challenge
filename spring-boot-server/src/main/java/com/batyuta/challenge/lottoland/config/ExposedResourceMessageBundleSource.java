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

package com.batyuta.challenge.lottoland.config;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Properties;

/**
 * Custom message bundle class to read all i18n keys.
 */
public class ExposedResourceMessageBundleSource extends ReloadableResourceBundleMessageSource {

	/**
	 * Gets all key-value pairs for locale.
	 * @param locale locale
	 * @return key-value pairs
	 */
	public Properties getMessages(final Locale locale) {
		return getMergedProperties(locale).getProperties();
	}

}
