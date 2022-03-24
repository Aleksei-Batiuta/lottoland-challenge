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

package com.batyuta.challenge.lottoland.dao;

import com.batyuta.challenge.lottoland.annotation.LogEntry;
import com.batyuta.challenge.lottoland.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * User DAO class.
 */
@Repository
public class UserDAO implements CrudDAO<UserEntity> {
    /**
     * Data Repository.
     */
    private final DataRepository repository;

    /**
     * Default Constructor.
     *
     * @param dataRepository data repository
     */
    @Autowired
    public UserDAO(final DataRepository dataRepository) {
        this.repository = dataRepository;
    }

    /**
     * Creates a new user in repository.
     *
     * @param user user
     * @return created user
     */
    @Override
    @LogEntry
    public UserEntity create(final UserEntity user) {
        return repository.createUser(user);
    }

    /**
     * Gets user by user ID from repository.
     *
     * @param userId user ID
     * @return user
     */
    @Override
    @LogEntry
    public UserEntity get(final int userId) {
        return repository.getUser(userId);
    }
}
