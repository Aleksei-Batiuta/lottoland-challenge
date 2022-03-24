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

import com.batyuta.challenge.lottoland.annotation.LogEntry;
import com.batyuta.challenge.lottoland.enums.StatusEnum;
import com.batyuta.challenge.lottoland.model.RoundEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Round DAO class.
 */
@Repository
public class RoundDAO implements CrudDAO<RoundEntity> {
    /**
     * Data Repository.
     */
    private final DataRepository repository;

    /**
     * Default Constructor.
     *
     * @param dataRepository data repository
     */
    @Autowired
    public RoundDAO(final DataRepository dataRepository) {
        this.repository = dataRepository;
    }

    /**
     * Save a new {@link RoundEntity} into DB/Memory.
     *
     * @param round round
     * @return updated round
     */
    @Override
    @LogEntry
    public RoundEntity create(final RoundEntity round) {
        return repository.createRound(round);
    }

    /**
     * Deletes all rounds by user ID.
     *
     * @param userId user ID
     * @return count of deleted rounds
     */
    @LogEntry
    public int deleteAllByUserId(final int userId) {
        return repository.deleteRounds(userId, null);
    }

    /**
     * Gets all {@link RoundEntity} from DB/Memory.
     *
     * @return rounds
     */
    @Override
    @LogEntry
    public Collection<RoundEntity> getAll() {
        return repository.getRounds(null, null, null, false);
    }

    /**
     * Gets rounds by user ID.
     *
     * @param userId user ID
     * @return rounds
     */
    @LogEntry
    public Collection<RoundEntity> getRoundsByUserId(final int userId) {
        return repository.getRounds(userId, false, null, true);
    }

    /**
     * Gets total played rounds.
     *
     * @return number of total played rounds
     */
    @LogEntry
    public int getTotalRounds() {
        return getAll().size();
    }

    /**
     * Gets total played rounds by status.
     *
     * @param status status of rounds
     * @return number of total played rounds
     */
    @LogEntry
    public int getTotalRoundsByStatus(final StatusEnum status) {
        return getAllRoundsByStatus(status).size();
    }

    /**
     * Gets total played rounds by status.
     *
     * @param status status of rounds
     * @return number of total played rounds
     */
    @LogEntry
    public Collection<RoundEntity> getAllRoundsByStatus(
            final StatusEnum status) {
        return repository.getRounds(null, null, status, false);
    }
}
