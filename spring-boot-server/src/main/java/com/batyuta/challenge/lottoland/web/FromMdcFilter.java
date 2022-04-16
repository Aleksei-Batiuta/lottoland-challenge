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

import com.batyuta.challenge.lottoland.annotation.LogEntry;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.event.Level;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.GenericFilterBean;

public class FromMdcFilter extends GenericFilterBean {
  /** MDC parameter name. */
  public static final String MDC_PROPERTY_FROM = "_from";
  /** Class logger. */
  private static final Logger LOG =
      LoggerFactory.getLogger(FromMdcFilter.class);

  /**
   * Adds {@link FromMdcFilter#MDC_PROPERTY_FROM}.
   *
   * @param from value
   * @return set value
   */
  public static String setMdcFromProperty(String from) {
    if (from == null) {
      from = createNewFrom();
    }
    MDC.put(MDC_PROPERTY_FROM, "[" + from + "]");
    return from;
  }

  private static String createNewFrom() {
    return Integer.toString(UUID.randomUUID().hashCode(), Character.MAX_RADIX);
  }

  /**
   * Adds MDC {@link FromMdcFilter#MDC_PROPERTY_FROM} parameter for logging.
   *
   * @param request The request to process
   * @param response The response associated with the request
   * @param chain Provides access to the next filter in the chain for this
   *        filter to pass the request and response to for further processing
   * @throws IOException if an I/O error occurs during this filter's processing
   *         of the request
   * @throws ServletException if the processing fails for any other reason
   */
  @LogEntry(Level.INFO)
  @Override
  public void doFilter(final ServletRequest request,
      final ServletResponse response, final FilterChain chain)
      throws IOException, ServletException {
    try {
      HttpServletRequest req = (HttpServletRequest) request;
      String from = req.getHeader(HttpHeaders.FROM);
      setMdcFromProperty(from);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    }
    chain.doFilter(request, response);
  }
}
