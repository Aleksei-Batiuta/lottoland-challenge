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

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/** Abstract Base Spring Boot Test Case Class. */
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractSpringBootTest extends AbstractTest {
  /** ContentType header default value. */
  public static final String HAL_JSON = "application/hal+json";
  /** Root path. */
  public static final String ROOT_PATH = "/";
  /** Local Host Name. */
  public static final String HOST_NAME = "localhost";
  /** Test server port. */
  @LocalServerPort
  private int port;

  /**
   * Server port number.
   *
   * @return port number
   */
  public int getPort() {
    return this.port;
  }

  /**
   * Server host.
   *
   * @return host
   */
  public String getHostName() {
    return HOST_NAME;
  }

  /**
   * Builds the root server URL.
   *
   * @return root server URL
   */
  public String getRootUrl() {
    return "http://" + getHostName() + ":" + getPort() + ROOT_PATH;
  }
}
