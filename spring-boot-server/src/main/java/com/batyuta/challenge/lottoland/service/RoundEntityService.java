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
import java.util.Random;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/** Round's Entity Service. */
@Service
public class RoundEntityService extends AbstractEntityService<RoundEntity> {

  /** Random instance. */
  private static final Random RANDOM = new Random();

  /** Round Entity repository. */
  private final RoundEntityRepository roundRepository;

  /**
   * Default constructor.
   *
   * @param repository Round Entity Repository
   */
  public RoundEntityService(final RoundEntityRepository repository) {
    super(repository);
    roundRepository = repository;
  }

  /**
   * Gets random integer.
   *
   * @param min minimal value
   * @param max maximal value
   * @return number
   */
  private static int getRandomNumberUsingNextInt(final int min, final int max) {
    return RANDOM.nextInt(max - min + 1) + min;
  }

  /**
   * Update implementation.
   *
   * @param destination destination entity
   * @param source source entity
   * @return updated entity
   */
  @Override
  protected RoundEntity updateImpl(final RoundEntity destination, final RoundEntity source) {
    return source;
  }

  /**
   * Generates a new round and saves it.
   *
   * @param userId user ID
   * @return generated round
   */
  public RoundEntity generate(final Long userId) {
    SignEnum player1;
    SignEnum player2;
    if (getRandomNumberUsingNextInt(0, 1) == 1) {
      player1 = getRandom();
      player2 = SignEnum.ROCK;
    } else {
      player1 = SignEnum.ROCK;
      player2 = getRandom();
    }

    return save(
        new RoundEntity(
            userId, player1, player2, StatusEnum.valueOf(player1.compareToEnum(player2))));
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
   * Deletes all rounds by user ID.
   *
   * @param userId user ID
   * @return count of deleted rounds
   */
  public Long deleteByUserId(final Long userId) {
    return roundRepository.deleteByUserId(userId);
  }

  /**
   * Find all Rounds.
   *
   * @return count
   */
  public long getTotalRounds() {
    return roundRepository.getTotalRoundsByStatus(null);
  }

  /**
   * Find all {@link StatusEnum#WIN} Rounds.
   *
   * @return count
   */
  public long getFirstRounds() {
    return roundRepository.getTotalRoundsByStatus(StatusEnum.WIN);
  }

  /**
   * Find all {@link StatusEnum#LOSS} Rounds.
   *
   * @return count
   */
  public long getSecondRounds() {
    return roundRepository.getTotalRoundsByStatus(StatusEnum.LOSS);
  }

  /**
   * Find all {@link StatusEnum#DRAW} Rounds.
   *
   * @return count
   */
  public long getDraws() {
    return roundRepository.getTotalRoundsByStatus(StatusEnum.DRAW);
  }

  /**
   * Gets entity page from repository.
   *
   * @param userId user ID
   * @param pageable page config
   * @return entities
   */
  public Page<RoundEntity> findAllByUserId(final Long userId, final Pageable pageable) {
    return roundRepository.findAllByUserId(userId, pageable);
  }
}
