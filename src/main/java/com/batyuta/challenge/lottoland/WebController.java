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

package com.batyuta.challenge.lottoland;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * The main WEB controller.
 */
@RestController
public class WebController {
    /**
     * Message template.
     */
    private static final String MESSAGE_TEMPLATE = "Hello, %s!";
    /**
     * Request counter.
     */
    private final AtomicLong counter = new AtomicLong();

    /**
     * The root path processor.
     *
     * @return response
     */
    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    /**
     * The greeting request.
     *
     * @param name name
     * @return greeting
     */
    @GetMapping("/greeting")
    public Greeting greeting(final
            @RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(
                counter.incrementAndGet(),
                String.format(MESSAGE_TEMPLATE, name)
        );
    }
}
