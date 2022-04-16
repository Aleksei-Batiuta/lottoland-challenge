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

package com.batyuta.challenge.lottoland.model;

import com.batyuta.challenge.lottoland.concurrency.LockedData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base Entity class.
 *
 * @param <T>
 *            entity type class
 */
@MappedSuperclass
public abstract class BaseEntity<T> extends LockedData implements Serializable, Comparable<T> {

    /** New Entity ID. */
    public static final long NEW_ENTITY_ID = 0L;

    /** Entity ID. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Default no arguments Constructor. */
    protected BaseEntity() {
        this(NEW_ENTITY_ID);
    }

    /**
     * Default constructor.
     *
     * @param entityId
     *            entity ID.
     */
    protected BaseEntity(final Long entityId) {
        this.setId(entityId);
    }

    /**
     * Check the entity to new.
     *
     * @return <code>true</code> if {@link UserEntity#getId()} equals to {@link UserEntity#NEW_ENTITY_ID}
     */
    @JsonIgnore
    public boolean isNew() {
        return this.id == NEW_ENTITY_ID;
    }

    /**
     * Getter of entity ID.
     *
     * @return entity ID
     */
    public Long getId() {
        return read(() -> this.id);
    }

    /**
     * Getter of entity ID.
     *
     * @param entityId
     *            entity ID
     */
    public void setId(final Long entityId) {
        write(() -> this.id = entityId);
    }
}
