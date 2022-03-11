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
import com.batyuta.challenge.lottoland.model.UserEntity;
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
     * User DAO Service.
     */
    private final UserService userService;
    /**
     * Rounds DAO Service.
     */
    private final RoundService roundService;

    /**
     * Default constructor.
     *
     * @param service  user DAO service
     * @param rService round DAO service
     */
    @Autowired
    public ApplicationService(final UserService service,
                              final RoundService rService) {
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
     * @see UserService#getAllUsers()
     */
    public Collection<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Saves user.
     *
     * @param user user
     * @return updated user entity
     * @see UserService#save(UserEntity)
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
    public UserEntity getUserById(final int userId) {
        return userService.getUserById(userId);
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
    public Collection<RoundEntity> getRoundsByUserId(final int userId) {
        return roundService.getRoundsByUserId(userId);
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
    public void deleteAllRoundsByUserId(final int userId) {
        roundService.deleteAllRoundsByUserId(userId);
    }

    /**
     * Generates a new round and saves it.
     *
     * @param userId user ID
     * @return generated round
     */
    public RoundEntity newRoundByUserId(final int userId) {
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
    public int getTotalRounds() {
        return roundService.getTotalRounds();
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
        return roundService.getTotalRounds(status);
    }
}
