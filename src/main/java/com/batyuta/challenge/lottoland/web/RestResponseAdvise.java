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

package com.batyuta.challenge.lottoland.web;

import com.batyuta.challenge.lottoland.annotation.ApplyResponseBinding;
import com.batyuta.challenge.lottoland.vo.RestResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

@ControllerAdvice(annotations = RestController.class)
public class RestResponseAdvise implements ResponseBodyAdvice<Object> {
    /**
     * Whether this component supports the given controller method return type
     * and the selected {@code HttpMessageConverter} type.
     *
     * @param returnType    the return type
     * @param converterType the selected converter type
     * @return {@code true} if {@link #beforeBodyWrite} should be invoked;
     * {@code false} otherwise
     */
    @Override
    public boolean supports(final MethodParameter returnType,
                            final Class<? extends HttpMessageConverter<?>>
                                    converterType) {
        Class<?> aClass = returnType.getContainingClass();
        return (aClass.isAnnotationPresent(RestController.class)
                || aClass.isAnnotationPresent(ControllerAdvice.class))
                && aClass.isAnnotationPresent(ApplyResponseBinding.class);
    }

    /**
     * Invoked after an {@code HttpMessageConverter} is selected and just before
     * its write method is invoked.
     *
     * @param body                  the body to be written
     * @param returnType            the return type of the controller method
     * @param selectedContentType   the content type selected through content
     *                              negotiation
     * @param selectedConverterType the converter type selected to write
     *                              to the response
     * @param request               the current request
     * @param response              the current response
     * @return the body that was passed in or a modified
     * (possibly new) instance
     */
    @Override
    public Object beforeBodyWrite(
            final Object body,
            final MethodParameter returnType,
            final MediaType selectedContentType,
            final Class selectedConverterType,
            final ServerHttpRequest request,
            final ServerHttpResponse response) {
        Object o = body;
        if (!(o instanceof RestResponse)) {
            Method method = returnType.getMethod();
            if (method.isAnnotationPresent(ResponseStatus.class)) {
                ResponseStatus responseStatus =
                        method.getAnnotation(ResponseStatus.class);
                o = RestResponse.status(o, responseStatus.value());
            } else {
                o = RestResponse.ok(o);
            }
        }
        return o;
    }
}
