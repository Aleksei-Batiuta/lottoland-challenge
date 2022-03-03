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

import com.batyuta.challenge.lottoland.enums.StatusEnum;
import com.batyuta.challenge.lottoland.model.RoundEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Round DAO class.
 */
@Component
public final class RoundDAO implements CrudDAO<RoundEntity> {
    /**
     * Data Repository.
     */
    private final DataRepository repository;

    /**
     * Default Constructor.
     *
     * @param repository data repository
     */
    @Autowired
    public RoundDAO(final DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public RoundEntity create(final RoundEntity round) {
        List<RoundEntity> rounds = repository.getRounds();
        rounds.add(round);
        int index = rounds.size() - 1;
        round.setId(index);
        return round;
    }

    @Override
    public RoundEntity update(final RoundEntity user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RoundEntity get(final int id) {
        return repository.getRounds().get(id);
    }

    @Override
    public Collection<RoundEntity> getAll() {
        return repository.getRounds().stream()
                .filter(Objects::nonNull)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                Collections::unmodifiableList
                        )
                );
    }

    @Override
    public void delete(final RoundEntity round) {
        repository.getRounds().set(round.getId(), null);
    }

    /**
     * Gets rounds by user ID.
     *
     * @param userId user ID
     * @return rounds
     */
    public Collection<RoundEntity> getRoundsByUserId(
            final int userId) {
        List<RoundEntity> list = repository.getRounds().stream()
                .filter(Objects::nonNull)
                .filter(
                        roundEntity ->
                                !roundEntity.isDeleted()
                                        && roundEntity.getUserid() == userId)
                .collect(Collectors.toList());
        Collections.reverse(list);
        return Collections.unmodifiableList(list);
    }

    /**
     * Deletes all rounds by user ID.
     *
     * @param userId user ID
     */
    public void deleteAllByUserId(final int userId) {
        getRoundsByUserId(userId)
                .forEach(roundEntity -> roundEntity.setDeleted(true));
    }

    /**
     * Gets total played rounds by status.
     *
     * @param status status of rounds
     * @return number of total played rounds
     */
    public Collection<RoundEntity> getRoundsByStatus(
            final StatusEnum status) {
        List<RoundEntity> list = repository.getRounds().stream()
                .filter(Objects::nonNull)
                .filter(roundEntity -> roundEntity.getStatus() == status)
                .collect(Collectors.toList());
        return Collections.unmodifiableList(list);
    }

    /**
     * Gets total played rounds.
     *
     * @return number of total played rounds
     */
    public int getTotalRounds() {
        return getAll().size();
    }

    /**
     * Gets total played rounds by status.
     *
     * @param status status of rounds
     * @return number of total played rounds
     */
    public int getTotalRoundsByStatus(final StatusEnum status) {
        return getRoundsByStatus(status).size();
    }
}
