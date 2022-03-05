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

import com.batyuta.challenge.lottoland.model.RoundEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.parallel.Execution;

import java.util.Random;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

/**
 * Test of modifying entity properties.
 */
class RoundEntityTest {
    /**
     * Test data.
     */
    private final RoundEntity roundEntity = new RoundEntity();
    /**
     * Random deleted status.
     */
    private final Random random = new Random();

    /**
     * Try to get the concurrent.
     */
    @RepeatedTest(2000)
    @DisplayName("TC#01: Update property concurrently")
    @Execution(CONCURRENT)
    void concurrentModification() {
        roundEntity.setDeleted(random.nextBoolean());
    }
}
