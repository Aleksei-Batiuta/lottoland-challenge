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

package com.batyuta.challenge.lottoland;

import static com.batyuta.challenge.lottoland.StatusEnum.Const.EQUALS;
import static com.batyuta.challenge.lottoland.StatusEnum.Const.GREAT;
import static com.batyuta.challenge.lottoland.StatusEnum.Const.LESS;

/**
 * Enum of things.
 *
 * @author Aleksei Batyuta aleksei.batiuta@gmail.com
 */
public enum ThingEnum {
    /**
     * A rock.
     */
    ROCK {
        @SuppressWarnings("Duplicates")
        @Override
        public int compareToEnum(final ThingEnum o2) {
            if (o2 == null) {
                return GREAT;
            }
            switch (o2) {
                case ROCK:
                    return EQUALS;
                case SCISSORS:
                    return GREAT;
                case PAPER:
                    return LESS;
                default:
                    throw new IllegalArgumentException();
            }
        }
    },
    /**
     * A scissors.
     */
    SCISSORS {
        @SuppressWarnings("Duplicates")
        @Override
        public int compareToEnum(final ThingEnum o2) {
            if (o2 == null) {
                return GREAT;
            }
            switch (o2) {
                case ROCK:
                    return LESS;
                case SCISSORS:
                    return EQUALS;
                case PAPER:
                    return GREAT;
                default:
                    throw new IllegalArgumentException();
            }
        }
    },
    /**
     * A papaper.
     */
    PAPER {
        @SuppressWarnings("Duplicates")
        @Override
        public int compareToEnum(final ThingEnum o2) {
            if (o2 == null) {
                return GREAT;
            }
            switch (o2) {
                case ROCK:
                    return GREAT;
                case SCISSORS:
                    return LESS;
                case PAPER:
                    return EQUALS;
                default:
                    throw new IllegalArgumentException();
            }
        }
    };

    /**
     * Compare two things.
     *
     * @param o object to compare with current
     * @return {@link StatusEnum.Const#EQUALS} if equals,
     * {@link StatusEnum.Const#LESS} if less and
     * {@link StatusEnum.Const#GREAT} if this is great of compared value
     */
    public int compareToEnum(final ThingEnum o) {
        // it should be implemented for each value
        throw new UnsupportedOperationException();
    }
}