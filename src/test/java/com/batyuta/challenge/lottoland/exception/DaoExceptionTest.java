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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

/**
 * DAO exception test.
 */
@DisplayName("DAO exception test")
class DaoExceptionTest {
    /**
     * Test Default Constructor.
     */
    @Test
    @DisplayName("TC#01: Test Default Constructor")
    void testDefaultConstructor() {
        String theFirstArgument = "the first argument";
        String msg = "error.unknown";
        DaoException daoException = assertThrows(DaoException.class,
                () -> {
                    throw new DaoException(msg, theFirstArgument);
                }
        );
        assertNotNull("Exception is null", daoException);
        assertEquals(
                "Exception has different message",
                msg,
                daoException.getMessage()
        );
        Object[] args = daoException.getArgs();
        assertNotNull("Exception arguments is empty", args);
        assertFalse(
                "Exception has another arguments",
                args.length != 1
        );
        assertEquals(
                "Exception has another first argument",
                theFirstArgument,
                daoException.getArgs()[0]
        );
    }

    /**
     * Test Default Constructor.
     */
    @Test
    @DisplayName("TC#02: Test Default Constructor with parent")
    void testDefaultConstructorWithParent() {
        String theFirstArgument = "the first argument";
        String msg = "error.unknown";
        DaoException daoException = assertThrows(DaoException.class,
                () -> {
                    throw new DaoException(
                            msg,
                            new RuntimeException(),
                            theFirstArgument
                    );
                }
        );
        assertNotNull("Exception is null", daoException);
        assertNotNull(
                "Has empty message",
                daoException.getMessage()
        );
        assertTrue(
                "Exception has different message",
                Objects.requireNonNull(daoException.getMessage()).contains(msg)
        );
        Object[] args = daoException.getArgs();
        assertNotNull("Exception arguments is empty", args);
        assertFalse(
                "Exception has another arguments",
                args.length != 1
        );
        assertNotNull(
                "Has empty first message argument",
                daoException.getArgs()[0]
        );
        assertTrue(
                "Exception has another first argument",
                daoException.getArgs()[0].getClass() == String.class
        );
        assertTrue(
                "Exception has another first argument value",
                ((String) daoException.getArgs()[0]).contains(theFirstArgument)
        );
    }
}
