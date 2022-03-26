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
import com.batyuta.challenge.lottoland.exception.DataException;
import com.batyuta.challenge.lottoland.model.BaseEntity;
import com.batyuta.challenge.lottoland.model.RoundEntity;
import com.batyuta.challenge.lottoland.model.UserEntity;
import com.batyuta.challenge.lottoland.repository.RoundEntityRepository;
import com.batyuta.challenge.lottoland.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Random;
import java.util.UUID;

/**
 * The General Application Service.
 */
@Service
public class ApplicationService {
    /**
     * Random instance.
     */
    private static final Random RANDOM = new Random();
    /**
     * User repository.
     */
    private final UserEntityRepository userService;
    /**
     * Rounds repository.
     */
    private final RoundEntityRepository roundService;

    /**
     * Default constructor.
     *
     * @param service  user repository
     * @param rService round repository
     */
    @Autowired
    public ApplicationService(final UserEntityRepository service,
                              final RoundEntityRepository rService) {
        this.userService = service;
        this.roundService = rService;
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

    /**
     * Getter for all users.
     *
     * @return users
     * @see UserEntityRepository#findAll()
     */
    public Iterable<UserEntity> getAllUsers() {
        return userService.findAll();
    }

    /**
     * Saves user.
     *
     * @param user user
     * @return updated user entity
     * @see com.batyuta.challenge.lottoland.repository.BaseEntityRepository#save(BaseEntity) to details
     */
    public UserEntity saveUser(final UserEntity user) {
        return userService.save(user);
    }

    /**
     * Gets user by user ID.
     *
     * @param userId user ID
     * @return user
     */
    public UserEntity getUserById(final Long userId) {
        return userService.findById(userId).orElseThrow(
                () -> new DataException("error.data.user.not.found", userId)
        );
    }

    /**
     * Creates a new or gets existed {@link UserEntity}
     * from repository.
     *
     * @param user user entity
     * @return new or existed user
     */
    public UserEntity getOrCreateUser(final UserEntity user) {
        UserEntity userEntity;
        if (user == null || user.isNew()) {
            userEntity = saveUser(
                    new UserEntity(
                            UserEntity.NEW_ENTITY_ID,
                            UUID.randomUUID().toString()
                    )
            );
        } else {
            userEntity = getUserById(user.getId());
        }
        return userEntity;
    }

    /**
     * Gets rounds by user ID.
     *
     * @param userId user ID
     * @return rounds
     */
    public Collection<RoundEntity> getRoundsByUserId(final Long userId) {
        return roundService.findByUserId(userId);
    }

    /**
     * Saves round.
     *
     * @param round round
     * @return saved round
     */
    public RoundEntity saveRound(final RoundEntity round) {
        return roundService.save(round);
    }

    /**
     * Deletes all rounds by user ID.
     *
     * @param userId user ID
     */
    public void deleteAllRoundsByUserId(final Long userId) {
        roundService.deleteAllByUserId(userId);
    }

    /**
     * Generates a new round and saves it.
     *
     * @param userId user ID
     * @return generated round
     */
    public RoundEntity newRoundByUserId(final Long userId) {
        SignEnum player1;
        SignEnum player2;
        if (getRandomNumberUsingNextInt(0, 1) == 1) {
            player1 = getRandom();
            player2 = SignEnum.ROCK;
        } else {
            player1 = SignEnum.ROCK;
            player2 = getRandom();

        }

        return saveRound(
                new RoundEntity(
                        userId,
                        player1,
                        player2,
                        StatusEnum.valueOf(
                                player1.compareToEnum(player2)
                        )
                )
        );
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
     * Gets total played rounds.
     *
     * @return number of rounds
     */
    public long getTotalRounds() {
        return roundService.count();
    }

    /**
     * Gets total wins.
     *
     * @return number of rounds
     */
    public int getFirstRounds() {
        return getTotalRounds(StatusEnum.WIN);
    }

    /**
     * Gets total loses.
     *
     * @return number of rounds
     */
    public int getSecondRounds() {
        return getTotalRounds(StatusEnum.LOSS);
    }

    /**
     * Gets total draws.
     *
     * @return number of rounds
     */
    public int getDraws() {
        return getTotalRounds(StatusEnum.DRAW);
    }

    /**
     * Gets total played rounds by round status.
     *
     * @param status round status
     * @return number of rounds
     */
    private int getTotalRounds(final StatusEnum status) {
        return roundService.getTotalRoundsByStatus(status);
    }
}
