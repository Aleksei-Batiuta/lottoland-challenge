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
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.Comparator;

/**
 * Sort comparator implementation class.
 *
 * @param <T> Objects type class to compare
 */
//@Component("sortComparator")
public class SortComparator<T extends Comparable<? super T>>
        implements Comparator<T> {
    /**
     * Sort type.
     */
    private final Sort sort;
    /**
     * Property Value bean.
     */
    @Autowired
    private PropertyValueUtilsBean propertyBean;

    /**
     * Default constructor.
     *
     * @param sortType sort type
     */
    public SortComparator(final Sort sortType) {
        this.sort = sortType;
    }

    /**
     * Implementation of {@link Comparator#compare(Object, Object)}.
     *
     * @param o1 the first object
     * @param o2 the second object
     * @return compare result
     */
    @Override
    @LogEntry(Level.TRACE)
    public int compare(final T o1, final T o2) {
        return getSortComparator(sort).compare(o1, o2);
    }

    /**
     * Creates {@link Comparator} by sort type.
     *
     * @param orders sort type
     * @return comparator
     */
    private Comparator<T> getSortComparator(final Sort orders) {
        Comparator<T> comparator = null;
        if (orders != null) {
            for (Sort.Order order : orders) {
                Comparator<T> sortOrderComparator =
                        getSortOrderComparator(order);
                if (comparator == null) {
                    comparator = sortOrderComparator;
                } else {
                    comparator = comparator.thenComparing(sortOrderComparator);
                }
            }
        }
        if (comparator == null) {
            comparator = Comparator.naturalOrder();
        }
        return comparator;
    }

    /**
     * Creates {@link Comparator} by order type.
     *
     * @param order order type
     * @param <P>   compared field type class
     * @return comparator
     */
    private <P extends Comparable<? super P>> Comparator<T>
    getSortOrderComparator(final Sort.Order order) {
        Comparator<P> orderComparator;
        if (order.isDescending()) {
            orderComparator = Comparator.reverseOrder();
        } else {
            orderComparator = Comparator.naturalOrder();
        }

        Comparator<T> comparator = Comparator.comparing(
                o -> propertyBean.getProperty(order, o),
                orderComparator
        );

        Comparator<T> nullHandlingComparator;
        switch (order.getNullHandling()) {
            case NULLS_FIRST:
                nullHandlingComparator = Comparator.nullsFirst(comparator);
                break;
            case NULLS_LAST:
                nullHandlingComparator = Comparator.nullsLast(comparator);
                break;
            default:
                nullHandlingComparator = Comparator.nullsFirst(comparator);
        }
        return nullHandlingComparator;
    }
}
