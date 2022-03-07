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

/**
 * User VO.
 */
public class UserVO extends BaseVO {
    /**
     * User e-mail.
     */
    private final String email;

    /**
     * Default constructor.
     *
     * @param userId    user ID
     * @param userEmail e-mail address
     */
    public UserVO(final int userId, final String userEmail) {
        super(userId);
        this.email = userEmail;
    }

    /**
     * Getter of user e-mail.
     *
     * @return e-mail
     */
    public String getEmail() {
        return email;
    }
}
