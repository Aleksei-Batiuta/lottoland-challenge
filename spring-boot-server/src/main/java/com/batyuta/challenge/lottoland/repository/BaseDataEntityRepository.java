/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.batyuta.challenge.lottoland.repository;

import com.batyuta.challenge.lottoland.concurrency.LockedData;
import com.batyuta.challenge.lottoland.config.SortComparatorFactory;
import com.batyuta.challenge.lottoland.model.BaseEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

/**
 * Thread safe abstractrepository implementation.
 *
 * @param <T> repository entities class
 */
public abstract class BaseDataEntityRepository<T extends BaseEntity<T>>
    extends LockedData {

  /** This is entity table index. */
  private final AtomicLong index = new AtomicLong(0L);

  /** This is entity table. */
  private final List<T> entities = new ArrayList<>();

  /** Sort Comparator Factory. */
  @Autowired
  private SortComparatorFactory sortComparatorFactory;

  /**
   * Getter of entities.
   *
   * @return entities
   */
  protected Stream<T> getEntities() {
    return read(entities::stream);
  }

  /**
   * Implementation of save operation.
   *
   * @param entity entity
   * @param <S> entity type class
   * @return saved entity
   */
  protected <S extends T> S saveImpl(final S entity) {
    if (entity == null) {
      return null;
    }
    if (entity.getId() == BaseEntity.NEW_ENTITY_ID) {
      setId(entity);
    }
    entities.add(entity);
    return entity;
  }

  /**
   * Implementation of delete operation.
   *
   * @param entity entity
   */
  protected void deleteImpl(final T entity) {
    write(() -> {
      entities.remove(entity);
      return null;
    });
  }

  /**
   * Updates Entity ID before save operation.
   *
   * @param entity entity
   * @param <S> entity type class
   */
  private <S extends T> void setId(final S entity) {
    entity.setId(index.incrementAndGet());
  }

  /**
   * Sorts entities.
   *
   * @param entityList entities
   * @param sort sort type.
   * @return sorted entity list
   */
  protected List<T> sort(final Stream<T> entityList, final Sort sort) {
    return entityList.sorted((o1, o2) -> compare(sort, o1, o2))
        .collect(Collectors.collectingAndThen(Collectors.toList(),
            Collections::unmodifiableList));
  }

  /**
   * Compares two entities by sort type.
   *
   * @param sort sort type
   * @param o1 the first entity
   * @param o2 the second entity
   * @return compare result
   */
  private int compare(final Sort sort, final T o1, final T o2) {
    SortComparator<T> comparator = sortComparatorFactory.sortComparator(sort);
    return comparator.compare(o1, o2);
  }
}
