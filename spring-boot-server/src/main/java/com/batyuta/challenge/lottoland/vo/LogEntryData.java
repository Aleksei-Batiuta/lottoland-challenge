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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Data;

/** The View Object class for Logger. */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "method", "args", "result", "duration" })
public class LogEntryData {

    /** Called method name. */
    private final String method;

    /** Called method arguments. */
    private final Map<String, Object> args;

    /** Called method start time. */
    @JsonIgnore
    private final Instant start;

    /** Called method duration time unit. */
    @JsonIgnore
    private final ChronoUnit unit;

    /** Called method result. */
    private Object result;

    /** Called method end time. */
    @JsonIgnore
    private Instant end;

    /**
     * Default constructor.
     *
     * @param methodName
     *            method name
     * @param parameters
     *            method parameter names
     * @param arguments
     *            method arguments
     * @param startTime
     *            start time
     * @param timeUnit
     *            duration time unit
     */
    public LogEntryData(final String methodName, final String[] parameters, final Object[] arguments,
            final Instant startTime, final ChronoUnit timeUnit) {
        this.method = methodName;
        this.args = toMap(parameters, arguments);
        this.start = startTime;
        this.unit = timeUnit;
    }

    /**
     * Converts to map.
     *
     * @param params
     *            method parameter names
     * @param args
     *            method parameter arguments
     *
     * @return map
     */
    private static Map<String, Object> toMap(final String[] params, final Object[] args) {
        Map<String, Object> values;
        if (Objects.nonNull(params) && Objects.nonNull(args) && params.length == args.length) {

            values = new HashMap<>(params.length);
            for (int i = 0; i < params.length; i++) {
                values.put(params[i], args[i]);
            }
        } else {
            values = new HashMap<>();
        }
        return values;
    }

    /**
     * Called method duration.
     *
     * @return duration time
     */
    @JsonProperty
    public String duration() {
        if (unit != null && start != null && end != null) {
            return String.format("%s %s", unit.between(start, end), unit.name().toLowerCase());
        }
        return null;
    }
}
