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

package com.batyuta.challenge.lottoland.service;

import com.batyuta.challenge.lottoland.enums.SignEnum;
import com.batyuta.challenge.lottoland.enums.StatusEnum;
import com.batyuta.challenge.lottoland.model.RoundEntity;
import com.batyuta.challenge.lottoland.repository.RoundEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RoundEntityService extends AbstractEntityService<RoundEntity> {
    /**
     * Random instance.
     */
    private static final Random RANDOM = new Random();

    public RoundEntityService(RoundEntityRepository repository) {
        super(repository);
    }

    @Override
    protected RoundEntity update(RoundEntity destination, RoundEntity source) {
        // todo: it should be reviewed
        return source;
    }

    /**
     * Generates a new round and saves it.
     *
     * @param userId user ID
     * @return generated round
     */
    public Page<RoundEntity> generate(final Long userId) {
        SignEnum player1;
        SignEnum player2;
        if (getRandomNumberUsingNextInt(0, 1) == 1) {
            player1 = getRandom();
            player2 = SignEnum.ROCK;
        } else {
            player1 = SignEnum.ROCK;
            player2 = getRandom();

        }

        RoundEntity save = save(
                new RoundEntity(
                        userId,
                        player1,
                        player2,
                        StatusEnum.valueOf(
                                player1.compareToEnum(player2)
                        )
                )
        );

        return findAll((Pageable) null);
    }

    /**
     * Gets random Sign.
     *
     * @return random Sign
     */
    private SignEnum getRandom() {
        SignEnum[] values = SignEnum.values();
        return values[getRandomNumberUsingNextInt(0, values.length - 1)];
    }


    /**
     * Gets random integer.
     *
     * @param min minimal value
     * @param max maximal value
     * @return number
     */
    private static int getRandomNumberUsingNextInt(final int min,
                                                   final int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    public void deleteByUserId(Long userId) {
        ((RoundEntityRepository)repository).deleteByUserId(userId);
    }

    public long getTotalRounds() {
        return ((RoundEntityRepository)repository).getTotalRoundsByStatus(null);
    }

    public long getFirstRounds() {
        return ((RoundEntityRepository)repository).getTotalRoundsByStatus(StatusEnum.WIN);
    }

    public long getSecondRounds() {
        return ((RoundEntityRepository)repository).getTotalRoundsByStatus(StatusEnum.LOSS);
    }

    public long getDraws() {
        return ((RoundEntityRepository)repository).getTotalRoundsByStatus(StatusEnum.DRAW);
    }
}
