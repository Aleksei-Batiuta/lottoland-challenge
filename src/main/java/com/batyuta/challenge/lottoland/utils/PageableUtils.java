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

package com.batyuta.challenge.lottoland.utils;

import org.springframework.data.domain.Pageable;

/**
 * Pageable utils.
 */
public final class PageableUtils {
    /**
     * Hide constructor.
     */
    private PageableUtils() {
    }

    /**
     * Fixes the page settings if page is not in list range.
     *
     * @param pageSetting page config
     * @param total       total elements in list
     * @return the first page if page config was not valid.
     */
    public static Pageable fixPageable(final Pageable pageSetting,
                                       final int total) {
        Pageable pageable = pageSetting;
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }
        if (!pageable.isUnpaged()) {
            if (total != 0 && pageable.getOffset() >= total) {
                pageable = pageable.first();
            }
        }
        return pageable;
    }
}
