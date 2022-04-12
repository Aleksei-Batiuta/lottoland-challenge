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
import com.batyuta.challenge.lottoland.model.BaseEntity;
import com.batyuta.challenge.lottoland.utils.PageableUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Abstract Repository Implementation.
 *
 * @param <T> Entity Class
 */
public abstract class BaseEntityRepository<T extends BaseEntity<T>> extends BaseDataEntityRepository<T>
		implements PagingAndSortingRepository<T, Long> {

	/**
	 * Gets all entities from repository which have been sorted.
	 * @param sort sort type, if it's <code>null</code> then returns natural object sorted
	 * list
	 * @return entities
	 */
	@Override
	public Iterable<T> findAll(final Sort sort) {
		Stream<T> entities = getEntities().filter(entity -> !isDeleted(entity));
		return sort(entities, sort);
	}

	/**
	 * Gets entity page from repository.
	 * @param pageSettings page config
	 * @return entities
	 */
	@Override
	public Page<T> findAll(final Pageable pageSettings) {
		Pageable pageable = pageSettings;
		if (pageable == null) {
			pageable = Pageable.unpaged();
		}
		List<T> list = sort(getEntities().filter(entity -> !isDeleted(entity)), pageable.getSort());
		int total = list.size();
		pageable = PageableUtils.fixPageable(pageable, total);
		return subList(list, pageable, total);
	}

	/**
	 * Cutting list to necessary size.
	 * @param inputList input
	 * @param pageSettings page settings
	 * @param total total elements in DB
	 * @return sub list
	 */
	protected Page<T> subList(final List<T> inputList, final Pageable pageSettings, final int total) {
		Pageable pageable = pageSettings;
		List<T> list = inputList;
		if (pageable == null) {
			pageable = Pageable.unpaged();
		}
		if (!pageable.isUnpaged()) {
			list = list.subList((int) pageable.getOffset(),
					Math.min((int) (pageable.getPageSize() + pageable.getOffset()), total));
		}

		return new PageImpl<>(Collections.unmodifiableList(list), pageable, total);
	}

	/**
	 * Getter of the deleted flag.
	 * @param entity entity
	 * @return {@code true} if entity was deleted
	 */
	protected abstract boolean isDeleted(T entity);

	/**
	 * Gets all entities form repository as is.
	 * @return all entities
	 */
	@Override
	public Iterable<T> findAll() {
		return getEntities().filter(entity -> !isDeleted(entity)).collect(Collectors.toList());
	}

	/**
	 * Creates a new entity.
	 * @param entity entity
	 * @param <S> entity class
	 * @return saved entity
	 */
	@Override
	public <S extends T> S save(final S entity) {
		return write(() -> saveImpl(entity));
	}

	/**
	 * Creates new entities.
	 * @param entities entities
	 * @param <S> entity class
	 * @return saved entities
	 */
	@Override
	public <S extends T> Iterable<S> saveAll(final Iterable<S> entities) {
		return write(() -> {
			ArrayList<S> result = new ArrayList<>();
			getEntities().forEach(s -> result.add((S) saveImpl(s)));
			return Collections.unmodifiableList(result);
		});
	}

	/**
	 * Find entity by ID.
	 * @param id entity ID
	 * @return entity
	 * @throws DataException if entity does not exist
	 */
	@Override
	public Optional<T> findById(final Long id) {
		Optional<T> read = read(() -> getEntities().filter(entity -> Objects.equals(entity.getId(), id)).findFirst());
		if (!read.isPresent()) {
			throw new DataException("error.data.entity.not.found", id);
		}
		return read;
	}

	/**
	 * Checks entity to exist in repository.
	 * @param id entity ID
	 * @return <code>true</code> if it exists
	 */
	@Override
	public boolean existsById(final Long id) {
		return read(() -> getEntities().anyMatch(entity -> Objects.equals(entity.getId(), id)));
	}

	/**
	 * Looks out the entities by IDs.
	 * @param entityIds entity IDs
	 * @return entities
	 */
	@Override
	public Iterable<T> findAllById(final Iterable<Long> entityIds) {
		return read(() -> findByIdImpl(entityIds));
	}

	/**
	 * Implementation of {@link this#findAllById(Iterable)}.
	 * @param entityIds entity IDs
	 * @return entities
	 */
	private List<T> findByIdImpl(final Iterable<Long> entityIds) {
		Stream<Long> ids = StreamSupport.stream(entityIds.spliterator(), false);
		return getEntities().filter(entity -> ids.anyMatch(id -> Objects.equals(entity.getId(), id)))
				.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
	}

	/**
	 * Gets count of entities in repository.
	 * @return count of entities
	 */
	@Override
	public long count() {
		return read(() -> getEntities().count());
	}

	/**
	 * Deletes entity by its ID from repository.
	 * @param id entity ID
	 */
	@Override
	public void deleteById(final Long id) {
		findById(id).ifPresent(this::deleteImpl);
	}

	/**
	 * Deletes entity from repository.
	 * @param entity entity
	 */
	@Override
	public void delete(final T entity) {
		if (entity == null) {
			throw new DataException("error.data.entity.null");
		}
		write(() -> {
			deleteImpl(entity);
			return null;
		});
	}

	/**
	 * Deletes entities by IDs from repository.
	 * @param entityIds entities IDs
	 */
	@Override
	public void deleteAllById(final Iterable<? extends Long> entityIds) {
		write(() -> {
			Stream<? extends Long> ids = StreamSupport.stream(entityIds.spliterator(), false);

			getEntities().filter(entity -> ids.anyMatch(id -> Objects.equals(entity.getId(), id)))
					.forEach(this::deleteImpl);
			return null;
		});
	}

	/**
	 * Deletes entities from repository.
	 * @param entities entities
	 */
	@Override
	public void deleteAll(final Iterable<? extends T> entities) {
		write(() -> {
			for (T entity : entities) {
				deleteImpl(entity);
			}
			return null;
		});
	}

	/**
	 * Fresh repository.
	 */
	@Override
	public void deleteAll() {
		write(() -> {
			getEntities().forEach(this::deleteImpl);
			return null;
		});
	}

	/**
	 * todo: It should be reviewed by CRUD concept.
	 * @param entity entity
	 */
	public void update(final T entity) {
		throw new DataException("error.unsupported.operation");
	}

}
