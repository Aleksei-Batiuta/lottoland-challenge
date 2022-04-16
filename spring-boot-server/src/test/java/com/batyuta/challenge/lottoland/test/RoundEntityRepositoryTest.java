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

import static com.batyuta.challenge.lottoland.enums.SignEnum.PAPER;
import static com.batyuta.challenge.lottoland.enums.SignEnum.ROCK;
import static com.batyuta.challenge.lottoland.enums.SignEnum.SCISSORS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

import com.batyuta.challenge.lottoland.enums.SignEnum;
import com.batyuta.challenge.lottoland.enums.StatusEnum;
import com.batyuta.challenge.lottoland.exception.DataException;
import com.batyuta.challenge.lottoland.model.RoundEntity;
import com.batyuta.challenge.lottoland.repository.RoundEntityRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.Isolated;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.BeforeTestClass;

/** Round repository test class. */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@DisplayName("Test Cases of Round Entity Repository")
public final class RoundEntityRepositoryTest extends BaseTest {

  /** The first test user ID. */
  public static final long USER_1_ID = 1L;

  /** The second test user ID. */
  public static final long USER_2_ID = 2L;

  /** The third test user ID. */
  public static final long USER_3_ID = 3L;

  /** Hide constructor. */
  private RoundEntityRepositoryTest() {}

  /** The functional part of test cases. */
  @Nested
  @Isolated
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  @TestMethodOrder(MethodOrderer.DisplayName.class)
  @SpringBootTest
  @DisplayName("Test Cases of Round Repository Functional Test")
  @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
  public class FunctionalTest {

    /** How many rounds will be created for each user. */
    public static final int ROUNDS_FOR_EACH_USER = 5;

    /** This is constant and only two users played in test cases. */
    public static final int USER_COUNT = 2;

    /** This index is used for generating non-static test data. */
    private final AtomicInteger statusIndex = new AtomicInteger(-1);

    /** Store of status counts. */
    private final ConcurrentHashMap<StatusEnum, AtomicInteger> roundsByStatus =
        new ConcurrentHashMap<>();

    /** Repository instance. */
    @Autowired
    private RoundEntityRepository repository;

    /**
     * The simple data test.
     *
     * @return data test
     */
    public Stream<? extends Arguments> testData() {
      return Stream.of(Arguments.of((RoundEntity) null),
          Arguments.of(new RoundEntity()));
    }

    /**
     * Collection of result status.
     *
     * @return status test data
     */
    public Stream<? extends Arguments> testStatusEnums() {
      return Stream.of(Arguments.of((StatusEnum) null),
          Arguments.of(StatusEnum.WIN), Arguments.of(StatusEnum.DRAW),
          Arguments.of(StatusEnum.LOSS));
    }

    /**
     * Creates a new instance of {@link RoundEntity} to store.
     *
     * @param userId User ID
     * @return new instance
     */
    private RoundEntity newRound(final Long userId) {
      final StatusEnum[] statusEnums = StatusEnum.values();
      final int statusEnumSize = statusEnums.length;

      final StatusEnum statusEnum =
          statusEnums[statusIndex.incrementAndGet() % statusEnumSize];

      roundsByStatus
          .computeIfAbsent(statusEnum, statusEnum1 -> new AtomicInteger(0))
          .incrementAndGet();

      final SignEnum player1;

      switch (statusEnum) {
        case WIN:
          player1 = PAPER;
          break;
        case LOSS:
          player1 = SCISSORS;
          break;
        case DRAW:
          player1 = ROCK;
          break;
        default:
          fail(() -> "Unexpected status: " + statusEnum);
          // this is extra code for IDE and checkstyle
          throw new IllegalArgumentException("Incorrect test data");
      }
      return new RoundEntity(userId, player1, ROCK, statusEnum);
    }

    /**
     * Test data of {@link RoundEntity} for the first user.
     *
     * @return test data
     */
    public Collection<RoundEntity> testUser1Data() {
      return testUserData(USER_1_ID);
    }

    /**
     * Test data of {@link RoundEntity} for the second user.
     *
     * @return test data
     */
    public Collection<RoundEntity> testUser2Data() {
      return testUserData(USER_2_ID);
    }

    /**
     * Test data of {@link RoundEntity} for the user.
     *
     * @param userId user ID
     * @return test data
     */
    private List<RoundEntity> testUserData(final Long userId) {
      List<RoundEntity> data = new ArrayList<>();
      for (int i = 0; i < ROUNDS_FOR_EACH_USER; i++) {
        data.add(newRound(userId));
      }
      return data;
    }

    /**
     * Creates round for the first user.
     *
     * @param round round
     */
    @DisplayName("TC#01: Creates rounds of first user")
    @ParameterizedTest(name = "createUser1[{index}]: first={0}")
    @MethodSource("testUser1Data")
    void create1User(final RoundEntity round) {
      RoundEntity actual = repository.save(round);
      assertNotNull(actual, "Unexpected result");
    }

    /**
     * Creates round for the second user.
     *
     * @param round round
     */
    @DisplayName("TC#02: Creates rounds of seconds user")
    @ParameterizedTest(name = "createUser2[{index}]: first={0}")
    @MethodSource("testUser2Data")
    void createUser2(final RoundEntity round) {
      RoundEntity actual = repository.save(round);
      assertNotNull(actual, "Unexpected result");
    }

    /**
     * Check to impossibility of update round.
     *
     * @param round round
     */
    @DisplayName("TC#03: Checks to updating is impossible")
    @ParameterizedTest(name = "update[{index}]: first={0}")
    @MethodSource("testData")
    void update(final RoundEntity round) {
      DataException dataException =
          assertThrows(DataException.class, () -> repository.update(round));
      assertNotNull(dataException.getMessage(),
          () -> "Empty exception message: round = " + round);
      assertTrue(
          dataException.getMessage().contains("error.unsupported.operation"),
          () -> "Unexpected exception message: round = " + round);
    }

    /** Check to impossibility of get one round by ID. */
    @Test
    @DisplayName("TC#04: Checks to get one round is impossible")
    void get() {
      long roundId = 1L;
      assertNotNull(repository.findById(roundId),
          () -> "Round was not found: roundId = " + roundId);
    }

    @Test
    @DisplayName("TC#05: Checks to data of first user have been created")
    void getRoundsByUser1IdBeforeDelete() {
      Page<RoundEntity> roundsByUserId =
          repository.findAllByUserId(USER_1_ID, null);
      assertNotNull(roundsByUserId, "Unexpected result");
      assertFalse(roundsByUserId.isEmpty(), "Empty data");

      assertEquals(ROUNDS_FOR_EACH_USER, roundsByUserId.getTotalElements(),
          "Count of rounds is not the same before delete one");
    }

    @Test
    @DisplayName("TC#06: Checks to all data is visible")
    void getAllBeforeDelete() {
      Collection<RoundEntity> rounds = toList(repository.findAll());
      assertNotNull(rounds, "Unexpected result");
      assertFalse(rounds.isEmpty(), "Empty data");
      assertEquals(ROUNDS_FOR_EACH_USER * USER_COUNT, rounds.size(),
          "Count of rounds is not the same before delete one");
    }

    /**
     * Check to impossibility of delete round.
     *
     * @param round round
     */
    @DisplayName("TC#07: Checks to delete one round is impossible")
    @ParameterizedTest(name = "delete[{index}]: first={0}")
    @MethodSource("testData")
    void delete(final RoundEntity round) {
      if (round == null) {
        deleteNull(round);
      } else {
        Long roundId = round.getId();
        repository.delete(round);
        DataException dataException = assertThrows(DataException.class,
            () -> repository.findById(roundId));
        assertNotNull(dataException, "Exception is null");
        assertNotNull(dataException.getMessage(), "Exception text is null");
        assertTrue(
            dataException.getMessage().contains("error.data.entity.not.found"),
            () -> "Unexpected exception message: text = "
                + dataException.getMessage());
      }
    }

    private void deleteNull(final RoundEntity round) {
      DataException dataException =
          assertThrows(DataException.class, () -> repository.delete(round));
      assertNotNull(dataException.getMessage(),
          () -> "Empty exception message: round = " + round);
      assertTrue(dataException.getMessage().contains("error.data.entity.null"),
          () -> "Unexpected exception message: round = " + round);
    }

    @Test
    @DisplayName("TC#08: Test delete of first user data")
    void deleteAllByUserId() {
      repository.deleteAllByUserId(USER_1_ID);
    }

    @Test
    @DisplayName("TC#09: Checks data of first user after delete")
    void getRoundsByUser1IdAfterDelete() {
      Page<RoundEntity> roundsByUserId =
          repository.findAllByUserId(USER_1_ID, null);
      assertNotNull(roundsByUserId, "Unexpected result");
      assertTrue(roundsByUserId.isEmpty(), "Empty data");
    }

    @Test
    @DisplayName("TC#10: Checks data of second user after delete data of first")
    void getRoundsByUser2IdAfterDelete() {
      Page<RoundEntity> roundsAll = repository.findAllByUserId(USER_2_ID, null);
      assertNotNull(roundsAll, "Unexpected result");
      assertFalse(roundsAll.isEmpty(), "Empty data");
      assertEquals(ROUNDS_FOR_EACH_USER, roundsAll.getTotalElements(),
          "Count of rounds is not the same before delete one");
    }

    @Test
    @DisplayName("TC#11: Checks to all data is still exists")
    void getAllAfterDelete() {
      Collection<RoundEntity> roundsAll = toList(repository.findAll());
      assertNotNull(roundsAll, "Unexpected result");
      assertFalse(roundsAll.isEmpty(), "Empty data");
      assertEquals(ROUNDS_FOR_EACH_USER, roundsAll.size(),
          "Count of rounds is not the same before delete one");
    }

    /**
     * Check to possibility of get rounds by status.
     *
     * @param statusEnum round's status
     */
    @DisplayName("TC#12: Checks all data to correct amount by status")
    @ParameterizedTest(name = "getRoundsByStatus[{index}]: first={0}")
    @MethodSource("testStatusEnums")
    void getRoundsByStatus(final StatusEnum statusEnum) {
      Page<RoundEntity> allRoundsByStatus =
          repository.getAllRoundsByStatus(statusEnum);
      assertNotNull(allRoundsByStatus,
          () -> "Unexpected result: status = " + statusEnum);
      int expected;
      if (statusEnum == null) {
        expected = this.roundsByStatus.values().stream().map(AtomicInteger::get)
            .reduce(0, Integer::sum);
      } else {
        expected = this.roundsByStatus.get(statusEnum).get();
      }
      assertFalse(allRoundsByStatus.isEmpty(),
          () -> "Empty data: status = " + statusEnum);
      assertEquals(expected, allRoundsByStatus.getTotalElements(),
          () -> "Unexpected count for " + statusEnum);
    }

    @Test
    @DisplayName("TC#13: Checks data to correct total count")
    void getTotalRounds() {
      assertEquals(ROUNDS_FOR_EACH_USER * USER_COUNT, repository.count(),
          "Unexpected total refunds");
    }

    /**
     * Check to possibility of get count of rounds by status.
     *
     * @param statusEnum round's status
     */
    @ParameterizedTest(name = "getTotalRoundsByStatus[{index}]: first={0}")
    @MethodSource("testStatusEnums")
    @DisplayName("TC#14: Checks data to correct total count of status")
    void getTotalRoundsByStatus(final StatusEnum statusEnum) {
      long totalRoundsByStatus = repository.getTotalRoundsByStatus(statusEnum);
      long expected;
      if (statusEnum == null) {
        expected = roundsByStatus.values().stream().map(AtomicInteger::get)
            .reduce(0, Integer::sum);
      } else {
        expected = roundsByStatus.get(statusEnum).get();
      }
      assertEquals(expected, totalRoundsByStatus,
          () -> "Unexpected count for " + statusEnum);
    }
  }

  /** Test cases of thread safe. */
  @Nested
  @SpringBootTest
  @DisplayName("Test Cases of Round Repository Concurrency Test")
  public class ConcurrencyTest {

    /**
     * Counts of retry to broke system. todo: maybe it should be reviewed to use
     * one test method
     */
    public static final int REPEAT_COUNT = 1;

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
}
