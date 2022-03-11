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
 * Base response HTTP page VO.
 *
 * @param <T> data type
 */
public class PageVO<T> {
    /**
     * Page title.
     */
    private final String title;
    /**
     * Subpage name.
     */
    private final String body;
    /**
     * Page custom data.
     */
    private final T data;

    /**
     * Default constructor.
     *
     * @param pageTitle page title
     * @param pageBody  subpage name
     * @param pageData  custom data
     */
    public PageVO(final String pageTitle,
                  final String pageBody,
                  final T pageData) {
        this.title = pageTitle;
        this.body = pageBody;
        this.data = pageData;
    }

    /**
     * Getter of page title.
     *
     * @return page title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter of subpage name.
     *
     * @return subpage name
     */
    public String getBody() {
        return body;
    }

    /**
     * Getter of custom page data.
     *
     * @return custom data
     */
    public T getData() {
        return data;
    }
}
