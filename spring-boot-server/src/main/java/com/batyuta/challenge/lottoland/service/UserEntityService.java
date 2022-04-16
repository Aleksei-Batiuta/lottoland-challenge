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

import com.batyuta.challenge.lottoland.exception.DataException;
import com.batyuta.challenge.lottoland.model.UserEntity;
import com.batyuta.challenge.lottoland.repository.UserEntityRepository;
import java.util.Objects;
import org.springframework.stereotype.Service;

/** User's Entity Service. */
@Service
public class UserEntityService extends AbstractEntityService<UserEntity> {

    /** User Entity repository. */
    private final UserEntityRepository userEntityRepository;

    /**
     * Default constructor.
     *
     * @param repository
     *            User Entity Repository
     */
    public UserEntityService(final UserEntityRepository repository) {
        super(repository);
        userEntityRepository = repository;
    }

    /**
     * Update implementation.
     *
     * @param destination
     *            destination entity
     * @param source
     *            source entity
     *
     * @return updated entity
     */
    @Override
    protected UserEntity updateImpl(final UserEntity destination, final UserEntity source) {
        return source;
    }

    /**
     * Creates a new or gets existed {@link UserEntity} from repository.
     *
     * @param user
     *            user
     *
     * @return new or existed user
     */
    public UserEntity findByUserNameOrNew(final String user) {
        UserEntity userEntity;
        try {
            userEntity = userEntityRepository.findByName(user);
        } catch (DataException e) {
            if (Objects.requireNonNull(e.getMessage()).contains("error.data.entity.not.found")) {
                userEntity = save(new UserEntity(user));
            } else {
                throw e;
            }
        }
        return userEntity;
    }
}
