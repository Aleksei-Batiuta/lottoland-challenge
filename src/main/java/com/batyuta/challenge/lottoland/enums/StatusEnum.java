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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The status enums. It's used for visualization of
 * comparing test cases
 *
 * @author Aleksei Batyuta aleksei.batiuta@gmail.com
 */
public enum StatusEnum implements I18n {
    /**
     * Player won the round.
     */
    WIN(SignEnum.Const.WIN, "label.player.1.wins"),
    /**
     * Players played a draw round.
     */
    DRAW(SignEnum.Const.DRAW, "label.players.draw"),
    /**
     * Player lost the round.
     */
    LOSS(SignEnum.Const.LOSS, "label.player.2.wins");

    /**
     * the flag value.
     */
    private final int status;
    /**
     * i18n message key.
     */
    private final String messageKey;

    /**
     * Default constructor.
     *
     * @param statusValue status value.
     * @param key         i18n message key.
     */
    StatusEnum(final int statusValue, final String key) {
        this.status = statusValue;
        this.messageKey = key;
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
            case SignEnum.Const.WIN:
                return WIN;
            case SignEnum.Const.DRAW:
                return DRAW;
            case SignEnum.Const.LOSS:
                return LOSS;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String getMessageKey() {
        return this.messageKey;
    }

    /**
     * Getter of status value.
     *
     * @return value
     */
    @JsonIgnore
    public int getStatus() {
        return status;
    }
}
