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

package com.batyuta.challenge.lottoland.repository;

import com.batyuta.challenge.lottoland.annotation.LogEntry;
import com.batyuta.challenge.lottoland.exception.DataException;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/** Gets object property value. */
@Service
public class PropertyValueUtilsBean {

    /** Property utils. */
    private final org.apache.commons.beanutils.PropertyUtilsBean propertyUtils = BeanUtilsBean.getInstance()
            .getPropertyUtils();

    /**
     * Gets property value.
     *
     * @param order
     *            property declaration in order
     * @param o
     *            object
     * @param <P>
     *            object type class
     *
     * @return property value
     */
    @LogEntry
    public <P extends Comparable<? super P>> P getProperty(final Sort.Order order, final Object o) {
        try {
            return (P) propertyUtils.getProperty(o, order.getProperty());
        } catch (Exception e) {
            throw new DataException("error.unsupported.operation", e, e.getMessage());
        }
    }
}
