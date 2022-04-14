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

package com.batyuta.challenge.lottoland.test;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

import com.batyuta.challenge.lottoland.utils.PageableUtils;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DisplayName("Pageable Utils test cases")
public class PageableUtilsTest {

  /**
   * Test data generator.
   *
   * @return test data
   */
  public static Stream<? extends Arguments> testData() {
    return Stream.of(
        Arguments.of(PageRequest.of(0, 10), 10, PageRequest.of(0, 10)),
        Arguments.of((Pageable) null, 10, Pageable.unpaged()),
        Arguments.of(PageRequest.of(10, 10), 10, PageRequest.of(0, 10)),
        Arguments.of(PageRequest.of(0, 10), 0, PageRequest.of(0, 10)));
  }

  /**
   * The test method.
   *
   * @param pageable the test pageable
   * @param total the test pageable total
   * @param expected expected result
   */
  @ParameterizedTest(name = "{index}: pageable={0}, total={1}, expected={2}")
  @MethodSource("testData")
  public void test(final Pageable pageable, int total, final Object expected) {
    try {
      Pageable actual = PageableUtils.fixPageable(pageable, total);
      assertEquals(
          String.format("Test case was failed: {data: {pageable: %s, total: %d}", pageable, total),
          expected,
          actual);
    } catch (IllegalArgumentException actual) {
      assertNotNull(
          String.format(
              "Expected result is null: {data: {pageable: %s, total: %d}", pageable, total),
          expected);
      assertEquals("Unexpected result!", expected.getClass(), actual.getClass());
    }
  }
}
