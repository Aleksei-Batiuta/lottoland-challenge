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

package com.batyuta.challenge.lottoland.dao;

import com.batyuta.challenge.lottoland.model.RoundEntity;
import com.batyuta.challenge.lottoland.model.UserEntity;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Data repository class.
 */
@Component
@Getter
public class DataRepository {
    /**
     * This is a user table index.
     */
    private final AtomicInteger userIndex = new AtomicInteger(0);
    /**
     * This is a round table index.
     */
    private final AtomicInteger roundIndex = new AtomicInteger(0);
    /**
     * This is a user table.
     */
    private final Collection<UserEntity> users =
            new LinkedBlockingQueue<UserEntity>() {
                @Override
                public boolean add(UserEntity o) {
                    o.setId(userIndex.incrementAndGet());
                    return super.add(o);
                }
            };

    /**
     * This is a round table.
     */
    private final Collection<RoundEntity> rounds =
            new LinkedBlockingQueue<RoundEntity>() {
                @Override
                public boolean add(RoundEntity o) {
                    o.setId(roundIndex.incrementAndGet());
                    return super.add(o);
                }
            };
}
