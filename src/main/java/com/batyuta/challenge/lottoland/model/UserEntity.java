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

import lombok.ToString;

import javax.persistence.Entity;
import java.util.Comparator;

/**
 * User entity.
 */
@Entity
@ToString
public class UserEntity extends NamedEntity<UserEntity> {
    /**
     * Default no-arguments constructor.
     */
    public UserEntity() {
        this(NEW_ENTITY_ID, null);
    }

    /**
     * Default Constructor.
     *
     * @param userId   user ID
     * @param userName username
     */
    public UserEntity(final Long userId,
                      final String userName) {
        super(userId, userName);
    }

    /**
     * Entities comparator.
     *
     * @param o object to compare
     * @return compare result
     */
    @Override
    public int compareTo(final UserEntity o) {
        Comparator<UserEntity> comparator =
                Comparator.nullsFirst(
                        Comparator.comparing(BaseEntity::getId)
                );
        return comparator.compare(this, o);
    }
}
