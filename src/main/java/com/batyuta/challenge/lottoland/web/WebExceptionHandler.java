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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * WEB Exception Handler class.
 */
@ControllerAdvice
public class WebExceptionHandler {
    /**
     * Logger.
     */
    private final Logger logger =
            LoggerFactory.getLogger(WebExceptionHandler.class);

    /**
     * Message Source Bean.
     */
    private final MessageSource messageSource;

    /**
     * Default constructor.
     *
     * @param messageSource message servcice
     */
    @Autowired
    public WebExceptionHandler(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Catches the {@link BaseException}.
     *
     * @param exception exception instance
     * @param locale    current session locale
     * @return view
     */
    @ExceptionHandler(BaseException.class)
    public ModelAndView handleBaseException(final BaseException exception,
                                            final Locale locale) {
        if (exception != null && exception.getMessage() != null) {
            String errorMessage = messageSource.getMessage(
                    exception.getMessage(),
                    exception.getArgs(),
                    null,
                    locale
            );
            logger.error(errorMessage, exception);
            List<String> messages = Collections.singletonList(errorMessage);
            HttpStatus httpStatus = HttpStatus.CONFLICT;
            return getErrorModelAndView(messages, httpStatus);
        } else {
            return handleException(new Exception(), locale);
        }
    }

    /**
     * Catches the {@link Exception}.
     *
     * @param exception exception instance
     * @param locale    current session locale
     * @return view
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(final Exception exception,
                                        final Locale locale) {
        String errorMessage = messageSource.getMessage(
                "error.unknown",
                new Object[]{exception.getMessage()},
                null,
                locale
        );
        logger.error(exception.getMessage(), exception);
        return getErrorModelAndView(
                Collections.singletonList(errorMessage),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Builds the error view.
     *
     * @param messages   errors
     * @param httpStatus returned HTTP status
     * @return view
     */
    private ModelAndView getErrorModelAndView(final List<String> messages,
                                              final HttpStatus httpStatus) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", "title.error");
        map.put("body", "exception");
        map.put("messages", messages);
        return new ModelAndView("main", map, httpStatus);
    }
}
