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

package com.batyuta.challenge.lottoland.enums;

import static com.batyuta.challenge.lottoland.enums.SignEnum.Const.DRAW;
import static com.batyuta.challenge.lottoland.enums.SignEnum.Const.WIN;
import static com.batyuta.challenge.lottoland.enums.SignEnum.Const.LOSS;

/**
 * Enum of things.
 *
 * @author Aleksei Batyuta aleksei.batiuta@gmail.com
 */
public enum SignEnum implements I18n {
    /**
     * A rock.
     */
    ROCK("label.rock") {
        @SuppressWarnings("Duplicates")
        @Override
        public int compareToEnum(final SignEnum o2) {
            if (o2 == null) {
                return WIN;
            }
            switch (o2) {
                case ROCK:
                    return DRAW;
                case SCISSORS:
                    return WIN;
                case PAPER:
                    return LOSS;
                default:
                    throw new IllegalArgumentException();
            }
        }
    },
    /**
     * A scissors.
     */
    SCISSORS("label.scissors") {
        @SuppressWarnings("Duplicates")
        @Override
        public int compareToEnum(final SignEnum o2) {
            if (o2 == null) {
                return WIN;
            }
            switch (o2) {
                case ROCK:
                    return LOSS;
                case SCISSORS:
                    return DRAW;
                case PAPER:
                    return WIN;
                default:
                    throw new IllegalArgumentException();
            }
        }
    },
    /**
     * A paper.
     */
    PAPER("label.paper") {
        @SuppressWarnings("Duplicates")
        @Override
        public int compareToEnum(final SignEnum o2) {
            if (o2 == null) {
                return WIN;
            }
            switch (o2) {
                case ROCK:
                    return WIN;
                case SCISSORS:
                    return LOSS;
                case PAPER:
                    return DRAW;
                default:
                    throw new IllegalArgumentException();
            }
        }
    };
    /**
     * i18n message key.
     */
    private final String messageKey;

    /**
     * Default Constructor.
     *
     * @param key i18n message key.
     */
    SignEnum(final String key) {
        this.messageKey = key;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }

    /**
     * Compare two things.
     *
     * @param o object to compare with current
     * @return {@link Const#DRAW} if equals,
     * {@link Const#LOSS} if less and
     * {@link Const#WIN} if this is great of compared value
     */
    public int compareToEnum(final SignEnum o) {
        // it should be implemented for each value
        throw new UnsupportedOperationException();
    }

    /**
     * The constant helper.
     */
    public static class Const {
        /**
         * a great value.
         */
        public static final int WIN = 1;
        /**
         * a equals value.
         */
        public static final int DRAW = 0;
        /**
         * a less value.
         */
        public static final int LOSS = -1;
    }
}
