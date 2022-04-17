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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.batyuta.challenge.lottoland.AbstractSpringBootTest;
import com.batyuta.challenge.lottoland.vo.ErrorBody;
import com.batyuta.challenge.lottoland.vo.RestResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/** JSON Converter Test Case. */
@DisplayName("JSON Converter Test Case")
public class JsonConverterTest extends AbstractSpringBootTest {
  /** JSON Converter. */
  @Autowired
  private JsonConverter jsonConverter;

  /** JSON parsing with Object Type Reference test. */
  @DisplayName("JSON parsing with Object Type Reference test")
  @Test
  public void testToObjectTypeReference() {
    String msg =
        "" + "{" + "\"status\":{\"codeValue\":403,\"code\":\"Forbidden\"},"
            + "\"body\":{\"message\":\"/exception\"},"
            + "\"timestamp\":\"2022-04-16T07:39:34.234\"}";
    RestResponse<ErrorBody> restResponse = jsonConverter.toObject(msg,
        new TypeReference<RestResponse<ErrorBody>>() {});
    assertNotNull(restResponse);
  }

  /** JSON parsing with Object Class test. */
  @DisplayName("JSON parsing with Object Class test")
  @Test
  public void testToObjectClass() {
    String msg = "{\"codeValue\":403,\"code\":\"Forbidden\"}";
    HttpStatus httpStatus = jsonConverter.toObject(msg, HttpStatus.class);
    assertNotNull(httpStatus);
  }

  /** JSON serialization test. */
  @DisplayName("JSON serialization test")
  @Test
  public void testToString() {
    String expected = "\"" + HttpStatus.FORBIDDEN.name() + "\"";
    String actual = jsonConverter.toString(HttpStatus.FORBIDDEN, false);
    assertNotNull(actual);
    assertEquals(expected, actual);
  }
}
