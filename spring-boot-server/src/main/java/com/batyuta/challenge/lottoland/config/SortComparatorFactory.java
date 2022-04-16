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

package com.batyuta.challenge.lottoland.config;

import com.batyuta.challenge.lottoland.repository.SortComparator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;

/** Comparator Factory class. */
@Configuration
@EnableAutoConfiguration
public class SortComparatorFactory {

  /**
   * Creates a new instance of comparator.
   *
   * @param sort sort type
   * @param <T> type of compared objects
   * @return comparator
   */
  @Bean(autowireCandidate = false)
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public <T extends Comparable<? super T>> SortComparator<T> sortComparator(
      final Sort sort) {
    return new SortComparator<>(sort);
  }
}
