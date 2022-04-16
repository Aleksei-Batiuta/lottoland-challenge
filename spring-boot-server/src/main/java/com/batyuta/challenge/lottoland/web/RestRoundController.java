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

package com.batyuta.challenge.lottoland.web;

import com.batyuta.challenge.lottoland.annotation.ApplyResponseBinding;
import com.batyuta.challenge.lottoland.annotation.LogEntry;
import com.batyuta.challenge.lottoland.model.RoundEntity;
import com.batyuta.challenge.lottoland.model.UserEntity;
import com.batyuta.challenge.lottoland.service.RoundEntityService;
import com.batyuta.challenge.lottoland.service.RoundLightweightService;
import com.batyuta.challenge.lottoland.utils.PageableUtils;
import com.batyuta.challenge.lottoland.vo.RoundVO;
import com.batyuta.challenge.lottoland.vo.StatisticsVO;
import java.util.Collections;
import org.slf4j.event.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST Round Controller. */
@RestController("REST Round Controller")
@RequestMapping("/api/rounds")
@ApplyResponseBinding
public class RestRoundController
    extends AbstractController<RoundEntity, RoundVO> {

  /** Round Service. */
  private final RoundEntityService roundService;

  /**
   * Default constructor.
   *
   * @param entityService round service
   * @param lightweightService round lightweight service
   */
  public RestRoundController(final RoundEntityService entityService,
      final RoundLightweightService lightweightService) {
    super(entityService, lightweightService);
    this.roundService = entityService;
  }

  /**
   * Find all active user's rounds.
   *
   * @param user user
   * @param page page number
   * @param size page size
   * @param sort sort type
   * @return rounds
   */
  @Override
  @ResponseStatus(HttpStatus.OK)
  public Page<RoundVO> findAll(
      final @RequestHeader(value = CSRF, required = false) UserEntity user,
      final @RequestParam(required = false) Integer page,
      final @RequestParam(required = false) Integer size,
      final @RequestParam(required = false) Sort sort) {
    Pageable pageable = buildPageable(page, size, sort);
    if (user == null) {
      return new PageImpl<>(Collections.emptyList(),
          PageableUtils.fixPageable(pageable, 0), 0L);
    }
    Page<RoundEntity> allByUserId =
        roundService.findAllByUserId(user.getId(), pageable);
    return getLightweightService().getViews(allByUserId);
  }

  /**
   * Creates a new round.
   *
   * @param user round's user
   * @return a new user's round
   */
  @RequestMapping(value = "/generate", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  @LogEntry(Level.INFO)
  public RoundVO generate(final @RequestHeader(value = CSRF) UserEntity user) {
    RoundEntity roundEntity = roundService.generate(user.getId());
    ServletUriComponentsBuilder builder =
        ServletUriComponentsBuilder.fromCurrentRequestUri();
    builder.path("/api/rounds/" + roundEntity.getId());
    return getLightweightService().toView(roundEntity);
  }

  /**
   * Clean user rounds.
   *
   * @param user round's user
   * @return count of deleted user's rounds
   */
  @RequestMapping(value = "/", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Long deleteByUserName(
      final @RequestHeader(value = CSRF) UserEntity user) {
    return roundService.deleteByUserId(user.getId());
  }

  /**
   * Statistics getter.
   *
   * @return statistics data
   */
  @RequestMapping(value = "/statistics", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public StatisticsVO statistics() {
    long totalRounds = roundService.getTotalRounds();
    long firstRounds = roundService.getFirstRounds();
    long secondRounds = roundService.getSecondRounds();
    long totalDraws = roundService.getDraws();

    return new StatisticsVO(totalRounds, firstRounds, secondRounds, totalDraws);
  }
}
