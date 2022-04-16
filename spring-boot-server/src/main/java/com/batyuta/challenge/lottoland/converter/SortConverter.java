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

package com.batyuta.challenge.lottoland.converter;

import java.util.Arrays;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/** Convertor {@link String} to {@link Sort}. */
@Component
public class SortConverter implements Converter<String, Sort> {

  /**
   * Parsing of {@link Sort}.
   *
   * @param sortString as string
   * @return parsed sort
   */
  @Override
  public Sort convert(final String sortString) {
    Sort sort = null;
    String[] split = sortString.split(",");
    Sort.Direction direction = null;
    if (split.length > 0) {
      direction = Sort.Direction.valueOf(split[0].toUpperCase());
    }
    if (split.length > 1) {
      String[] fields = Arrays.copyOfRange(split, 1, split.length);
      sort = Sort.by(direction, fields);
    }
    return sort;
  }
}
