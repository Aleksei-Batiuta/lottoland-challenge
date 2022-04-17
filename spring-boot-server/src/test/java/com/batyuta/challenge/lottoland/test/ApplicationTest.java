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

package com.batyuta.challenge.lottoland.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.batyuta.challenge.lottoland.AbstractSpringBootMockMvcTest;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for Application.
 *
 * @author Aleksei Batyuta aleksei.batiuta@gmail.com
 */
@DisplayName("Simple HTTP request test cases")
public class ApplicationTest extends AbstractSpringBootMockMvcTest {

  /**
   * Test access to main game page.
   *
   * @throws Exception if error appears
   */
  @Test
  public void root() throws Exception {
    perform(get(ROOT_PATH)).andDo(print()).andExpect(status().isOk());
  }

  /**
   * Test root Http page.
   *
   * @throws IOException if error appears
   */
  @Test
  public void rootHttp() throws IOException {

    // Given
    HttpUriRequest request = new HttpGet(getRootUrl());

    // When
    HttpResponse httpResponse =
        HttpClientBuilder.create().build().execute(request);

    // Then
    assertThat("Invalid HTTP response code",
        httpResponse.getStatusLine().getStatusCode(),
        equalTo(HttpStatus.SC_OK));
  }

  /**
   * Test error page.
   *
   * @throws Exception if error appears
   */
  @Test
  public void error() throws Exception {
    perform(get(ROOT_PATH + "error")).andDo(log())
        .andExpect(status().is4xxClientError());
  }

  /**
   * Test main.css.
   *
   * @throws Exception if error appears
   */
  @Test
  public void cssTest() throws Exception {
    perform(get(ROOT_PATH + "css/main.css")).andDo(log())
        .andExpect(status().isOk());
  }

  /**
   * Test lottoland.png.
   *
   * @throws Exception if error appears
   */
  @Test
  public void logoTest() throws Exception {

    perform(get(ROOT_PATH + "image/lottoland.png")).andExpect(status().isOk());
  }

  /**
   * Test favicon.ico.
   *
   * @throws Exception if error appears
   */
  @Test
  public void faviconTest() throws Exception {

    perform(get(ROOT_PATH + "favicon.ico")).andExpect(status().isOk());
  }
}
