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

package com.batyuta.challenge.lottoland.config;

import static org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME;

import com.batyuta.challenge.lottoland.annotation.LogEntry;
import com.batyuta.challenge.lottoland.web.FromMdcFilter;
import javax.servlet.Filter;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class FilterConfig {

  /**
   * Registers the secure filter after MDC.
   *
   * @param securityFilter security filter
   * @return filter registration bean
   */
  @LogEntry(Level.INFO)
  @Bean
  public FilterRegistrationBean<Filter> securityFilterChain(
      final @Qualifier(DEFAULT_FILTER_NAME) Filter securityFilter) {
    FilterRegistrationBean<Filter> registration =
        new FilterRegistrationBean<>(securityFilter);
    registration.setOrder(Integer.MAX_VALUE - 1);
    registration.setName(DEFAULT_FILTER_NAME);
    return registration;
  }

  /**
   * Getter of {@link FromMdcFilter}.
   *
   * @return filter
   */
  @LogEntry(Level.INFO)
  @Bean
  public FilterRegistrationBean<FromMdcFilter> mdcFilterFilterChain() {
    FilterRegistrationBean<FromMdcFilter> registrationBean =
        new FilterRegistrationBean<>();
    FromMdcFilter userFilter = new FromMdcFilter();
    registrationBean.setFilter(userFilter);
    registrationBean.setOrder(Integer.MAX_VALUE);
    return registrationBean;
  }
}
