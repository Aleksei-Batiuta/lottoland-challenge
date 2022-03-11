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

import com.batyuta.challenge.lottoland.exception.DaoException;
import com.batyuta.challenge.lottoland.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * User DAO class.
 */
@Repository
public final class UserDAO implements CrudDAO<UserEntity> {
    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger(UserDAO.class);
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

    @Override
    public UserEntity create(final UserEntity user) {
        try {
            return repository.createUser(user);
        } finally {
            log.info("create: User {} has been created", user);
        }
    }

    @Override
    public UserEntity get(final int userId) {
        try {
            return repository.getUser(userId);
        } catch (Exception e) {
            throw new DaoException("error.dao.user.not.found", userId, e);
        }
    }
}
