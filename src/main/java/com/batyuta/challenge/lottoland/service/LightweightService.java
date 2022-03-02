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

import com.batyuta.challenge.lottoland.model.BaseEntity;
import com.batyuta.challenge.lottoland.vo.BaseVO;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Lightweight Service Interface.
 *
 * @param <T> entity class
 * @param <V> view class
 */
public interface LightweightService<T extends BaseEntity, V extends BaseVO> {
    /**
     * Converts view to entity.
     *
     * @param view view
     * @return entity
     */
    T toEntity(V view);

    /**
     * Converts view to entity.
     *
     * @param entity entity
     * @return view
     */
    V toView(T entity);

    /**
     * Converts collection of views to entity collection.
     *
     * @param views views
     * @return entities
     */
    default Collection<T> getEntities(Collection<V> views) {
        return views.stream()
                .map(this::toEntity)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                Collections::unmodifiableList
                        )
                );
    }

    /**
     * Converts collection of views to entity collection.
     *
     * @param entities entities
     * @return views
     */
    default Collection<V> getViews(Collection<T> entities) {
        return entities.stream()
                .map(this::toView)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                Collections::unmodifiableList
                        )
                );
    }
}
