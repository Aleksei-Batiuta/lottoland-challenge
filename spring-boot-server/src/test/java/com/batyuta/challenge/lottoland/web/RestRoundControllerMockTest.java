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

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.batyuta.challenge.lottoland.Application;
import com.batyuta.challenge.lottoland.model.UserEntity;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

/** REST Message test cases. */
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Round REST test cases")
@AutoConfigureMockMvc
public class RestRoundControllerMockTest {

    /** Root path. */
    public static final String ROOT_PATH = "/api/rounds/";

    public static final String HAL_JSON = "application/hal+json";
    public static final String XSRF_TOKEN = "XSRF-TOKEN";
    public static final String X_XSRF_TOKEN = "X-XSRF-TOKEN";
    ThreadLocal<AtomicReference<String>> csrf = ThreadLocal.withInitial(() -> new AtomicReference<>(null));
    ThreadLocal<AtomicReference<String>> id = ThreadLocal.withInitial(() -> new AtomicReference<>(null));
    /** Mock Model View Controller. */
    @Autowired
    private MockMvc mockMvc;

    // @Autowired RestRoundController restRoundController;
    // @Autowired MessageSource messageSource;
    /**
     * Test data generator.
     *
     * @return test data
     */
    public static Stream<? extends Arguments> testFindAllData() {
        return Stream.of(Arguments.of(null, 0, 10, getOrder(Sort.by(Sort.Direction.DESC, "id")), null),
                Arguments.of(null, 1, 10, getOrder(Sort.by(Sort.Direction.DESC, "id")), null),
                Arguments.of(null, -1, 10, getOrder(Sort.by(Sort.Direction.DESC, "id")), null),
                Arguments.of(new UserEntity("test"), 0, 10, getOrder(Sort.by(Sort.Direction.DESC, "id")), null),
                Arguments.of(new UserEntity("test"), 0, 10, "", null));
    }

    /**
     * Test data generator.
     *
     * @return test data
     */
    public static Stream<? extends Arguments> testGenerateData() {
        return Stream.of(Arguments.of(null, false, HttpStatus.FORBIDDEN.value()),
                Arguments.of(new UserEntity("test"), true, HttpStatus.CREATED.value()),
                Arguments.of(new UserEntity("test"), false, HttpStatus.FORBIDDEN.value()));
    }

    /**
     * Test data generator.
     *
     * @return test data
     */
    public static Stream<? extends Arguments> testDeleteByUserNameData() {
        return Stream.of(Arguments.of(null, false, HttpStatus.FORBIDDEN.value()),
                Arguments.of(new UserEntity("test"), true, HttpStatus.ACCEPTED.value()),
                Arguments.of(new UserEntity("test"), false, HttpStatus.FORBIDDEN.value()));
    }

    private static String getOrder(Sort sort) {
        String order = "";
        if (sort == null) {
            return order;
        }
        Iterator<Sort.Order> iterator = sort.iterator();
        if (iterator.hasNext()) {
            order = iterator.next().isAscending() ? "asc" : "desc";
        }
        return order;
    }

    @BeforeEach
    public void beforeTestMethod() {
        // this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // adds test case id into logger
        id.get().set(FromMdcFilter.setMdcFromProperty(null));
        // resets CSRF value
        setCsrf(null);

        // this.mockMvc =
        // MockMvcBuilders.standaloneSetup(restRoundController)
        // .setControllerAdvice(new RestExceptionHandler(messageSource))
        // .addFilters(new FromMdcFilter())
        // .build();
    }

    /**
     * The test findAll .
     *
     * @param expected
     *            expected result
     *
     * @return request result
     */
    @ParameterizedTest(name = "FindAllData[{index}]: user={0}, page={1}, size={2}, sort={3}, expected={4}")
    @MethodSource("testFindAllData")
    public MvcResult testFindAllRequest(final UserEntity user, final Integer page, final Integer size, final Sort sort,
            final Object expected) {
        Map<String, String> params = buildParams(page, size, sort);
        try {
            MockHttpServletRequestBuilder requestBuilder;
            if (params.isEmpty()) {
                requestBuilder = get(ROOT_PATH);
            } else {
                requestBuilder = get(ROOT_PATH + buildQuery(params),
                        (Object[]) new ArrayList<>(params.values()).toArray(new String[0]));
            }
            setBaseHeaders(requestBuilder);
            if (user != null) {
                requestBuilder = requestBuilder.header(XSRF_TOKEN, user.getName());
            }
            MvcResult resultActions = this.mockMvc.perform(requestBuilder).andDo(log()).andExpect(status().isOk())
                    .andExpect(header().string(HttpHeaders.CONTENT_TYPE, HAL_JSON))
                    .andExpect(header().exists(HttpHeaders.SET_COOKIE)).andExpect(cookie().exists(XSRF_TOKEN))
                    .andReturn();
            setCsrf(resultActions);
            String actual = resultActions.getResponse().getContentAsString();
            if (expected != null) {
                assertEquals(String.format("Test case was failed: {params: %s}", params), expected, actual);
            }
            return resultActions;
        } catch (Exception actual) {
            assertNotNull(String.format("Expected result is null: {params: %s, exception: %s}", params, actual),
                    expected);
            assertEquals("Unexpected result!", expected.getClass(), actual.getClass());
        }
        return null;
    }

    private void setCsrf(MvcResult resultActions) {
        String csrfLocal = null;
        if (resultActions != null) {

            Cookie csrfCookie = resultActions.getResponse().getCookie(XSRF_TOKEN);
            if (csrfCookie != null) {
                csrfLocal = csrfCookie.getValue();
            }
        }
        this.csrf.get().set(csrfLocal);
    }

    private MockHttpServletRequestBuilder setBaseHeaders(MockHttpServletRequestBuilder requestBuilder) {
        requestBuilder.characterEncoding(StandardCharsets.UTF_8).header(HttpHeaders.ACCEPT, HAL_JSON);
        String csrfLocal = csrf.get().get();
        if (csrfLocal != null) {
            requestBuilder.header(X_XSRF_TOKEN, csrfLocal);
        }
        return requestBuilder;
    }

    /**
     * The test generate.
     *
     * @param expected
     *            expected result
     */
    @ParameterizedTest(name = "Generate[{index}]: user={0}, getRequestBefore={1}, expected={2}")
    @MethodSource("testGenerateData")
    public void testGenerate(final UserEntity user, final boolean sendGetRequestBefore, final Object expected) {
        try {
            try {
                MockHttpServletRequestBuilder requestBuilder = setBaseHeaders(post(ROOT_PATH + "generate"));
                requestBuilder = setCsrfHeader(user, sendGetRequestBefore, requestBuilder);
                MvcResult resultActions = this.mockMvc.perform(requestBuilder).andDo(log())
                        .andExpect(status().is((Integer) expected)).andReturn();
            } catch (Exception actual) {
                assertNotNull(String.format("Expected result is null: {exception = %s}", actual), expected);
                assertEquals("Unexpected result!", expected.getClass(), actual.getClass());
            }
        } catch (AssertionError e) {
            throw new AssertionError(id.get().get() + ": " + e.getMessage(), e);
        }
    }

    /**
     * The test delete by username.
     *
     * @param expected
     *            expected result
     */
    @ParameterizedTest(name = "DeleteByUserName[{index}]: user={0}, getRequestBefore={1}, expected={2}")
    @MethodSource("testDeleteByUserNameData")
    public void deleteByUserName(final UserEntity user, final boolean sendGetRequestBefore, final Object expected) {
        try {
            MockHttpServletRequestBuilder requestBuilder = setBaseHeaders(delete(ROOT_PATH));
            requestBuilder = setCsrfHeader(user, sendGetRequestBefore, requestBuilder);
            MvcResult resultActions = this.mockMvc.perform(requestBuilder).andDo(log())
                    .andExpect(status().is((Integer) expected)).andReturn();
        } catch (Exception actual) {
            assertNotNull(String.format("Expected result is null: {exception = %s}", actual), expected);
            assertEquals("Unexpected result!", expected.getClass(), actual.getClass());
        }
    }

    private MockHttpServletRequestBuilder setCsrfHeader(UserEntity user, boolean sendGetRequestBefore,
            MockHttpServletRequestBuilder requestBuilder) {
        if (user != null) {
            String csrf = user.getName() == null ? "" : user.getName();
            if (sendGetRequestBefore) {
                MvcResult findAllResult = testFindAllRequest(user, 0, 10, Sort.by(Sort.Direction.DESC, "id"), null);
                Cookie cookie = findAllResult.getResponse().getCookie(XSRF_TOKEN);
                if (cookie != null) {
                    csrf = cookie.getValue();
                    requestBuilder.cookie(new Cookie(XSRF_TOKEN, csrf));
                }
            }
            requestBuilder = requestBuilder.header(X_XSRF_TOKEN, csrf);
        }
        return requestBuilder;
    }

    /** The test statistics. */
    @Test
    @DisplayName("Statistics test case")
    public void statistics() {
        Object expected = null;
        try {
            MockHttpServletRequestBuilder requestBuilder = setBaseHeaders(get(ROOT_PATH + "statistics"));
            MvcResult resultActions = this.mockMvc.perform(requestBuilder).andDo(log()).andExpect(status().isOk())
                    .andExpect(header().string(HttpHeaders.CONTENT_TYPE, HAL_JSON))
                    // .andExpect(header().exists(HttpHeaders.SET_COOKIE))
                    // .andExpect(cookie().exists(XSRF_TOKEN))
                    .andReturn();
            String actual = resultActions.getResponse().getContentAsString();

            if (expected != null) {
                assertEquals(String.format("Test case was failed"), expected, actual);
            }
        } catch (Exception actual) {
            assertNotNull(String.format("Expected result is null: {exception = %s}", actual), expected);
            assertEquals("Unexpected result!", expected.getClass(), actual.getClass());
        }
    }

    private String buildQuery(Map<String, String> params) {
        return "?" + params.keySet().stream().map(name -> name + "={" + name + "}").collect(Collectors.joining("&"));
    }

    private Map<String, String> buildParams(Integer page, Integer size, Sort sort) {
        Map<String, String> result = new LinkedHashMap<>();
        addParam("page", page, result);
        addParam("size", size, result);
        addParam("sort", sort, result);
        return result;
    }

    private void addParam(String name, Object value, Map<String, String> result) {
        if (value != null) {
            result.put(name, toString(value));
        }
    }

    private String toString(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Sort) {
            Sort sort = (Sort) value;
            String order = getOrder(sort);
            return "" + order + getProperties(sort);
        } else {
            return value.toString();
        }
    }

    private String getProperties(Sort sort) {
        String properties = "";
        if (sort == null) {
            return properties;
        }
        Iterator<Sort.Order> iterator = sort.iterator();
        if (iterator.hasNext()) {
            properties = iterator.next().getProperty();
        }
        if (!properties.isEmpty()) {
            properties = "," + properties;
        }
        return properties;
    }
}
