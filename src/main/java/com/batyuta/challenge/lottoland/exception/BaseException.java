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

package com.batyuta.challenge.lottoland.exception;

import lombok.Getter;
import org.springframework.core.NestedRuntimeException;

/**
 * Base Application Exception class. It should be used
 * for business logic errors.
 */
@Getter
public abstract class BaseException extends NestedRuntimeException {
    /**
     * Argument which can be used in format.
     */
    private final Object[] args;

    /**
     * Default Constructor.
     *
     * @param msg       format message
     * @param arguments arguments
     */
    public BaseException(final String msg,
                         final Object... arguments) {
        this(msg, null, arguments);
    }

    /**
     * Default Constructor.
     *
     * @param msg       format message
     * @param cause     exception
     * @param arguments arguments
     */
    public BaseException(final String msg,
                         final Throwable cause,
                         final Object... arguments) {
        super(msg, cause);
        this.args =
                arguments == null || arguments.length == 0
                        ? new Object[]{""}
                        : arguments;
    }
}
