/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.batyuta.challenge.lottoland.model;

import com.batyuta.challenge.lottoland.enums.SignEnum;
import com.batyuta.challenge.lottoland.enums.StatusEnum;
import java.text.MessageFormat;
import java.util.Comparator;
import javax.persistence.Entity;
import lombok.ToString;

/** Round entity. */
@Entity
@ToString
public class RoundEntity extends BaseEntity<RoundEntity> {

  /** User ID. */
  private Long userid;

  /** Choice of first player. */
  private SignEnum player1;

  /** Choice of second player. */
  private SignEnum player2;

  /** Result of round for the first player. */
  private StatusEnum status;

  /** Deleted flag. */
  private boolean deleted = false;

  /** Default no-arguments constructor. */
  public RoundEntity() {
    super();
  }

  /**
   * Default Constructor.
   *
   * @param user user ID
   * @param player1Enum choice of player 1
   * @param player2Enum choice of player 2
   * @param statusEnum status
   */
  public RoundEntity(final Long user, final SignEnum player1Enum,
      final SignEnum player2Enum, final StatusEnum statusEnum) {
    this(NEW_ENTITY_ID, user, player1Enum, player2Enum, statusEnum);
  }

  /**
   * Default Constructor.
   *
   * @param roundId round ID
   * @param user user ID
   * @param player1Enum choice of player 1
   * @param player2Enum choice of player 2
   * @param statusEnum status
   */
  public RoundEntity(final Long roundId, final Long user,
      final SignEnum player1Enum, final SignEnum player2Enum,
      final StatusEnum statusEnum) {
    super(roundId);
    this.userid = user;
    this.player1 = player1Enum;
    this.player2 = player2Enum;
    this.status = statusEnum;
  }

  /**
   * Getter of user ID.
   *
   * @return user ID
   */
  public Long getUserid() {
    return userid;
  }

  /**
   * Getter of first player choice.
   *
   * @return first player choice
   */
  public SignEnum getPlayer1() {
    return player1;
  }

  /**
   * Getter of second player choice.
   *
   * @return second player choice
   */
  public SignEnum getPlayer2() {
    return player2;
  }

  /**
   * Getter of round result.
   *
   * @return round result
   */
  public StatusEnum getStatus() {
    return status;
  }

  /**
   * Getter of round delete flag.
   *
   * @return delete flag
   */
  public boolean isDeleted() {
    return read(() -> deleted);
  }

  /**
   * Setter of round delete flag.
   *
   * @param isDeleted delete flag
   */
  public void setDeleted(final boolean isDeleted) {
    write(() -> this.deleted = isDeleted);
  }

  /**
   * Returns a string representation of the object.
   *
   * @return string
   */
  @Override
  public String toString() {
    return MessageFormat.format(
        "RoundEntity'{'id={0}, userid={1}, player1={2}, player2={3}, "
            + "status={4}, deleted={5}'}'",
        getId(), userid, player1, player2, status, deleted);
  }

  /**
   * Entities comparator.
   *
   * @param o object to compare
   * @return compare result
   */
  @Override
  public int compareTo(final RoundEntity o) {
    Comparator<RoundEntity> comparator =
        Comparator.nullsFirst(Comparator.comparing(BaseEntity::getId));
    return comparator.compare(this, o);
  }
}
