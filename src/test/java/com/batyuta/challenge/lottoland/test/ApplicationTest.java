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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for Application.
 *
 * @author Aleksei Batyuta aleksei.batiuta@gmail.com
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {
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
