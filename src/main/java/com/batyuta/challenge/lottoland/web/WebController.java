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

import com.batyuta.challenge.lottoland.exception.ApplicationException;
import com.batyuta.challenge.lottoland.model.RoundEntity;
import com.batyuta.challenge.lottoland.model.UserEntity;
import com.batyuta.challenge.lottoland.service.ApplicationService;
import com.batyuta.challenge.lottoland.service.RoundLightweightService;
import com.batyuta.challenge.lottoland.service.UserLightweightService;
import com.batyuta.challenge.lottoland.vo.RoundVO;
import com.batyuta.challenge.lottoland.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main WEB controller.
 */
@Controller
public class WebController {
    /**
     * Application Main Service.
     */
    private final ApplicationService applicationService;
    /**
     * User Service.
     */
    private final UserLightweightService userService;
    /**
     * Round Service.
     */
    private final RoundLightweightService roundService;
    /**
     * HTTP Request.
     */
    private final HttpServletRequest request;

    /**
     * Default constructor.
     *
     * @param service     application service
     * @param uService    user lightweight service
     * @param rService    round lightweight service
     * @param httpRequest HTTP Servlet Request
     */
    @Autowired
    public WebController(final ApplicationService service,
                         final UserLightweightService uService,
                         final RoundLightweightService rService,
                         final HttpServletRequest httpRequest) {
        this.applicationService = service;
        this.userService = uService;
        this.roundService = rService;
        this.request = httpRequest;
    }

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
            applicationService.saveUser(userService.toEntity(userVO));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /**
     * Gets list of users.
     *
     * @return user list model and view
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView users() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(
                "users",
                userService.getViews(
                        applicationService.getAllUsers()
                )
        );
        map.put("title", "title.users");
        map.put("body", "users");
        return new ModelAndView("main", map);
    }

    /**
     * Gets greetings.
     *
     * @return greetings model and view
     */
    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public ModelAndView game() {
        UserVO userVO = getOrCreateUserVO();

        return getGameModelAndView(userVO);
    }

    /**
     * new Round.
     *
     * @return game model and view
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView newRound() {
        UserVO userVO = getOrCreateUserVO();
        RoundEntity round = applicationService.newRoundByUserId(userVO.getId());

        return getGameModelAndView(userVO);
    }

    /**
     * new Round.
     *
     * @return game model and view
     */
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView resetGame() {
        UserVO userVO = getOrCreateUserVO();
        applicationService.deleteAllRoundsByUserId(userVO.getId());

        return getGameModelAndView(userVO);
    }

    private ModelAndView getGameModelAndView(final UserVO userVO) {
        Collection<RoundVO> rounds = roundService.getViews(
                applicationService.getRoundsByUserId(userVO.getId())
        );
        int totalRounds = applicationService.getTotalRounds();
        int firstRounds = applicationService.getFirstRounds();
        int secondRounds = applicationService.getSecondRounds();
        int totalDraws = applicationService.getDraws();

        HashMap<String, Object> map = new HashMap<>();
        map.put("title", "title.game");
        map.put("body", "game");
        map.put("round", rounds.size());
        map.put("rounds", rounds);
        map.put("totalrounds", totalRounds);
        map.put("firstrounds", firstRounds);
        map.put("secondrounds", secondRounds);
        map.put("totaldraws", totalDraws);
        return new ModelAndView("main", map);
    }

    private UserVO getOrCreateUserVO() {
        UserVO userVO = (UserVO) this.request.getSession().getAttribute("user");

        UserEntity userEntity = applicationService.getOrCreateUser(
                userService.toEntity(userVO)
        );
        userVO = userService.toView(userEntity);

        this.request.getSession().setAttribute("user", userVO);
        return userVO;
    }

    /**
     * Gets greetings.
     *
     * @return greetings model and view
     */
    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public ModelAndView exception() {
        throw new ApplicationException("error.unknown");
    }
}
