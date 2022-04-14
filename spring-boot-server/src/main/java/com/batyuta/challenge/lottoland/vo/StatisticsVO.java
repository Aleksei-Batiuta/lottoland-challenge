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

package com.batyuta.challenge.lottoland.vo;

import lombok.ToString;

/** Statistics page VO. */
@ToString
public class StatisticsVO {

  /** The total of all rounds. */
  private final long total;

  /** The first users wins. */
  private final long first;

  /** The second users wins. */
  private final long second;

  /** The total of draws. */
  private final long draws;

  /**
   * Default constructor.
   *
   * @param totalRounds the total of all rounds
   * @param firstRounds the first users wins
   * @param secondRounds the second users wins
   * @param totalDraws the total of draws
   */
  public StatisticsVO(
      final long totalRounds,
      final long firstRounds,
      final long secondRounds,
      final long totalDraws) {
    this.total = totalRounds;
    this.first = firstRounds;
    this.second = secondRounds;
    this.draws = totalDraws;
  }

  /**
   * Getter of total of all rounds.
   *
   * @return the total of all rounds.
   */
  public long getTotal() {
    return total;
  }

  /**
   * Getter of the total wins of the first user.
   *
   * @return the total wins of the first user.
   */
  public long getFirst() {
    return first;
  }

  /**
   * Getter of the total wins of the second user.
   *
   * @return the total wins of the second user.
   */
  public long getSecond() {
    return second;
  }

  /**
   * Getter of the total draws.
   *
   * @return the total draws.
   */
  public long getDraws() {
    return draws;
  }
}
