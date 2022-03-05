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
     * @param repository data repository
     */
    @Autowired
    public RoundDAO(final DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public RoundEntity create(final RoundEntity round) {
        try {
            Collection<RoundEntity> rounds = repository.getRounds();
            rounds.add(round);
            return round;
        } finally {
            log.info("create: Round {} has been saved", round);
        }
    }

    @Override
    public Collection<RoundEntity> getAll() {
        List<RoundEntity> rounds = null;
        try {
            rounds = repository.getRounds().stream()
                    .filter(Objects::nonNull)
                    .collect(
                            Collectors.collectingAndThen(
                                    Collectors.toList(),
                                    Collections::unmodifiableList
                            )
                    );
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
    public Collection<RoundEntity> getRoundsByUserId(
            final int userId) {
        List<RoundEntity> rounds = null;
        try {
            List<RoundEntity> list = repository.getRounds().stream()
                    .filter(Objects::nonNull)
                    .filter(
                            round ->
                                    !round.isDeleted()
                                            && round.getUserid() == userId)
                    .collect(Collectors.toList());
            Collections.reverse(list);
            rounds = Collections.unmodifiableList(list);
            return rounds;
        } finally {
            log("getRoundsByUserId: userId = " + userId, rounds);
        }
    }

    /**
     * Deletes all rounds by user ID.
     *
     * @param userId user ID
     */
    public void deleteAllByUserId(final int userId) {
        Collection<RoundEntity> rounds = null;
        try {
            rounds = getRoundsByUserId(userId);
            rounds.forEach(
                    roundEntity ->
// todo: it should be as concurrency safe operation
                            roundEntity.setDeleted(true)
            );
        } finally {
            log("deleteAllByUserId: userId = " + userId, rounds);
        }
    }

    /**
     * Gets total played rounds by status.
     *
     * @param status status of rounds
     * @return number of total played rounds
     */
    public Collection<RoundEntity> getAllRoundsByStatus(
            final StatusEnum status) {
        List<RoundEntity> rounds = null;
        try {
            List<RoundEntity> list = repository.getRounds().stream()
                    .filter(Objects::nonNull)
                    .filter(roundEntity -> roundEntity.getStatus() == status)
                    .collect(Collectors.toList());
            rounds = Collections.unmodifiableList(list);
            return rounds;
        } finally {
            log("getRoundsByStatus: status = " + status, rounds);
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


    private void log(String methodName, Collection<RoundEntity> rounds) {
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
