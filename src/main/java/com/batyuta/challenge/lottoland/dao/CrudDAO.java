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

import com.batyuta.challenge.lottoland.model.BaseEntity;

import java.util.Collection;
import java.util.Optional;

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
    Optional<T> get(int id);

    /**
     * Gets all entities.
     *
     * @return all entities
     * @deprecated it should be removed in the feature,
     * the paginator should be used instead of this method
     */
    @Deprecated
    Collection<T> getAll();

    /**
     * Saves a new entity.
     *
     * @param t entity
     * @return entity ID.
     */
    int create(T t);

    /**
     * Updates entity.
     *
     * @param t entity
     */
    void update(T t);

    /**
     * Deletes entity.
     *
     * @param t entity
     */
    void delete(T t);
}

