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

package com.batyuta.challenge.lottoland.service;

import com.batyuta.challenge.lottoland.model.RoundEntity;
import com.batyuta.challenge.lottoland.vo.RoundVO;
import org.springframework.stereotype.Service;

/** Lightweight Service between {@link RoundEntity} and {@link RoundVO}. */
@Service
public final class RoundLightweightService
    implements LightweightService<RoundEntity, RoundVO> {

  @Override
  public RoundEntity toEntity(final RoundVO view) {
    if (view == null) {
      return null;
    }
    return new RoundEntity(view.getId(), view.getUserId(), view.getPlayer1(),
        view.getPlayer2(), view.getStatus());
  }

  @Override
  public RoundVO toView(final RoundEntity entity) {
    if (entity == null) {
      return null;
    }
    return new RoundVO(entity.getId(), entity.getUserid(), entity.getPlayer1(),
        entity.getPlayer2(), entity.getStatus());
  }
}
