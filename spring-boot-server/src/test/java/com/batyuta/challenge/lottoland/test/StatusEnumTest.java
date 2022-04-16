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
import static org.springframework.test.util.AssertionErrors.assertNotNull;

import com.batyuta.challenge.lottoland.enums.SignEnum;
import com.batyuta.challenge.lottoland.enums.StatusEnum;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test class for verify the {@code StatusEnum}.
 *
 * @author Aleksei Batyuta aleksei.batiuta@gmail.com
 */
@DisplayName("Status Enum test cases")
public class StatusEnumTest {

    /** Invalid positive test data value. */
    public static final int TEST_STATUS_INVALID_POSITIVE = 2;

    /** Invalid negative test data value. */
    public static final int TEST_STATUS_INVALID_NEGATIVE = -2;

    /**
     * Test data generator.
     *
     * @return test data
     */
    public static Stream<? extends Arguments> testData() {
        return Stream.of(Arguments.of(SignEnum.Const.WIN, StatusEnum.WIN),
                Arguments.of(SignEnum.Const.DRAW, StatusEnum.DRAW), Arguments.of(SignEnum.Const.LOSS, StatusEnum.LOSS),
                Arguments.of(TEST_STATUS_INVALID_NEGATIVE, new IllegalArgumentException()),
                Arguments.of(TEST_STATUS_INVALID_POSITIVE, new IllegalArgumentException()));
    }

    /**
     * The test method.
     *
     * @param data
     *            the test data
     * @param expected
     *            expected result
     */
    @ParameterizedTest(name = "{index}: data={0}, expected={1}")
    @MethodSource("testData")
    public void test(final int data, final Object expected) {
        try {
            StatusEnum actual = StatusEnum.valueOf(data);
            assertEquals(String.format("Test case was failed: {data = %s}", data), expected, actual);
        } catch (IllegalArgumentException actual) {
            assertNotNull(String.format("Expected result is null: {data = %s}", data), expected);
            assertEquals("Unexpected result!", expected.getClass(), actual.getClass());
        }
    }

    @DisplayName("Test Localization")
    @Test
    void testLocalized() throws IOException {
        Properties p = new Properties();
        p.load(getClass().getClassLoader().getResourceAsStream("messages/messages.properties"));
        String msg = p.getProperty("label.player.1.wins");
        assertEquals("There are different", msg, StatusEnum.WIN.getLocalized());
    }
}
