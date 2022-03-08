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

package com.batyuta.challenge.lottoland.test;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for Application.
 *
 * @author Aleksei Batyuta aleksei.batiuta@gmail.com
 */
@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@DisplayName("Simple HTTP request test cases")
public class ApplicationTest {

    /**
     * Test Server port.
     */
//    @LocalServerPort
    private static final int SERVER_PORT = 8080;
    /**
     * Mock Model View Controller.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Test access to main game page.
     *
     * @throws Exception if error appears
     */
    @Test
    public void root() throws Exception {
        this.mockMvc
                .perform(
                        get("/")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Test root JSP page.
     *
     * @throws IOException if error appears
     */
    @Test
    public void rootJsp() throws IOException {

        // Given
        HttpUriRequest request =
                new HttpGet("http://localhost:" + SERVER_PORT + "/");

        // When
        HttpResponse httpResponse = HttpClientBuilder.create()
                .build()
                .execute(request);

        // Then
        assertThat(
                "Invalid HTTP response code",
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK)
        );
    }

    /**
     * Test access to statistics page.
     *
     * @throws Exception if error appears
     */
    @Test
    public void statistics() throws Exception {
        this.mockMvc
                .perform(
                        get("/statistics")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Test statistics JSP page.
     *
     * @throws IOException if error appears
     */
    @Test
    public void statisticsJsp() throws IOException {

        // Given
        HttpUriRequest request =
                new HttpGet("http://localhost:" + SERVER_PORT + "/statistics");

        // When
        HttpResponse httpResponse = HttpClientBuilder.create()
                .build()
                .execute(request);

        // Then
        assertThat(
                "Invalid HTTP response code",
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK)
        );
    }

    /**
     * Test access to new round page.
     *
     * @throws Exception if error appears
     */
    @Test
    public void newRound() throws Exception {
        this.mockMvc
                .perform(
                        post("/new")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    /**
     * Test new JSP page.
     *
     * @throws IOException if error appears
     */
    @Test
    public void newJsp() throws IOException {

        // Given
        HttpUriRequest request =
                new HttpPost("http://localhost:" + SERVER_PORT + "/new");

        // When
        HttpResponse httpResponse = HttpClientBuilder.create()
                .build()
                .execute(request);

        // Then
        assertThat(
                "Invalid HTTP response code",
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_MOVED_TEMPORARILY)
        );
    }
    /**
     * Test access to reset game page.
     *
     * @throws Exception if error appears
     */
    @Test
    public void resetGame() throws Exception {
        this.mockMvc
                .perform(
                        post("/reset")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    /**
     * Test reset JSP page.
     *
     * @throws IOException if error appears
     */
    @Test
    public void resetJsp() throws IOException {

        // Given
        HttpUriRequest request =
                new HttpPost("http://localhost:" + SERVER_PORT + "/reset");

        // When
        HttpResponse httpResponse = HttpClientBuilder.create()
                .build()
                .execute(request);

        // Then
        assertThat(
                "Invalid HTTP response code",
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_MOVED_TEMPORARILY)
        );
    }
    /**
     * Test error page.
     *
     * @throws Exception if error appears
     */
    @Test
    public void error() throws Exception {
        this.mockMvc
                .perform(
                        get("/error")
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
    /**
     * Test error JSP page.
     *
     * @throws IOException if error appears
     */
    @Test
    public void errorJsp() throws IOException {

        // Given
        HttpUriRequest request =
                new HttpGet("http://localhost:" + SERVER_PORT + "/error");

        // When
        HttpResponse httpResponse = HttpClientBuilder.create()
                .build()
                .execute(request);

        // Then
        assertThat(
                "Invalid HTTP response code",
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_CONFLICT)
        );
    }
    /**
     * Test main.css.
     *
     * @throws Exception if error appears
     */
    @Test
    public void cssTest() throws Exception {
        this.mockMvc
                .perform(
                        get("/main.css")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Test lottoland.png.
     *
     * @throws Exception if error appears
     */
    @Test
    public void logoTest() throws Exception {

        this.mockMvc
                .perform(
                        get("/lottoland.png")
                )
                .andExpect(status().isOk());
    }

    /**
     * Test favicon.ico.
     *
     * @throws Exception if error appears
     */
    @Test
    public void faviconTest() throws Exception {

        this.mockMvc
                .perform(
                        get("/favicon.ico")
                )
                .andExpect(status().isOk());
    }

    /**
     * Test gcharts.js.
     *
     * @throws Exception if error appears
     */
    @Test
    public void googleChartsJsTest() throws Exception {

        this.mockMvc
                .perform(
                        get("/js/gcharts.js")
                )
                .andExpect(status().isOk());
    }
}
