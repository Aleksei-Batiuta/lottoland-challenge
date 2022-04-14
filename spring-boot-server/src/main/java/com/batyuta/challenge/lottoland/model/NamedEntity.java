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

import javax.persistence.MappedSuperclass;

/**
 * Named entity base class.
 *
 * @param <T> entity type class
 */
@MappedSuperclass
public abstract class NamedEntity<T> extends BaseEntity<T> {

  /** Name field. */
  private String name;

  /**
   * Default constructor.
   *
   * @param id entity ID
   * @param entityName name field of entity
   */
  protected NamedEntity(final Long id, final String entityName) {
    super(id);
    this.name = entityName;
  }

  /**
   * Getter of entity name.
   *
   * @return entity name
   */
  public String getName() {
    return name;
  }
}
