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

import com.batyuta.challenge.lottoland.model.UserEntity;
import com.batyuta.challenge.lottoland.vo.UserVO;
import org.springframework.stereotype.Service;

/** Lightweight Service between {@link UserEntity} and {@link UserVO}. */
@Service
public final class UserLightweightService implements LightweightService<UserEntity, UserVO> {

  @Override
  public UserEntity toEntity(final UserVO view) {
    if (view == null) {
      return null;
    }
    return new UserEntity(view.getId(), view.getEmail());
  }

  @Override
  public UserVO toView(final UserEntity entity) {
    if (entity == null) {
      return null;
    }
    return new UserVO(entity.getId(), entity.getName());
  }
}
