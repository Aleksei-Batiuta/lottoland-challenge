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

/**
 * User entity.
 */
@Entity
@ToString
public class UserEntity extends NamedEntity {
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
    public UserEntity(final int userId,
                      final String userName) {
        super(userId, userName);
    }
}
