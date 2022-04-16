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

package com.batyuta.challenge.lottoland.web;

import static org.junit.jupiter.api.Assertions.*;

import com.batyuta.challenge.lottoland.Application;
import com.batyuta.challenge.lottoland.converter.JsonConverter;
import com.batyuta.challenge.lottoland.vo.ErrorBody;
import com.batyuta.challenge.lottoland.vo.RestResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("REST Response Advice test cases")
public class RestResponseAdviseTest {

    public static final String HAL_JSON = "application/hal+json";
    @Autowired
    JsonConverter jsonConverter;
    @LocalServerPort
    private int port;

    @Test
    public void accessDeniedException() {
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;

        HttpHeaders headers = new HttpHeaders();
        headers.add("From", FromMdcFilter.setMdcFromProperty(null));
        headers.add(HttpHeaders.ACCEPT, HAL_JSON);
        headers.add(HttpHeaders.CONTENT_TYPE, HAL_JSON);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/rounds/generate",
                HttpMethod.POST, entity, String.class);

        assertEquals(expectedStatusCode, response.getStatusCode());
        RestResponse<ErrorBody> restResponse = jsonConverter.toObject(response.getBody(),
                new TypeReference<RestResponse<ErrorBody>>() {
                });
        assertEquals(restResponse.getStatus(), expectedStatusCode);
    }
}
