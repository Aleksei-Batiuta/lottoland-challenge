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

package com.batyuta.challenge.lottoland.repository;

import com.batyuta.challenge.lottoland.annotation.LogEntry;
import com.batyuta.challenge.lottoland.enums.StatusEnum;
import com.batyuta.challenge.lottoland.model.RoundEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Round repository implementation class.
 */
@Repository
public class RoundEntityRepository extends BaseEntityRepository<RoundEntity> {

    /**
     * Marks Round as deleted.
     *
     * @param entity entity
     */
    @Override
    protected void deleteImpl(final RoundEntity entity) {
        entity.setDeleted(true);
    }

    /**
     * Gets rounds.
     *
     * @param userId    user ID
     * @param isDeleted deleted flag
     * @param status    round status
     * @param sort      order
     * @return rounds
     */
    public List<RoundEntity> getRounds(final Long userId,
                                       final Boolean isDeleted,
                                       final StatusEnum status,
                                       final Sort sort) {
        Stream<RoundEntity> roundsStream = getEntities()
                .filter(Objects::nonNull);
        if (userId != null) {
            roundsStream = roundsStream
                    .filter(round -> Objects.equals(round.getUserid(), userId));
        }
        if (isDeleted != null) {
            roundsStream = roundsStream
                    .filter(round -> round.isDeleted() == isDeleted);
        }
        if (status != null) {
            roundsStream = roundsStream
                    .filter(round -> round.getStatus() == status);
        }

        return Collections.unmodifiableList(sort(roundsStream, sort));
    }

    /**
     * Deletes rounds.
     *
     * @param userId user ID
     * @param status round status
     * @return count of deleted rounds
     */
    public Integer deleteRounds(final Long userId,
                                final StatusEnum status) {
        return write(
                () -> {
                    Collection<RoundEntity> roundsList =
                            getRounds(
                                    userId,
                                    false,
                                    status,
                                    null
                            );
                    this.deleteAll(roundsList);
                    return roundsList.size();
                }
        );
    }

    /**
     * Deletes all rounds by user ID.
     *
     * @param userId user ID
     * @return count of deleted rounds
     */
    @LogEntry
    public int deleteAllByUserId(final Long userId) {
        return deleteRounds(userId, null);
    }

    /**
     * Gets rounds by user ID.
     *
     * @param userId user ID
     * @return rounds
     */
    @LogEntry
    public Collection<RoundEntity> findByUserId(final Long userId) {
        return getRounds(
                userId,
                false,
                null,
                Sort.by(Sort.Direction.DESC, "id")
        );
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
        return getRounds(null, null, status, null);
    }
}
