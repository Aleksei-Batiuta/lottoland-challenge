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

import com.batyuta.challenge.lottoland.converter.HttpStatusSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Rest response wrapper.
 *
 * @param <T> body type
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "headers", "body", "timestamp"})
@Data
public final class RestResponse<T> {
    /**
     * Response timestamp.
     */
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss"
    )
    private final LocalDateTime timestamp;
    /**
     * Response body.
     */
    private final T body;
    /**
     * Response HTTP headers.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final HttpHeaders headers;
    /**
     * Response HTTP status.
     */
    @JsonSerialize(using = HttpStatusSerializer.class)
    private final HttpStatus status;

    /**
     * Default constructor.
     *
     * @param tBody      response body
     * @param httpStatus response HTTP status
     */
    private RestResponse(final T tBody,
                         final HttpStatus httpStatus) {
        this(tBody, null, httpStatus);
    }

    /**
     * Default constructor.
     *
     * @param tBody       response body
     * @param httpHeaders response HTTP headers
     * @param httpStatus  response HTTP status
     */
    private RestResponse(final T tBody,
                         final HttpHeaders httpHeaders,
                         final HttpStatus httpStatus) {
        this.body = tBody;
        this.headers = httpHeaders;
        this.status = httpStatus;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Build response.
     *
     * @param body   response body.
     * @param status HTTP response status
     * @param <T>    body type
     * @return response wrapper
     */
    public static <T> RestResponse<T> status(final T body,
                                             final HttpStatus status) {
        return new RestResponse<>(body, status);
    }

    /**
     * Build {@link HttpStatus#OK} response.
     *
     * @param body response body
     * @param <T>  body type
     * @return response wrapper
     */
    public static <T> RestResponse<T> ok(final T body) {
        return new RestResponse<>(body, HttpStatus.OK);
    }

    /**
     * Build {@link HttpStatus#INTERNAL_SERVER_ERROR} response.
     *
     * @param ex exception
     * @return response wrapper
     */
    public static RestResponse<ErrorBody> internalServerError(
            final Throwable ex) {
        return new RestResponse<>(
                new ErrorBody(ex),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Build {@link HttpStatus#FORBIDDEN} response.
     *
     * @param ex exception
     * @return response wrapper
     */
    public static RestResponse<ErrorBody> forbidden(
            final Throwable ex) {
        return new RestResponse<>(new ErrorBody(ex), HttpStatus.FORBIDDEN);
    }

    /**
     * Build {@link HttpStatus#BAD_REQUEST} response.
     *
     * @param message message text.
     * @param ex      exception
     * @return response wrapper
     */
    public static RestResponse<ErrorBody> badRequest(
            final String message, final Throwable ex) {
        return new RestResponse<>(
                new ErrorBody(message, ex),
                HttpStatus.BAD_REQUEST
        );
    }
}
