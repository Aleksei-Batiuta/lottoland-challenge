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

import com.batyuta.challenge.lottoland.exception.DataException;
import com.batyuta.challenge.lottoland.model.BaseEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Abstract entities service.
 *
 * @param <T>
 *            Entity class
 */
public abstract class AbstractEntityService<T extends BaseEntity<T>> {

    /** Entity repository. */
    private final PagingAndSortingRepository<T, Long> repository;

    /**
     * Default constructor.
     *
     * @param sortingRepository
     *            Entity repository.
     */
    public AbstractEntityService(final PagingAndSortingRepository<T, Long> sortingRepository) {
        this.repository = sortingRepository;
    }

    /**
     * Getter of entity repository.
     *
     * @return repository
     */
    public PagingAndSortingRepository<T, Long> getRepository() {
        return repository;
    }

    /**
     * Find all entities.
     *
     * @param pageable
     *            page settings
     *
     * @return entities
     */
    public Page<T> findAll(final Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Find entity by ID.
     *
     * @param id
     *            entity ID
     *
     * @return entity
     */
    public Optional<T> findById(final Long id) {
        return repository.findById(id);
    }

    /**
     * Save entity into repository.
     *
     * @param entity
     *            entity
     *
     * @return saved entity
     */
    public T save(final T entity) {
        return repository.save(entity);
    }

    /**
     * Replace entity.
     *
     * @param entity
     *            entity
     *
     * @return entity
     */
    public T replace(final T entity) {
        repository.deleteById(entity.getId());
        return repository.save(entity);
    }

    /**
     * Delete entity by ID.
     *
     * @param id
     *            entity ID
     */
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    /**
     * Update entity.
     *
     * @param entity
     *            entity
     *
     * @return updated entity
     */
    public T update(final T entity) {
        Optional<T> repositoryEntity = repository.findById(entity.getId());
        T t = repositoryEntity.orElseThrow(() -> new DataException("error.data.entity.not.found", entity.getId()));
        return updateImpl(t, entity);
    }

    /**
     * Update implementation.
     *
     * @param destination
     *            destination entity
     * @param source
     *            source entity
     *
     * @return updated entity
     */
    protected abstract T updateImpl(T destination, T source);
}
