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

import com.batyuta.challenge.lottoland.ThingEnum;
import lombok.Getter;

/**
 * The status enums. It's used for visualization of
 * comparing test cases
 *
 * @author Aleksei Batyuta aleksei.batiuta@gmail.com
 */
@Getter
public enum StatusEnum {
    /**
     * the great flag.
     */
    GREAT(ThingEnum.Const.GREAT),
    /**
     * the equals flag.
     */
    EQUALS(ThingEnum.Const.EQUALS),
    /**
     * the less flag.
     */
    LESS(ThingEnum.Const.LESS);

    /**
     * the flag value.
     */
    private final int status;

    /**
     * Default constructor.
     *
     * @param statusValue status value.
     */
    StatusEnum(final int statusValue) {
        this.status = statusValue;
    }

    /**
     * Resolves the status enum by its value.
     *
     * @param status status value.
     * @return status enum.
     * @throws IllegalArgumentException if enum was not found by status value
     */
    public static StatusEnum valueOf(final int status) {
        switch (status) {
            case ThingEnum.Const.GREAT:
                return GREAT;
            case ThingEnum.Const.EQUALS:
                return EQUALS;
            case ThingEnum.Const.LESS:
                return LESS;
            default:
                throw new IllegalArgumentException();
        }
    }
}
