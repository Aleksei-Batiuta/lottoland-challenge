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

import com.batyuta.challenge.lottoland.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * The General Application Service.
 */
@Component
public class ApplicationService {
    /**
     * User DAO Service.
     */
    private final UserService userService;

    /**
     * Default constructor.
     *
     * @param service user DAO service
     */
    @Autowired
    public ApplicationService(final UserService service) {
        this.userService = service;
    }

    /**
     * Getter for all users.
     *
     * @return users
     * @see UserService#getAllUsers()
     */
    public Collection<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Saves user.
     *
     * @param user user
     * @see UserService#save(UserEntity)
     */
    public void saveUser(final UserEntity user) {
        userService.save(user);
    }
}
