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

package com.batyuta.challenge.lottoland.converter;

import com.batyuta.challenge.lottoland.model.UserEntity;
import com.batyuta.challenge.lottoland.service.UserEntityService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Convertor {@link String} to {@link UserEntity}.
 */
@Component
public class UserConverter implements Converter<String, UserEntity> {

	/**
	 * User Entity Service.
	 */
	private final UserEntityService service;

	/**
	 * Default constructor.
	 * @param userService User Entity service
	 */
	public UserConverter(final UserEntityService userService) {
		this.service = userService;
	}

	/**
	 * Parsing of {@link UserEntity}.
	 * @param userName as string
	 * @return parsed user
	 */
	@Override
	public UserEntity convert(final String userName) {
		try {
			return service.findByUserNameOrNew(userName);
		}
		catch (Exception e) {
			return null;
		}
	}

}
