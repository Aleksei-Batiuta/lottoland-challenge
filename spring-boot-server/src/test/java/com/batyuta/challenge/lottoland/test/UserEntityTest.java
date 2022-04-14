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

package com.batyuta.challenge.lottoland.test;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import com.batyuta.challenge.lottoland.model.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Test of modifying entity properties. */
@DisplayName("User Entity Concurrency modification test")
class UserEntityTest {

  /** Test No-Arguments Constructor. */
  @Test
  @DisplayName("TC#01: Test No-Arguments Constructor")
  void testNoArgumentsConstructor() {
    UserEntity user = new UserEntity();
    assertNotNull("Entity was not created", user);
    assertTrue("Entity is not a new", user.isNew());
    assertNull("Entity has name", user.getName());
  }

  /** Test Default Constructor. */
  @Test
  @DisplayName("TC#01: Test Default Constructor")
  void testDefaultConstructor() {
    String userName = "User Name";
    UserEntity user = new UserEntity(1L, userName);
    assertNotNull("Entity was not created", user);
    assertFalse("Entity is a new", user.isNew());
    assertEquals("Entity has name", userName, user.getName());
  }
}
