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

package com.batyuta.challenge.lottoland.repository;

import com.batyuta.challenge.lottoland.exception.DataException;
import com.batyuta.challenge.lottoland.model.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

/**
 * User repository implementation class.
 */
@Repository
public class UserEntityRepository extends BaseEntityRepository<UserEntity> {

	/**
	 * Check user to deleted state.
	 * @param entity user entity
	 * @return <code>true</code> if user already deleted
	 */
	@Override
	protected boolean isDeleted(final UserEntity entity) {
		return entity == null;
	}

	/**
	 * Find user by username.
	 * @param userName username
	 * @return user
	 * @throws DataException if entity does not exist
	 */
	public UserEntity findByName(final String userName) {
		Optional<UserEntity> read = read(
				() -> getEntities().filter(entity -> Objects.equals(entity.getName(), userName)).findFirst());
		return read.orElseThrow(() -> new DataException("error.data.entity.not.found", userName));
	}

}
