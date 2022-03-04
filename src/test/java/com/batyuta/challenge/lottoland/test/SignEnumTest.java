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

import com.batyuta.challenge.lottoland.enums.StatusEnum;
import com.batyuta.challenge.lottoland.enums.SignEnum;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.batyuta.challenge.lottoland.enums.StatusEnum.DRAW;
import static com.batyuta.challenge.lottoland.enums.StatusEnum.LOSS;
import static com.batyuta.challenge.lottoland.enums.StatusEnum.WIN;
import static com.batyuta.challenge.lottoland.enums.SignEnum.PAPER;
import static com.batyuta.challenge.lottoland.enums.SignEnum.ROCK;
import static com.batyuta.challenge.lottoland.enums.SignEnum.SCISSORS;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

/**
 * The test class for the {@link SignEnum} class.
 *
 * @author Aleksei Batiuta
 */
public class SignEnumTest {
    /**
     * Test data generator.
     *
     * @return test data
     */
    public static Stream<? extends Arguments> testData() {
        return Stream.of(
                Arguments.of(ROCK, null, WIN),
                Arguments.of(ROCK, ROCK, DRAW),
                Arguments.of(ROCK, PAPER, LOSS),
                Arguments.of(ROCK, SCISSORS, WIN),
                Arguments.of(PAPER, null, WIN),
                Arguments.of(PAPER, ROCK, WIN),
                Arguments.of(PAPER, PAPER, DRAW),
                Arguments.of(PAPER, SCISSORS, LOSS),
                Arguments.of(SCISSORS, null, WIN),
                Arguments.of(SCISSORS, ROCK, LOSS),
                Arguments.of(SCISSORS, PAPER, WIN),
                Arguments.of(SCISSORS, SCISSORS, DRAW)
        );
    }

    /**
     * The test method.
     *
     * @param firstThing  the first test data
     * @param secondThing the second test data
     * @param expected    expected result
     */
    @ParameterizedTest(
            name = "{index}: first={0}, second={1}, expected={2}"
    )
    @MethodSource("testData")
    public void test(
            final SignEnum firstThing,
            final SignEnum secondThing,
            final StatusEnum expected) {

        assertNotNull(
                "The first argument can't be as null",
                firstThing
        );
        assertNotNull("Result can't be as null", expected);

        StatusEnum actual = StatusEnum.valueOf(
                firstThing.compareToEnum(secondThing)
        );
        assertEquals(
                String.format(
                        "Test case was failed: {first = %s, second = %s}",
                        firstThing, secondThing
                ),
                expected,
                actual
        );
    }
}
