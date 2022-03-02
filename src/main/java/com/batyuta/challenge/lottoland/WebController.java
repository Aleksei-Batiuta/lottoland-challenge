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

import com.batyuta.challenge.lottoland.vo.UserVO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main WEB controller.
 */
@Controller
public class WebController {

    /**
     * This is a user db.
     */
    private List<UserVO> userVOS = Arrays.asList(
            new UserVO("ana@yahoo.com"),
            new UserVO("bob@yahoo.com"),
            new UserVO("john@yahoo.com"),
            new UserVO("mary@yahoo.com")
    );

    /**
     * Saves user.
     *
     * @param userVO user VO
     * @param result result
     * @param model  model
     * @return status
     */
    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<Object> saveUser(
            final @Valid UserVO userVO,
            final BindingResult result,
            final Model model
    ) {
        if (result.hasErrors()) {
            final List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.OK);
        } else {
            if (userVOS.stream()
                    .anyMatch(it -> userVO.getEmail().equals(it.getEmail()))) {
                return new ResponseEntity<>(
                        Collections.singletonList("Email already exists!"),
                        HttpStatus.CONFLICT
                );
            } else {
                userVOS.add(userVO);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
    }

    /**
     * Gets list of users.
     * @return user list model and view
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView users() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("users", userVOS);
        map.put("title", "User List");
        map.put("body", "users");
        return new ModelAndView("main", map);
    }

    /**
     * Gets greetings.
     * @return greetings model and view
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView welcome() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", "Welcome");
        map.put("body", "welcome");
        return new ModelAndView("main", map);
    }
}
