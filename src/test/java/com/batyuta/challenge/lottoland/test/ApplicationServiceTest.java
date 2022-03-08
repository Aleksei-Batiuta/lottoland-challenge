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

import com.batyuta.challenge.lottoland.enums.SignEnum;
import com.batyuta.challenge.lottoland.model.RoundEntity;
import com.batyuta.challenge.lottoland.service.ApplicationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.parallel.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

/**
 * Application service tes cases.
 */
@Order(2)
@SpringBootTest()
class ApplicationServiceTest {
    /**
     * Test Repeat count.
     */
    public static final int REPEAT_COUNT = 5;

    /**
     * Service to test.
     */
    @Autowired
    private ApplicationService service;

    /**
     * Tests the generated rounds to at least one has a ROCK.
     */
    @DisplayName("At least one of player should have a ROCK")
    @RepeatedTest(REPEAT_COUNT)
    @Execution(CONCURRENT)
    void newRoundByUserId() {
        RoundEntity roundEntity = service.newRoundByUserId(0);
        assertNotNull(roundEntity, "Round was not generated");
        assertEquals(SignEnum.ROCK,
                roundEntity.getPlayer1() == SignEnum.ROCK
                        ? roundEntity.getPlayer1()
                        : roundEntity.getPlayer2(),
                () -> "Unexpected result: " + roundEntity
        );
    }
}
