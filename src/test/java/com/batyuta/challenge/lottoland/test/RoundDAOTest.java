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

import com.batyuta.challenge.lottoland.dao.RoundDAO;
import com.batyuta.challenge.lottoland.enums.SignEnum;
import com.batyuta.challenge.lottoland.enums.StatusEnum;
import com.batyuta.challenge.lottoland.exception.DaoException;
import com.batyuta.challenge.lottoland.model.RoundEntity;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

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

/**
 * Round DAO test class.
 */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public final class RoundDAOTest {
    /**
     * The first test user ID.
     */
    public static final int USER_1_ID = 1;
    /**
     * The second test user ID.
     */
    public static final int USER_2_ID = 2;
    /**
     * The third test user ID.
     */
    public static final int USER_3_ID = 3;

    /**
     * Hide constructor.
     */
    private RoundDAOTest() {
        //not called
    }

    /**
     * The functional part of test cases.
     */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    @Order(1)
    @SpringBootTest
    @DisplayName("Test Cases of Round DAO Functional Test")
    public class FunctionalTest {
        /**
         * How many rounds will be created for each user.
         */
        public static final int ROUNDS_FOR_EACH_USER = 20;
        /**
         * This is constant and only two users played in test cases.
         */
        public static final int USER_COUNT = 2;
        /**
         * This index is used for generating non-static test data.
         */
        private final AtomicInteger statusIndex =
                new AtomicInteger(-1);
        /**
         * Store of status counts.
         */
        private final ConcurrentHashMap<StatusEnum, AtomicInteger>
                roundCountByStatus = new ConcurrentHashMap<>();

        /**
         * DAO instance.
         */
        @Autowired
        private RoundDAO roundDao;

        /**
         * The simple data test.
         *
         * @return data test
         */
        public Stream<? extends Arguments> testData() {
            return Stream.of(
                    Arguments.of((RoundEntity) null),
                    Arguments.of(new RoundEntity())
            );
        }

        /**
         * Collection of result status.
         *
         * @return status test data
         */
        public Stream<? extends Arguments> testStatusEnums() {
            return Stream.of(
                    Arguments.of((StatusEnum) null),
                    Arguments.of(StatusEnum.WIN),
                    Arguments.of(StatusEnum.DRAW),
                    Arguments.of(StatusEnum.LOSS)
            );
        }

        /**
         * Creates a new instance of {@link RoundEntity}
         * to store.
         *
         * @param userId User ID
         * @return new instance
         */
        private RoundEntity newRound(int userId) {
            final StatusEnum[] statusEnums = StatusEnum.values();
            final int statusEnumSize = statusEnums.length;

            final StatusEnum statusEnum =
                    statusEnums[statusIndex.incrementAndGet() % statusEnumSize];

            roundCountByStatus.computeIfAbsent(
                    statusEnum,
                    statusEnum1 -> new AtomicInteger(0)
            ).incrementAndGet();

            final SignEnum player1;

            switch (statusEnum) {
                case WIN: {
                    player1 = PAPER;
                    break;
                }
                case LOSS: {
                    player1 = SCISSORS;
                    break;
                }
                case DRAW: {
                    player1 = ROCK;
                    break;
                }
                default: {
                    fail(() -> "Unexpected status: " + statusEnum);
                    // this is extra code for IDE and checkstyle
                    throw new IllegalArgumentException("Incorrect test data");
                }
            }
            return new RoundEntity(
                    userId,
                    player1,
                    ROCK,
                    statusEnum
            );
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
        private List<RoundEntity> testUserData(int userId) {
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
        @ParameterizedTest(
                name = "createUser1[{index}]: first={0}"
        )

        @MethodSource("testUser1Data")
        void create1User(RoundEntity round) {
            RoundEntity actual = roundDao.create(round);
            assertNotNull(actual, "Unexpected result");
        }

        /**
         * Creates round for the second user.
         *
         * @param round round
         */
        @DisplayName("TC#02: Creates rounds of seconds user")
        @ParameterizedTest(
                name = "createUser2[{index}]: first={0}"
        )
        @MethodSource("testUser2Data")
        void createUser2(RoundEntity round) {
            RoundEntity actual = roundDao.create(round);
            assertNotNull(actual, "Unexpected result");
        }

        /**
         * Check to impossibility of update round.
         *
         * @param round round
         */
        @DisplayName("TC#03: Checks to updating is impossible")
        @ParameterizedTest(
                name = "update[{index}]: first={0}"
        )
        @MethodSource("testData")
        void update(RoundEntity round) {
            DaoException daoException = assertThrows(
                    DaoException.class,
                    () -> roundDao.update(round)
            );
            assertNotNull(
                    daoException.getMessage(),
                    () -> "Empty exception message: round = " + round
            );
            assertTrue(
                    daoException.getMessage()
                            .contains("error.unsupported.operation"),
                    () -> "Unexpected exception message: round = " + round);
        }

        /**
         * Check to impossibility of get one round by ID.
         */
        @Test
        @DisplayName("TC#04: Checks to get one round is impossible")
        void get() {
            int roundId = 1;
            DaoException daoException = assertThrows(
                    DaoException.class,
                    () -> roundDao.get(roundId)
            );
            assertNotNull(
                    daoException.getMessage(),
                    () -> "Empty exception message: roundId = " + roundId
            );
            assertTrue(
                    daoException.getMessage()
                            .contains("error.unsupported.operation"),
                    () -> "Unexpected exception message: roundId = " + roundId
            );
        }

        @Test
        @DisplayName("TC#05: Checks to data of first user have been created")
        void getRoundsByUser1IdBeforeDelete() {
            Collection<RoundEntity> roundsByUserId =
                    roundDao.getRoundsByUserId(USER_1_ID);
            assertNotNull(roundsByUserId, "Unexpected result");
            assertFalse(roundsByUserId.isEmpty(), "Empty data");

            assertEquals(
                    ROUNDS_FOR_EACH_USER,
                    roundsByUserId.size(),
                    "Count of rounds is not the same before delete one"
            );
        }

        @Test
        @DisplayName("TC#06: Checks to all data is visible")
        void getAllBeforeDelete() {
            Collection<RoundEntity> rounds = roundDao.getAll();
            assertNotNull(rounds, "Unexpected result");
            assertFalse(rounds.isEmpty(), "Empty data");
            assertEquals(
                    ROUNDS_FOR_EACH_USER * USER_COUNT,
                    rounds.size(),
                    "Count of rounds is not the same before delete one"
            );
        }

        /**
         * Check to impossibility of delete round.
         *
         * @param round round
         */
        @DisplayName("TC#07: Checks to delete one round is impossible")
        @ParameterizedTest(name = "delete[{index}]: first={0}")
        @MethodSource("testData")
        void delete(RoundEntity round) {
            DaoException daoException = assertThrows(
                    DaoException.class,
                    () -> roundDao.delete(round)
            );
            assertNotNull(
                    daoException.getMessage(),
                    () -> "Empty exception message: round = " + round
            );
            assertTrue(
                    daoException.getMessage()
                            .contains("error.unsupported.operation"),
                    () -> "Unexpected exception message: round = " + round
            );
        }

        @Test
        @DisplayName("TC#08: Test delete of first user data")
        void deleteAllByUserId() {
            roundDao.deleteAllByUserId(USER_1_ID);
        }

        @Test
        @DisplayName("TC#09: Checks data of first user after delete")
        void getRoundsByUser1IdAfterDelete() {
            Collection<RoundEntity> roundsByUserId =
                    roundDao.getRoundsByUserId(USER_1_ID);
            assertNotNull(roundsByUserId, "Unexpected result");
            assertTrue(roundsByUserId.isEmpty(), "Empty data");
        }

        @Test
        @DisplayName(
                "TC#10: Checks data of second user after delete data of first"
        )
        void getRoundsByUser2IdAfterDelete() {
            Collection<RoundEntity> roundsAll =
                    roundDao.getRoundsByUserId(USER_2_ID);
            assertNotNull(roundsAll, "Unexpected result");
            assertFalse(roundsAll.isEmpty(), "Empty data");
            assertEquals(
                    ROUNDS_FOR_EACH_USER,
                    roundsAll.size(),
                    "Count of rounds is not the same before delete one"
            );
        }

        @Test
        @DisplayName("TC#11: Checks to all data is still exists")
        void getAllAfterDelete() {
            Collection<RoundEntity> roundsAll = roundDao.getAll();
            assertNotNull(roundsAll, "Unexpected result");
            assertFalse(roundsAll.isEmpty(), "Empty data");
            assertEquals(
                    ROUNDS_FOR_EACH_USER * USER_COUNT,
                    roundsAll.size(),
                    "Count of rounds is not the same before delete one"
            );
        }

        /**
         * Check to possibility of get rounds by status.
         *
         * @param statusEnum round's status
         */
        @DisplayName("TC#12: Checks all data to correct amount by status")
        @ParameterizedTest(name = "getRoundsByStatus[{index}]: first={0}")
        @MethodSource("testStatusEnums")
        void getRoundsByStatus(StatusEnum statusEnum) {
            Collection<RoundEntity> roundsByStatus =
                    roundDao.getAllRoundsByStatus(statusEnum);
            assertNotNull(
                    roundsByStatus,
                    () -> "Unexpected result: status = " + statusEnum
            );
            int expected;
            if (statusEnum == null) {
                expected = roundCountByStatus.values().stream()
                        .map(AtomicInteger::get)
                        .reduce(0, Integer::sum);
            } else {
                expected = roundCountByStatus.get(statusEnum).get();
            }
            assertFalse(
                    roundsByStatus.isEmpty(),
                    () -> "Empty data: status = " + statusEnum
            );
            assertEquals(
                    expected,
                    roundsByStatus.size(),
                    () -> "Unexpected count for " + statusEnum
            );
        }

        @Test
        @DisplayName("TC#13: Checks data to correct total count")
        void getTotalRounds() {
            assertEquals(
                    ROUNDS_FOR_EACH_USER * USER_COUNT,
                    roundDao.getTotalRounds(),
                    "Unexpected total refunds"
            );

        }

        /**
         * Check to possibility of get count of rounds by status.
         *
         * @param statusEnum round's status
         */
        @ParameterizedTest(
                name = "getTotalRoundsByStatus[{index}]: first={0}"
        )
        @MethodSource("testStatusEnums")
        void getTotalRoundsByStatus(StatusEnum statusEnum) {
            int totalRoundsByStatus =
                    roundDao.getTotalRoundsByStatus(statusEnum);
            int expected;
            if (statusEnum == null) {
                expected = roundCountByStatus.values().stream()
                        .map(AtomicInteger::get)
                        .reduce(0, Integer::sum);
            } else {
                expected = roundCountByStatus.get(statusEnum).get();
            }
            assertEquals(
                    expected,
                    totalRoundsByStatus,
                    () -> "Unexpected count for " + statusEnum
            );
        }
    }

    /**
     * Test cases of thread safe.
     */
    @Nested
    @Order(2)
    @SpringBootTest
    @DisplayName("Test Cases of Round DAO Concurrency Test")
    public class ConcurrencyTest {
        /**
         * Counts of retry to broke system.
         */
        public static final int REPEAT_COUNT = 1_000;

        /**
         * Round DAO.
         */
        @Autowired
        private RoundDAO roundDao;

        /**
         * Checks to empty rounds for the third user.
         */
        @BeforeTestClass
        public void before() {
            Collection<RoundEntity> rounds =
                    roundDao.getRoundsByUserId(USER_3_ID);
            assertNotNull(rounds, "Unexpected result");
            assertTrue(rounds.isEmpty(), "Empty data");
        }

        @Test
        @Execution(CONCURRENT)
        void create() {
            for (int i = 0; i < REPEAT_COUNT; i++) {
                RoundEntity round = new RoundEntity(
                        USER_3_ID,
                        ROCK,
                        ROCK,
                        StatusEnum.DRAW
                );
                RoundEntity actual = roundDao.create(round);
                assertNotNull(actual, "Unexpected result");
            }
        }

        @Test
        @Execution(CONCURRENT)
        void getAll() {
            for (int i = 0; i < REPEAT_COUNT; i++) {
                Collection<RoundEntity> rounds =
                        roundDao.getRoundsByUserId(USER_3_ID);
                assertNotNull(rounds, "Unexpected result");
            }
        }

        @Test
        @Execution(CONCURRENT)
        void delete() {
            for (int i = 0; i < REPEAT_COUNT; i++) {
                roundDao.deleteAllByUserId(USER_3_ID);
            }
        }
    }
}
