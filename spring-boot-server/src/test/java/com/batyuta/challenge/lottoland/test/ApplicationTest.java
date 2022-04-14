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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Unit test for Application.
 *
 * @author Aleksei Batyuta aleksei.batiuta@gmail.com
 */
@Order(3)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@DisplayName("Simple HTTP request test cases")
public class ApplicationTest {

  /** Root path. */
  public static final String ROOT_PATH = "/";

  /** Test Server port. */
  // @LocalServerPort
  private static final int SERVER_PORT = 8080;

  /** Mock Model View Controller. */
  @Autowired private MockMvc mockMvc;

  /**
   * Test access to main game page.
   *
   * @throws Exception if error appears
   */
  @Test
  public void root() throws Exception {
    this.mockMvc.perform(get(ROOT_PATH)).andDo(print()).andExpect(status().isOk());
  }

  /**
   * Test root JSP page.
   *
   * @throws IOException if error appears
   */
  @Test
  public void rootJsp() throws IOException {

    // Given
    HttpUriRequest request = new HttpGet("http://localhost:" + SERVER_PORT + ROOT_PATH);

    // When
    HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

    // Then
    assertThat(
        "Invalid HTTP response code",
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
    this.mockMvc
        .perform(get(ROOT_PATH + "error"))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  /**
   * Test main.css.
   *
   * @throws Exception if error appears
   */
  @Test
  public void cssTest() throws Exception {
    this.mockMvc.perform(get(ROOT_PATH + "css/main.css")).andDo(print()).andExpect(status().isOk());
  }

  /**
   * Test lottoland.png.
   *
   * @throws Exception if error appears
   */
  @Test
  public void logoTest() throws Exception {

    this.mockMvc.perform(get(ROOT_PATH + "image/lottoland.png")).andExpect(status().isOk());
  }

  /**
   * Test favicon.ico.
   *
   * @throws Exception if error appears
   */
  @Test
  public void faviconTest() throws Exception {

    this.mockMvc.perform(get(ROOT_PATH + "favicon.ico")).andExpect(status().isOk());
  }
}
