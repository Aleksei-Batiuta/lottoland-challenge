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
import junit.framework.TestCase;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Test class for verify the {@code StatusEnum}.
 *
 * @author Aleksei Batyuta aleksei.batiuta@gmail.com
 */
@RunWith(Parameterized.class)
public class StatusEnumTest extends TestCase {
    /**
     * Maximal test data value.
     */
    public static final int TEST_STATUS_MAXIMAL = 5;
    /**
     * Minimal test data value.
     */
    public static final int TEST_STATUS_MINIMAL = -5;
    /**
     * Test data values.
     */
    private final TestData testData;

    /**
     * Parameterized default constructor.
     *
     * @param testDataValue test data
     */
    public StatusEnumTest(final TestData testDataValue) {
        this.testData = testDataValue;
    }

    /**
     * The test data array.
     *
     * @return test data
     */
    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<TestData> testData() {
        List<TestData> data = new ArrayList<>();
        data.add(new TestData(StatusEnum.Const.GREAT, StatusEnum.GREAT));
        data.add(new TestData(StatusEnum.Const.EQUALS, StatusEnum.EQUALS));
        data.add(new TestData(StatusEnum.Const.LESS, StatusEnum.LESS));
        for (int i = TEST_STATUS_MINIMAL; i < TEST_STATUS_MAXIMAL; i++) {
            if (i > StatusEnum.Const.GREAT || i < StatusEnum.Const.LESS) {
                data.add(new TestData(i, new IllegalArgumentException()));
            }
        }
        return data;
    }

    /**
     * The test method.
     */
    @Test
    public void test() {
        int data = testData.getData();
        Object expected = testData.getExpected();
        try {
            StatusEnum actual = StatusEnum.valueOf(data);
            Assert.assertEquals(
                    testData.toString(),
                    expected,
                    actual
            );
        } catch (IllegalArgumentException actual) {
            Assert.assertNotNull(testData.toString(), expected);
            Assert.assertEquals(
                    testData.toString(),
                    expected.getClass(),
                    actual.getClass()
            );
        }
    }

    @Data
    private static class TestData {
        /**
         * Test value.
         */
        private final int data;

        /**
         * Expected value.
         */
        private final Object expected;

        TestData(final int testData, final Object expectedData) {
            this.data = testData;
            this.expected = expectedData;

            Assert.assertNotNull("Result can't be as null", expectedData);
        }
    }
}
