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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * NPE fix.
 *
 * @author Aleksei Batiuta
 */
@Component
public class WebMvcEndpointHandler {

  /**
   * Handles events on WEB MVC.
   *
   * @param webEndpointsSupplier web endpoint
   * @param servletEndpointsSupplier servlet endpoint
   * @param controllerEndpointsSupplier controller endpoint
   * @param endpointMediaTypes media data type
   * @param corsProperties CORS configuration
   * @param webEndpointProperties web configuration
   * @param environment environment
   * @return handler
   */
  @Bean
  public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(
      final WebEndpointsSupplier webEndpointsSupplier,
      final ServletEndpointsSupplier servletEndpointsSupplier,
      final ControllerEndpointsSupplier controllerEndpointsSupplier,
      final EndpointMediaTypes endpointMediaTypes,
      final CorsEndpointProperties corsProperties,
      final WebEndpointProperties webEndpointProperties,
      final Environment environment) {
    List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
    Collection<ExposableWebEndpoint> webEndpoints =
        webEndpointsSupplier.getEndpoints();
    allEndpoints.addAll(webEndpoints);
    allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
    allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
    String basePath = webEndpointProperties.getBasePath();
    EndpointMapping endpointMapping = new EndpointMapping(basePath);
    boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(
        webEndpointProperties, environment, basePath);
    return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints,
        endpointMediaTypes, corsProperties.toCorsConfiguration(),
        new EndpointLinksResolver(allEndpoints, basePath),
        shouldRegisterLinksMapping, null);
  }

  private boolean shouldRegisterLinksMapping(
      final WebEndpointProperties webEndpointProperties,
      final Environment environment, final String basePath) {
    return webEndpointProperties.getDiscovery().isEnabled()
        && (StringUtils.hasText(basePath) || ManagementPortType.get(environment)
            .equals(ManagementPortType.DIFFERENT));
  }
}
