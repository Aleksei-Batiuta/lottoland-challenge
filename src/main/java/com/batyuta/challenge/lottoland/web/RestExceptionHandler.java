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

import com.batyuta.challenge.lottoland.exception.BaseException;
import com.batyuta.challenge.lottoland.vo.ErrorBody;
import com.batyuta.challenge.lottoland.vo.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler
        extends ResponseEntityExceptionHandler {
    /**
     * Logger.
     */
    private final Logger logger =
            LoggerFactory.getLogger(RestExceptionHandler.class);
    /**
     * Message service.
     */
    private final MessageSource messageSource;

    /**
     * Default constructor.
     *
     * @param service Message service.
     */
    public RestExceptionHandler(final MessageSource service) {
        this.messageSource = service;
    }

    /**
     * Handle {@link BaseException}.
     *
     * @param ex      exception
     * @param request request
     * @return REST response
     */
    @ExceptionHandler({BaseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResponse<ErrorBody> handleBaseException(
            final BaseException ex, final WebRequest request) {
        String message = messageSource.getMessage(
                ex.getKey(),
                ex.getArgs(),
                request.getLocale()
        );
        setMessageAttribute(request, message, ex);
        return RestResponse.badRequest(message, ex);
    }

    /**
     * Handle {@link AccessDeniedException}.
     *
     * @param ex      exception
     * @param request request
     * @return REST response
     */
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public RestResponse<ErrorBody> handleAccessDeniedException(
            final Exception ex, final WebRequest request) {
        setMessageAttribute(request, ex.getMessage(), ex);
        return RestResponse.forbidden(ex);
    }

    /**
     * Handle {@link Throwable}.
     *
     * @param ex      exception
     * @param request request
     * @return REST response
     */
    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RestResponse<ErrorBody> handleThrowable(
            final Exception ex, final WebRequest request) {
        setMessageAttribute(request, ex.getMessage(), ex);
        return RestResponse.internalServerError(ex);
    }

    /**
     * Setts the 'message' HTTP attribute.
     *
     * @param request request
     * @param message message text
     * @param ex      exception
     */
    private void setMessageAttribute(
            final WebRequest request,
            final String message,
            final Throwable ex) {
        logger.error(message, ex);
        request.setAttribute(
                "message",
                message,
                RequestAttributes.SCOPE_REQUEST
        );
    }
}
