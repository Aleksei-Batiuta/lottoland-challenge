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

import com.batyuta.challenge.lottoland.dao.UserDAO;
import com.batyuta.challenge.lottoland.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * User DAO Service.
 */
@Component
public class UserService {
    /**
     * User DAO.
     */
    private final UserDAO userDAO;

    /**
     * Default Constructor.
     *
     * @param dao user DAO
     */
    @Autowired
    public UserService(final UserDAO dao) {
        this.userDAO = dao;
    }

    /**
     * Getter for all users.
     *
     * @return users
     * @see UserDAO#getAll()
     */
    public Collection<UserEntity> getAllUsers() {
        return userDAO.getAll();
    }

    /**
     * Saves user.
     *
     * @param user user
     * @return saved user
     * @see UserDAO#create(UserEntity)
     * @see UserDAO#update(
     *com.batyuta.challenge.lottoland.model.BaseEntity)
     */
    public UserEntity save(final UserEntity user) {
        UserEntity userEntity;
        if (user.isNew()) {
            userEntity = userDAO.create(user);
        } else {
            userEntity = userDAO.update(user);
        }
        return userEntity;
    }

    /**
     * Gets user by user ID.
     *
     * @param userId user ID
     * @return user
     */
    public UserEntity getUserById(final int userId) {
        return userDAO.get(userId);
    }
}
