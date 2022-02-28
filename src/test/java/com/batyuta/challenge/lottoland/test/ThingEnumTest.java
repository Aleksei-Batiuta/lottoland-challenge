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

import com.batyuta.challenge.lottoland.StatusEnum;
import com.batyuta.challenge.lottoland.ThingEnum;
import junit.framework.TestCase;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.batyuta.challenge.lottoland.StatusEnum.EQUALS;
import static com.batyuta.challenge.lottoland.StatusEnum.GREAT;
import static com.batyuta.challenge.lottoland.StatusEnum.LESS;
import static com.batyuta.challenge.lottoland.ThingEnum.PAPER;
import static com.batyuta.challenge.lottoland.ThingEnum.ROCK;
import static com.batyuta.challenge.lottoland.ThingEnum.SCISSORS;

/**
 * The test class for the {@link ThingEnum} class.
 *
 * @author Aleksei Batiuta
 */
@RunWith(Parameterized.class)
public class ThingEnumTest extends TestCase {
    /**
     * The test data.
     */
    private final TestData testData;

    /**
     * Default constructor.
     *
     * @param testDataValue test data
     */
    public ThingEnumTest(final TestData testDataValue) {
        this.testData = testDataValue;
    }

    /**
     * Test data generator.
     *
     * @return test data
     */
    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<TestData> testData() {
        List<TestData> data = new ArrayList<>();
        data.add(new TestData(ROCK, null, GREAT));
        data.add(new TestData(ROCK, ROCK, EQUALS));
        data.add(new TestData(ROCK, PAPER, LESS));
        data.add(new TestData(ROCK, SCISSORS, GREAT));

        data.add(new TestData(PAPER, null, GREAT));
        data.add(new TestData(PAPER, ROCK, GREAT));
        data.add(new TestData(PAPER, PAPER, EQUALS));
        data.add(new TestData(PAPER, SCISSORS, LESS));

        data.add(new TestData(SCISSORS, null, GREAT));
        data.add(new TestData(SCISSORS, ROCK, LESS));
        data.add(new TestData(SCISSORS, PAPER, GREAT));
        data.add(new TestData(SCISSORS, SCISSORS, EQUALS));
        return data;
    }

    /**
     * The test method.
     */
    @Test
    public void test() {
        StatusEnum expected = testData.getExpected();
        ThingEnum firstThing = testData.getFirst();
        ThingEnum secondThing = testData.getSecond();

        Assert.assertNotNull(
                "The first argument can't be as null",
                firstThing
        );
        Assert.assertNotNull("Result can't be as null", expected);

        StatusEnum actual = StatusEnum.valueOf(
                firstThing.compareToEnum(secondThing)
        );
        Assert.assertEquals(
                testData.toString(),
                expected,
                actual
        );
    }

    /**
     * The test data class.
     */
    @Data
    private static class TestData {
        /**
         * The first test value.
         */
        private ThingEnum first;
        /**
         * The second test value.
         */
        private ThingEnum second;
        /**
         * The expected test result.
         */
        private StatusEnum expected;

        /**
         * Default constructor.
         *
         * @param firstData      the first test data value
         * @param secondData     the second test data value
         * @param expectedResult expected test result
         */
        TestData(final ThingEnum firstData, final ThingEnum secondData,
                 final StatusEnum expectedResult) {
            this.first = firstData;
            this.second = secondData;
            this.expected = expectedResult;
        }
    }
}
