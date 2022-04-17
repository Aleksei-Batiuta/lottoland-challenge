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

package com.batyuta.challenge.lottoland.web;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.batyuta.challenge.lottoland.AbstractSpringBootMockMvcTest;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

/** REST Message test cases. */
@DisplayName("REST Message test cases")
public class RestMessageControllerTest extends AbstractSpringBootMockMvcTest {

  /** Root path. */
  public static final String ROOT_PATH = "/api/messages/";

  /** Root path with parameters. */
  public static final String ROOT_PATH_WITH_PARAMS = ROOT_PATH + "?lang={lang}";

  /**
   * Test data generator.
   *
   * @return test data
   */
  public static Stream<? extends Arguments> testData() {
    return Stream.of(Arguments.of(null, null), Arguments.of("en", null),
        Arguments.of("ru", null), Arguments.of("sk", null));
  }

  /**
   * The test method.
   *
   * @param language the test language/locale
   * @param expected expected result
   */
  @ParameterizedTest(name = "{index}: language={0}, expected={1}")
  @MethodSource("testData")
  public void getRequest(final String language, final Object expected) {

    try {
      MockHttpServletRequestBuilder requestBuilder;
      if (language == null) {
        requestBuilder = get(ROOT_PATH);
      } else {
        requestBuilder = get(ROOT_PATH_WITH_PARAMS, language);
      }
      requestBuilder = requestBuilder.characterEncoding(StandardCharsets.UTF_8);
      MvcResult resultActions = perform(requestBuilder).andDo(log())
          .andExpect(status().isOk())
          .andExpect(
              header().string(HttpHeaders.CONTENT_TYPE, "application/hal+json"))
          .andReturn();
      String actual = resultActions.getResponse().getContentAsString();
      if (expected != null) {
        assertEquals(String
            .format("Test case was failed: {data: {language: %s}", language),
            expected, actual);
      }
    } catch (Exception actual) {
      assertNotNull(String
          .format("Expected result is null: {data: {language: %s}", language),
          expected);
      assertEquals("Unexpected result!", expected.getClass(),
          actual.getClass());
    }
  }
}
