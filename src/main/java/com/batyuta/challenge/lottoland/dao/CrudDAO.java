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
import com.batyuta.challenge.lottoland.model.BaseEntity;

import java.util.Collection;

/**
 * Interface for the CRUD DAO.
 *
 * @param <T> entity class
 */
public interface CrudDAO<T extends BaseEntity> {

    /**
     * Gets entity by ID.
     *
     * @param id entity ID
     * @return entity instance
     */
    default T get(int id) {
        throw new DaoException(
                "error.unsupported.operation",
                new UnsupportedOperationException()
        );
    }

    /**
     * Gets all entities.
     *
     * @return all entities
     * todo: the paginator should be used instead of this method
     */
    default Collection<T> getAll() {
        throw new DaoException(
                "error.unsupported.operation",
                new UnsupportedOperationException()
        );
    }

    /**
     * Saves a new entity.
     *
     * @param t entity
     * @return entity
     */
    default T create(T t) {
        throw new DaoException(
                "error.unsupported.operation",
                new UnsupportedOperationException()
        );
    }

    /**
     * Updates entity.
     *
     * @param t entity
     * @return updated entity
     */
    default T update(T t) {
        throw new DaoException(
                "error.unsupported.operation",
                new UnsupportedOperationException()
        );
    }

    /**
     * Deletes entity.
     *
     * @param t entity
     */
    default void delete(T t) {
        throw new DaoException(
                "error.unsupported.operation",
                new UnsupportedOperationException()
        );
    }
}

