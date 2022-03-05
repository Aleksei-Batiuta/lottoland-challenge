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

package com.batyuta.challenge.lottoland.vo;

import com.batyuta.challenge.lottoland.enums.SignEnum;
import com.batyuta.challenge.lottoland.enums.StatusEnum;
import lombok.Data;

/**
 * Round VO.
 */
@Data
public class RoundVO extends BaseVO {
    /**
     * User ID.
     */
    private int userId;
    /**
     * Choice of first player.
     */
    private SignEnum player1;
    /**
     * Choice of second player.
     */
    private SignEnum player2;
    /**
     * Result of round for the first player.
     */
    private StatusEnum status;

    /**
     * Default Constructor.
     *
     * @param user        user ID
     * @param player1Enum choice of player 1
     * @param player2Enum choice of player 2
     * @param statusEnum  status
     */
    public RoundVO(final int user,
                   final SignEnum player1Enum,
                   final SignEnum player2Enum,
                   final StatusEnum statusEnum) {
        this.userId = user;
        this.player1 = player1Enum;
        this.player2 = player2Enum;
        this.status = statusEnum;
    }
}
