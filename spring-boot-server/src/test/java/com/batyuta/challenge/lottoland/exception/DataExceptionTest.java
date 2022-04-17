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

package com.batyuta.challenge.lottoland.exception;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import com.batyuta.challenge.lottoland.AbstractTest;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Data exception test. */
@DisplayName("Data exception test")
public class DataExceptionTest extends AbstractTest {

  /** Test Default Constructor. */
  @Test
  @DisplayName("TC#01: Test Default Constructor")
  void testDefaultConstructor() {
    String theFirstArgument = "the first argument";
    String msg = "error.unknown";
    DataException dataException = assertThrows(DataException.class, () -> {
      throw new DataException(msg, theFirstArgument);
    });
    assertNotNull("Exception is null", dataException);
    assertEquals("Exception has different message", msg,
        dataException.getMessage());
    assertEquals("Exception has different message and key", msg,
        dataException.getKey());
    Object[] args = dataException.getArgs();
    assertNotNull("Exception arguments is empty", args);
    assertFalse("Exception has another arguments", args.length != 1);
    assertEquals("Exception has another first argument", theFirstArgument,
        dataException.getArgs()[0]);
  }

  /** Test Default Constructor. */
  @Test
  @DisplayName("TC#02: Test Default Constructor with parent")
  void testDefaultConstructorWithParent() {
    String theFirstArgument = "the first argument";
    String msg = "error.unknown";
    DataException dataException = assertThrows(DataException.class, () -> {
      throw new DataException(msg, new RuntimeException(), theFirstArgument);
    });
    assertNotNull("Exception is null", dataException);
    assertNotNull("Has empty message", dataException.getMessage());
    assertTrue("Exception has different message",
        Objects.requireNonNull(dataException.getMessage()).contains(msg));
    Object[] args = dataException.getArgs();
    assertNotNull("Exception arguments is empty", args);
    assertFalse("Exception has another arguments", args.length != 1);
    assertNotNull("Has empty first message argument",
        dataException.getArgs()[0]);
    assertTrue("Exception has another first argument",
        dataException.getArgs()[0].getClass() == String.class);
    assertTrue("Exception has another first argument value",
        ((String) dataException.getArgs()[0]).contains(theFirstArgument));
  }
}
