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

package com.batyuta.challenge.lottoland.dao;

import com.batyuta.challenge.lottoland.concurrency.LockedData;
import com.batyuta.challenge.lottoland.enums.StatusEnum;
import com.batyuta.challenge.lottoland.model.BaseEntity;
import com.batyuta.challenge.lottoland.model.RoundEntity;
import com.batyuta.challenge.lottoland.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Data repository class.
 */
@Component
public final class DataRepository extends LockedData {
    /**
     * This is a user table index.
     */
    private final AtomicInteger userIndex = new AtomicInteger(0);
    /**
     * This is a round table index.
     */
    private final AtomicInteger roundIndex = new AtomicInteger(0);
    /**
     * This is a user table.
     */
    private final Collection<UserEntity> users = new ArrayList<>();

    /**
     * This is a round table.
     */
    private final Collection<RoundEntity> rounds = new ArrayList<>();

    /**
     * Gets rounds.
     *
     * @param userId            user ID
     * @param isDeleted         deleted flag
     * @param status            round status
     * @param isDescendingOrder order
     * @return rounds
     */
    public Collection<RoundEntity> getRounds(Integer userId, Boolean isDeleted,
                                             StatusEnum status,
                                             boolean isDescendingOrder) {
        return read(
                () -> {
                    Stream<RoundEntity> roundsStream = rounds.stream()
                            .filter(Objects::nonNull);
                    if (userId != null) {
                        roundsStream = roundsStream
                                .filter(round -> round.getUserid() == userId);
                    }
                    if (isDeleted != null) {
                        roundsStream = roundsStream
                                .filter(
                                        round ->
                                                round.isDeleted() == isDeleted
                                );
                    }
                    if (status != null) {
                        roundsStream = roundsStream
                                .filter(round -> round.getStatus() == status);
                    }
                    List<RoundEntity> roundsList =
                            roundsStream.collect(Collectors.toList());
                    if (isDescendingOrder) {
                        Collections.reverse(roundsList);
                    }
                    return Collections.unmodifiableList(roundsList);
                }
        );
    }

    /**
     * Deletes rounds.
     *
     * @param userId user ID
     * @param status round status
     * @return count of deleted rounds
     */
    public Integer deleteRounds(Integer userId, StatusEnum status) {
        return write(
                () -> {
                    Collection<RoundEntity> roundsList = getRounds(
                            userId,
                            false,
                            status,
                            false
                    );
                    roundsList.forEach(this::markRoundAsDeleted);
                    return roundsList.size();
                }
        );
    }

    private void markRoundAsDeleted(RoundEntity round) {
        round.setDeleted(true);
    }

    private void setId(BaseEntity entity, int id) {
        entity.setId(id);
    }

    /**
     * Creates round.
     *
     * @param round round
     * @return created round
     */
    public RoundEntity createRound(RoundEntity round) {
        return write(
                () -> {
                    setId(round, roundIndex.incrementAndGet());
                    if (rounds.add(round)) {
                        return round;
                    }
                    throw new IllegalArgumentException();
                }
        );
    }

    /**
     * Creates User.
     *
     * @param user user
     * @return created user
     */
    public UserEntity createUser(UserEntity user) {
        return write(
                () -> {
                    setId(user, userIndex.incrementAndGet());
                    if (users.add(user)) {
                        return user;
                    }
                    throw new IllegalArgumentException();
                }
        );
    }

    /**
     * Gets user by ID.
     *
     * @param userId user ID
     * @return user
     */
    public UserEntity getUser(int userId) {
        return read(
                () -> users.stream()
                        .filter(user -> user.getId() == userId)
                        .findFirst()
                        .orElse(null)
        );
    }
}
