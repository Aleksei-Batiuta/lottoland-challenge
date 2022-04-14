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
import com.batyuta.challenge.lottoland.utils.PageableUtils;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/** Round repository implementation class. */
@Repository
public class RoundEntityRepository extends BaseEntityRepository<RoundEntity>
    implements PagingAndSortingRepository<RoundEntity, Long> {

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
   * @param userId user ID
   * @param isDeleted deleted flag
   * @param status round status
   * @param pageSettings page setting
   * @return rounds
   */
  @LogEntry
  public Page<RoundEntity> getRounds(
      final Long userId,
      final Boolean isDeleted,
      final StatusEnum status,
      final Pageable pageSettings) {
    Pageable pageable = pageSettings;
    Stream<RoundEntity> roundsStream = getEntities().filter(Objects::nonNull);
    if (userId != null) {
      roundsStream = roundsStream.filter(round -> Objects.equals(round.getUserid(), userId));
    }
    if (isDeleted != null) {
      roundsStream = roundsStream.filter(round -> round.isDeleted() == isDeleted);
    }
    if (status != null) {
      roundsStream = roundsStream.filter(round -> round.getStatus() == status);
    }

    if (pageable == null) {
      pageable = Pageable.unpaged();
    }

    List<RoundEntity> list = sort(roundsStream, pageable.getSort());
    int total = list.size();
    pageable = PageableUtils.fixPageable(pageable, total);
    return subList(list, pageable, total);
  }

  /**
   * Deletes rounds.
   *
   * @param userId user ID
   * @param status round status
   * @return count of deleted rounds
   */
  public Long deleteRounds(final Long userId, final StatusEnum status) {
    return write(
        () -> {
          Page<RoundEntity> roundsList = getRounds(userId, false, status, null);
          this.deleteAll(roundsList);
          return roundsList.getTotalElements();
        });
  }

  /**
   * Deletes all rounds by user ID.
   *
   * @param userId user ID
   * @return count of deleted rounds
   */
  public long deleteAllByUserId(final Long userId) {
    return deleteRounds(userId, null);
  }

  /**
   * Gets entity page from repository.
   *
   * @param userId user ID
   * @param pageable page config
   * @return entities
   */
  public Page<RoundEntity> findAllByUserId(final Long userId, final Pageable pageable) {
    return getRounds(userId, false, null, pageable);
  }

  /**
   * Gets total played rounds by status.
   *
   * @param status status of rounds
   * @return number of total played rounds
   */
  public Long getTotalRoundsByStatus(final StatusEnum status) {
    Page<RoundEntity> allRoundsByStatus = getAllRoundsByStatus(status);
    return allRoundsByStatus.getTotalElements();
  }

  /**
   * Gets total played rounds by status.
   *
   * @param status status of rounds
   * @return number of total played rounds
   */
  public Page<RoundEntity> getAllRoundsByStatus(final StatusEnum status) {
    return getRounds(null, null, status, null);
  }

  /**
   * Deletes all user's rounds.
   *
   * @param userId user ID
   * @return count of deleted items
   */
  public Long deleteByUserId(final Long userId) {
    Page<RoundEntity> allByUserId = findAllByUserId(userId, null);
    deleteAll(allByUserId);
    return allByUserId.getTotalElements();
  }

  /**
   * Check round to deleted state.
   *
   * @param entity round entity
   * @return <code>true</code> if round already deleted
   */
  @Override
  protected boolean isDeleted(final RoundEntity entity) {
    return entity == null || entity.isDeleted();
  }
}
