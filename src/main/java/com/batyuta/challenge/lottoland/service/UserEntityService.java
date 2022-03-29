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
import com.batyuta.challenge.lottoland.repository.UserEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class UserEntityService extends AbstractEntityService<UserEntity> {
    public UserEntityService(UserEntityRepository repository) {
        super(repository);
    }

    @Override
    protected UserEntity update(UserEntity destination, UserEntity source) {
        // todo: it should be reviewed
        return source;
    }
}
