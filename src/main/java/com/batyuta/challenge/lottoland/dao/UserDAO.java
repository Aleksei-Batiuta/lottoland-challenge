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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User DAO class.
 */
@Component
public final class UserDAO implements CrudDAO<UserEntity> {
    /**
     * Data Repository.
     */
    private final DataRepository repository;

    /**
     * Default Constructor.
     *
     * @param repository data repository
     */
    @Autowired
    public UserDAO(final DataRepository repository) {
        this.repository = repository;

        // Initial Test data
        for (String name : Arrays.asList(
                "ana@yahoo.com",
                "bob@yahoo.com",
                "john@yahoo.com",
                "mary@yahoo.com"
        )) {
            create(new UserEntity(UserEntity.NEW_ENTITY_ID, name));
        }
    }

    @Override
    public UserEntity create(final UserEntity user) {
        List<UserEntity> userList = repository.getUsers();
        if (userList.stream()
                .anyMatch(
                        it -> user.getName().equals(it.getName())
                )
        ) {
            throw new DaoException(
                    "dao.error.email.duplicate",
                    user.getName()
            );
        } else {
            userList.add(user);
            int index = userList.size() - 1;
            user.setId(index);
            return user;
        }
    }

    @Override
    public UserEntity update(final UserEntity user) {
        repository.getUsers().set(user.getId(), user);
        return user;
    }

    @Override
    public UserEntity get(final int id) {
        return repository.getUsers().get(id);
    }

    @Override
    public Collection<UserEntity> getAll() {
        return repository.getUsers().stream()
                .filter(Objects::nonNull)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                Collections::unmodifiableList
                        )
                );
    }

    @Override
    public void delete(final UserEntity user) {
        repository.getUsers().set(user.getId(), null);
    }
}
