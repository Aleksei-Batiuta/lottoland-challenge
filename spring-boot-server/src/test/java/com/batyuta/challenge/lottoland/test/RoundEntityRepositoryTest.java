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

import static com.batyuta.challenge.lottoland.enums.SignEnum.ROCK;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

import com.batyuta.challenge.lottoland.AbstractSpringBootTest;
import com.batyuta.challenge.lottoland.enums.StatusEnum;
import com.batyuta.challenge.lottoland.model.RoundEntity;
import com.batyuta.challenge.lottoland.repository.RoundEntityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.event.annotation.BeforeTestClass;

/** Test cases of thread safe. */
@DisplayName("Test Cases of Round Repository Concurrency Test")
public class RoundEntityRepositoryTest extends AbstractSpringBootTest {

  /**
   * Counts of retry to broke system. todo: maybe it should be reviewed to use
   * one test method
   */
  public static final int REPEAT_COUNT = 1;
  /** The first test user ID. */
  public static final long USER_1_ID = 1L;
  /** The second test user ID. */
  public static final long USER_2_ID = 2L;
  /** The third test user ID. */
  public static final long USER_3_ID = 3L;

  /** Round repository. */
  @Autowired
  private RoundEntityRepository repository;

  /** Checks to empty rounds for the third user. */
  @BeforeTestClass
  public void before() {
    Page<RoundEntity> rounds = repository.findAllByUserId(USER_3_ID, null);
    assertNotNull(rounds, "Unexpected result");
    assertTrue(rounds.isEmpty(), "Empty data");
  }

  @Test
  @Execution(CONCURRENT)
  void create() {
    for (int i = 0; i < REPEAT_COUNT; i++) {
      RoundEntity round =
          new RoundEntity(USER_3_ID, ROCK, ROCK, StatusEnum.DRAW);
      RoundEntity actual = repository.save(round);
      assertNotNull(actual, "Unexpected result");
    }
  }

  @Test
  @Execution(CONCURRENT)
  void getAll() {
    for (int i = 0; i < REPEAT_COUNT; i++) {
      Page<RoundEntity> rounds = repository.findAllByUserId(USER_3_ID, null);
      assertNotNull(rounds, "Unexpected result");
    }
  }

  @Test
  @Execution(CONCURRENT)
  void delete() {
    for (int i = 0; i < REPEAT_COUNT; i++) {
      repository.deleteAllByUserId(USER_3_ID);
    }
  }
}
