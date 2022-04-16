/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.batyuta.challenge.lottoland.aspect;

import com.batyuta.challenge.lottoland.annotation.LogEntry;
import com.batyuta.challenge.lottoland.converter.JsonConverter;
import com.batyuta.challenge.lottoland.vo.LogEntryData;
import java.lang.reflect.Method;
import java.time.Instant;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

/** AspectJ method call logging. */
@Aspect
@Component
public class LogEntryAspect {
  /** JSON data Converter service. */
  private final JsonConverter jsonConverter;

  /**
   * Default constructor.
   *
   * @param converter JSON data Converter service
   */
  public LogEntryAspect(JsonConverter converter) {
    this.jsonConverter = converter;
  }

  /**
   * Log message.
   *
   * @param logger logger
   * @param level logging level
   * @param message message
   */
  static void log(final Logger logger, final Level level,
      final String message) {
    switch (level) {
      case DEBUG:
        logger.debug(message);
        break;
      case TRACE:
        logger.trace(message);
        break;
      case WARN:
        logger.warn(message);
        break;
      case ERROR:
        logger.error(message);
        break;
      default:
        logger.info(message);
    }
  }

  /**
   * Log Message to JSON serialization.
   *
   * @param message message
   * @param pretty output JSON pretty format flag
   * @return String view of message
   */
  private String toString(final LogEntryData message, final boolean pretty) {
    return jsonConverter.toString(message, pretty);
  }

  /** AspectJ entry point. */
  @Pointcut("@annotation(com.batyuta.challenge.lottoland.annotation.LogEntry)")
  public void methodsToBeProfiled() {}

  /**
   * AspectJ around pointcut.
   *
   * @param point point
   * @return result
   * @throws Throwable if exception was appeared
   */
  @Around("methodsToBeProfiled()")
  public Object log(final ProceedingJoinPoint point) throws Throwable {
    CodeSignature codeSignature = (CodeSignature) point.getSignature();
    MethodSignature methodSignature = (MethodSignature) point.getSignature();
    Method method = methodSignature.getMethod();
    LogEntry annotation = method.getAnnotation(LogEntry.class);
    Logger logger = LoggerFactory.getLogger(method.getDeclaringClass());

    LogEntryData logEntryMessage =
        new LogEntryData(method.getName(), codeSignature.getParameterNames(),
            point.getArgs(), Instant.now(), annotation.unit());
    Object response;
    try {
      response = point.proceed();
      logEntryMessage.setResult(response);
    } catch (Throwable throwable) {
      logEntryMessage.setResult(throwable);
      throw throwable;
    } finally {
      logEntryMessage.setEnd(Instant.now());
      log(logger, annotation.value(),
          toString(logEntryMessage, annotation.prettyFormatted()));
    }
    return response;
  }
}
