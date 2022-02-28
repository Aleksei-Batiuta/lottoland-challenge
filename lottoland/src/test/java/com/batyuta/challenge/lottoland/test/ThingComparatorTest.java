package com.batyuta.challenge.lottoland.test;

import com.batyuta.challenge.lottoland.StatusEnum;
import com.batyuta.challenge.lottoland.ThingComparator;
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
 * Test class for verify the {@link ThingComparator}.
 *
 * @author Aleksei Batyuta aleksei.batiuta@gmail.com
 */
@RunWith(Parameterized.class)
public class ThingComparatorTest extends TestCase {
    /**
     * Comparator instance.
     */
    private final ThingComparator comparator = new ThingComparator();
    /**
     * Test data.
     */
    private final TestData testData;

    /**
     * Default constructor.
     *
     * @param testDataValue test data
     */
    public ThingComparatorTest(final TestData testDataValue) {
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

        data.add(new TestData(null, null, EQUALS));
        data.add(new TestData(null, ROCK, LESS));
        data.add(new TestData(null, PAPER, LESS));
        data.add(new TestData(null, SCISSORS, LESS));
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
        StatusEnum actual = StatusEnum.valueOf(
                comparator.compare(firstThing, secondThing)
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
         * The first value of test data.
         */
        private final ThingEnum first;
        /**
         * The second value of test data.
         */
        private final ThingEnum second;
        /**
         * The expected value of test result.
         */
        private StatusEnum expected;

        TestData(final ThingEnum firstData, final ThingEnum secondData,
                        final StatusEnum expectedValue) {
            this.first = firstData;
            this.second = secondData;
            this.expected = expectedValue;

            Assert.assertNotNull("Result can't be as null", expected);
        }
    }
}
