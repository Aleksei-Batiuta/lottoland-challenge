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

package com.batyuta.challenge.lottoland;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

/** Abstract Base Spring Boot Mock MVC Test Case Class. */
@AutoConfigureMockMvc
public abstract class AbstractSpringBootMockMvcTest
    extends AbstractSpringBootTest {
  /** Mock Model View Controller. */
  @Autowired
  private MockMvc mockMvc;

  /**
   * Getter of Mock MVC.
   *
   * @return mock MVC
   */
  public MockMvc getMockMvc() {
    return this.mockMvc;
  }

  /**
   * Preform Mock MVC request, see {@link MockMvc#perform(RequestBuilder)}.
   *
   * @param builder request builder
   * @return action result
   * @throws Exception throws if any error appears
   */
  public ResultActions perform(RequestBuilder builder) throws Exception {
    return this.mockMvc.perform(builder);
  }
}
