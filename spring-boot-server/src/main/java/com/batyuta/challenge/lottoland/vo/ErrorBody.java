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

package com.batyuta.challenge.lottoland.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/** Error response body. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data // Lombok
public final class ErrorBody implements java.io.Serializable {

  /** Error message. */
  private String message;

  /** Exception class name. */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String debug;

  /** Exception cause. */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ErrorBody trace;

  /** Default constructor. */
  public ErrorBody() {
    this("error.unknown");
  }

  /**
   * Default constructor.
   *
   * @param ex exception
   */
  public ErrorBody(final Throwable ex) {
    this(ex.getMessage(), ex);
  }

  /**
   * Default constructor.
   *
   * @param error error message
   */
  public ErrorBody(final String error) {
    this(error, null);
  }

  /**
   * Default constructor.
   *
   * @param error error message
   * @param ex exception
   */
  public ErrorBody(final String error, final Throwable ex) {
    this.message = error;
    this.debug = ex != null ? ex.getClass().getName() : null;
    this.trace = buildTrace(ex);
  }

  /**
   * Build causes.
   *
   * @param ex exception
   * @return error body for exception
   */
  private ErrorBody buildTrace(final Throwable ex) {
    if (ex != null && ex.getCause() != null) {
      return new ErrorBody(ex.getMessage(), ex.getCause());
    }
    return null;
  }
}
