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

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Base Entity class.
 */
@MappedSuperclass
public abstract class BaseEntity extends LockedData implements Serializable {
    /**
     * New Entity ID.
     */
    public static final int NEW_ENTITY_ID = -1;
    /**
     * Entity ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Default no arguments Constructor.
     */
    protected BaseEntity() {
        this(NEW_ENTITY_ID);
    }

    /**
     * Default constructor.
     *
     * @param id entity ID.
     */
    protected BaseEntity(final Integer id) {
        this.setId(id);
    }

    /**
     * Check the entity to new.
     *
     * @return <code>true</code> if {@link UserEntity#getId()} equals to
     * {@link UserEntity#NEW_ENTITY_ID}
     */
    public boolean isNew() {
        return this.id == NEW_ENTITY_ID;
    }

    /**
     * Getter of entity ID.
     *
     * @return entity ID
     */
    public int getId() {
        return read(() -> this.id);
    }

    /**
     * Getter of entity ID.
     *
     * @param id entity ID
     */
    public void setId(int id) {
        write(() -> this.id = id);
    }
}
