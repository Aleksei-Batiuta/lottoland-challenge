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

package com.batyuta.challenge.lottoland.test;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import com.batyuta.challenge.lottoland.model.UserEntity;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/** Test of modifying entity properties. */
@DisplayName("User Entity Concurrency modification test")
class UserEntityTest {

  /**
   * Test data generator.
   *
   * @return test data
   */
  public static Stream<? extends Arguments> testData() {
    return Stream.of(Arguments.of(new UserEntity("name1"), null, 1),
        Arguments.of(new UserEntity("name1"), new UserEntity("name1"), 0),
        Arguments.of(new UserEntity("name1"), new UserEntity("name10"), -1),
        Arguments.of(new UserEntity("name1"), new UserEntity(null), 1));
  }

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
  @DisplayName("TC#02: Test Default Constructor")
  void testDefaultConstructor() {
    String userName = "User Name";
    UserEntity user = new UserEntity(1L, userName);
    assertNotNull("Entity was not created", user);
    assertFalse("Entity is a new", user.isNew());
    assertEquals("Entity has name", userName, user.getName());
  }

  /** Test Default Constructor. */
  @Test
  @DisplayName("TC#03: Test toString()")
  void testToString() {
    String userName = "User Name";
    UserEntity user = new UserEntity(userName);
    assertNotNull("Entity was not created", user);
    assertTrue("Entity is not a new", user.isNew());
    assertEquals("Entity has name", userName, user.getName());
    assertNotNull("Entity has nullable toString return", user.toString());
    assertTrue("Entity has no toString", user.toString().length() > 0);
  }

  /**
   * The test method of users comparing.
   *
   * @param user1 the first user
   * @param user2 the second user
   * @param expected expected result
   */
  // @DisplayName("TC#03: Test compareTo()")
  @ParameterizedTest(name = "{index}: user1={0}, user2={1}, expected={2}")
  @MethodSource("testData")
  public void compareToTest(final UserEntity user1, final UserEntity user2,
      final Object expected) {
    try {
      int result = user1.compareTo(user2);
      assertEquals(String.format(
          "Unexpected comparing result: {data: {user1: %s, user2: %s}", user1,
          user2), expected, result);
    } catch (Exception actual) {
      assertNotNull(String.format(
          "Expected result is null: {data: {user1: %s, user2: %s}", user1,
          user2), expected);
      assertEquals(
          String.format("Unexpected result! {data: {user1: %s, user2: %s}",
              user1, user2),
          expected.getClass(), actual.getClass());
    }
  }
}
