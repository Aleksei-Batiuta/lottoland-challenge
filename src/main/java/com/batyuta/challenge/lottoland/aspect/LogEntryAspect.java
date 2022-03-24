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

package com.batyuta.challenge.lottoland.aspect;

import com.batyuta.challenge.lottoland.annotation.LogEntry;
import com.batyuta.challenge.lottoland.vo.LogEntryData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Instant;

/**
 * AspectJ method call logging.
 */
@Aspect
@Component
public class LogEntryAspect {
    /**
     * JSON mapper.
     */
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    /**
     * Log Message to JSON serialization.
     *
     * @param message message
     * @param pretty  output JSON pretty format flag
     * @return String view of message
     */
    private static String toString(LogEntryData message, boolean pretty) {
        if (message != null) {
            try {
                if (pretty) {
                    return JSON_MAPPER
                            .writerWithDefaultPrettyPrinter()
                            .writeValueAsString(message);
                }
                return JSON_MAPPER
                        .writeValueAsString(message);
            } catch (JsonProcessingException e) {
                return message.toString();
            }
        }
        return null;
    }

    /**
     * Log message.
     *
     * @param logger  logger
     * @param level   logging level
     * @param message message
     */
    static void log(Logger logger, Level level, String message) {
        switch (level) {
            case DEBUG: {
                logger.debug(message);
                break;
            }
            case TRACE: {
                logger.trace(message);
                break;
            }
            case WARN: {
                logger.warn(message);
                break;
            }
            case ERROR: {
                logger.error(message);
                break;
            }
            default: {
                logger.info(message);
            }
        }
    }

    /**
     * AspectJ entry point.
     *
     * @param point point
     * @return result
     * @throws Throwable if exception was appeared
     */
    @Around("@annotation("
            + "com.batyuta.challenge.lottoland.annotation.LogEntry"
            + ")")
    public Object log(ProceedingJoinPoint point) throws Throwable {
        CodeSignature codeSignature =
                (CodeSignature) point.getSignature();
        MethodSignature methodSignature =
                (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        LogEntry annotation = method.getAnnotation(LogEntry.class);
        Logger logger = LoggerFactory.getLogger(method.getDeclaringClass());

        LogEntryData logEntryMessage =
                new LogEntryData(
                        method.getName(),
                        codeSignature.getParameterNames(),
                        point.getArgs(),
                        Instant.now(),
                        annotation.unit()
                );
        Object response = point.proceed();
        logEntryMessage.setResult(response);
        logEntryMessage.setEnd(Instant.now());
        log(
                logger,
                annotation.value(),
                toString(logEntryMessage, annotation.prettyFormatted())
        );
        return response;
    }
}
