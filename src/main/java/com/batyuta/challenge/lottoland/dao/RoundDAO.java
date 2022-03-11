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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Round DAO class.
 */
@Repository
public class RoundDAO implements CrudDAO<RoundEntity> {
    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger(RoundDAO.class);
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
    public RoundEntity create(final RoundEntity round) {
        try {
            return repository.createRound(round);
        } finally {
            log.info("create: Round {} has been created", round);
        }
    }

    /**
     * Deletes all rounds by user ID.
     *
     * @param userId user ID
     * @return count of deleted rounds
     */
    public int deleteAllByUserId(final int userId) {
        int rounds = 0;
        try {
            rounds = repository.deleteRounds(userId, null);
            return rounds;
        } finally {
            log.info(
                    "deleteAllByUserId: user = {}; count = {}",
                    userId,
                    rounds
            );
        }
    }

    /**
     * Gets all {@link RoundEntity} from DB/Memory.
     *
     * @return rounds
     */
    @Override
    public Collection<RoundEntity> getAll() {
        Collection<RoundEntity> rounds = null;
        try {
            rounds = repository.getRounds(null, null, null, false);
            return rounds;
        } finally {
            log("getAll", rounds);
        }
    }

    /**
     * Gets rounds by user ID.
     *
     * @param userId user ID
     * @return rounds
     */
    public Collection<RoundEntity> getRoundsByUserId(final int userId) {
        Collection<RoundEntity> rounds = null;
        try {
            rounds = repository.getRounds(userId, false, null, true);
            return rounds;
        } finally {
            log("getRoundsByUserId: userId = " + userId, rounds);
        }
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
        return getAllRoundsByStatus(status).size();
    }

    /**
     * Gets total played rounds by status.
     *
     * @param status status of rounds
     * @return number of total played rounds
     */
    public Collection<RoundEntity> getAllRoundsByStatus(
            final StatusEnum status) {
        Collection<RoundEntity> rounds = null;
        try {
            rounds = repository.getRounds(null, null, status, false);
            return rounds;
        } finally {
            log("getRoundsByStatus: status = " + status, rounds);
        }
    }

    private void log(final String methodName,
                     final Collection<RoundEntity> rounds) {
        if (rounds == null || rounds.isEmpty()) {
            if (log.isTraceEnabled()) {
                log.trace("{}: No Round Entities in repo!", methodName);
            } else {
                log.info("{}", methodName);
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace(
                        "{}: [\n{}\n]",
                        methodName,
                        rounds.stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(",\n"))
                );
            } else {
                log.info("{}: count = {}", methodName, rounds.size());
            }
        }
    }
}
