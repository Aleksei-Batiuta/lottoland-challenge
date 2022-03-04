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

package com.batyuta.challenge.lottoland.utils;

import com.batyuta.challenge.lottoland.enums.SignEnum;

import java.util.Comparator;

import static com.batyuta.challenge.lottoland.enums.SignEnum.Const.DRAW;
import static com.batyuta.challenge.lottoland.enums.SignEnum.Const.LOSS;

/**
 * Things comparator.
 *
 * @author Aleksei Batiuta
 */
public class ThingComparator implements Comparator<SignEnum> {

    /**
     * Compares two things.
     *
     * @param o1 the first thing
     * @param o2 the second thing
     * @return {@link SignEnum.Const#DRAW} if equals,
     * {@link SignEnum.Const#LOSS} if less and
     * {@link SignEnum.Const#WIN} if this is great of compared value
     */
    @Override
    public int compare(final SignEnum o1, final SignEnum o2) {
        if (o1 != null) {
            // the first is NOT null
            return o1.compareToEnum(o2);
        } else {
            if (o2 == null) {
                // both are null
                return DRAW;
            } else {
                // the first is null but the second in NOT null
                return LOSS;
            }
        }
    }
}
