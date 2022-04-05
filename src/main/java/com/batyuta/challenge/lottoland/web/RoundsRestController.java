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

package com.batyuta.challenge.lottoland.web;

import com.batyuta.challenge.lottoland.annotation.LogEntry;
import com.batyuta.challenge.lottoland.model.RoundEntity;
import com.batyuta.challenge.lottoland.service.RoundEntityService;
import com.batyuta.challenge.lottoland.service.RoundLightweightService;
import com.batyuta.challenge.lottoland.vo.RoundVO;
import com.batyuta.challenge.lottoland.vo.StatisticsVO;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("Round REST Controller")
@RequestMapping("/api/rounds")
public class RoundsRestController extends AbstractRestController<RoundEntity, RoundVO> {

    public RoundsRestController(RoundEntityService service, RoundLightweightService lightweightService) {
        super(service, lightweightService);
    }

    @LogEntry
    @Override
    public List<EntityModel<RoundVO>> findAll(Integer page, Integer size, String sortString) {
        return super.findAll(page, size, sortString);
    }

    @LogEntry
    @RequestMapping(value = "/generate/{userId}", method = RequestMethod.POST)
    public void generate(@PathVariable Long userId) {
        ((RoundEntityService)service).generate(userId);
    }

    @LogEntry
    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
    public void clean(@PathVariable Long userId) {
        ((RoundEntityService)service).deleteByUserId(userId);
    }
    @LogEntry
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public EntityModel<StatisticsVO> statistics() {
        RoundEntityService roundEntityService = (RoundEntityService) this.service;
        long totalRounds = roundEntityService.getTotalRounds();
        long firstRounds = roundEntityService.getFirstRounds();
        long secondRounds = roundEntityService.getSecondRounds();
        long totalDraws = roundEntityService.getDraws();

        EntityModel<StatisticsVO> statisticsVO = EntityModel.of(
                new StatisticsVO(
                        totalRounds,
                        firstRounds,
                        secondRounds,
                        totalDraws
                )
        );
        return statisticsVO;
    }
}
