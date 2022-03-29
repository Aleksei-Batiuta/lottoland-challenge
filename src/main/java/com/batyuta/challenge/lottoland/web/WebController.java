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
import com.batyuta.challenge.lottoland.vo.PageVO;
import com.batyuta.challenge.lottoland.vo.RoundVO;
import com.batyuta.challenge.lottoland.vo.StatisticsVO;
import com.batyuta.challenge.lottoland.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;

/**
 * The main WEB controller.
 */
@Controller
@RequestMapping(WebController.CONTROLLER_PATH)
public class WebController {
    public static final String CONTROLLER_PATH = "/old";
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
     * Default constructor.
     *
     * @param service  application service
     * @param uService user lightweight service
     * @param rService round lightweight service
     */
    @Autowired
    public WebController(final ApplicationService service,
                         final UserLightweightService uService,
                         final RoundLightweightService rService) {
        this.applicationService = service;
        this.userService = uService;
        this.roundService = rService;
    }

    /**
     * Gets greetings.
     *
     * @param httpRequest HTTP Servlet Request
     * @return greetings model and view
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView game(final HttpServletRequest httpRequest) {
        UserVO userVO = getOrCreateUserVO(httpRequest);
        Collection<RoundVO> rounds = roundService.getViews(
                applicationService.getRoundsByUserId(userVO.getId())
        );

        HashMap<String, Object> map = new HashMap<>();
        map.put("page", new PageVO<>(
                "title.game",
                "game",
                rounds
        ));

        return new ModelAndView("main", map);
    }

    /**
     * new Round.
     *
     * @param httpRequest HTTP Servlet Request
     * @return game model and view
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newRound(final HttpServletRequest httpRequest) {
        UserVO userVO = getOrCreateUserVO(httpRequest);
        RoundEntity round = applicationService.newRoundByUserId(userVO.getId());
        return "redirect:"+CONTROLLER_PATH + "/";
    }

    /**
     * Requests to e new Round.
     *
     * @param httpRequest HTTP Servlet Request
     * @return game model and view
     */
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public String resetGame(final HttpServletRequest httpRequest) {
        UserVO userVO = getOrCreateUserVO(httpRequest);
        applicationService.deleteAllRoundsByUserId(userVO.getId());

        return "redirect:" + CONTROLLER_PATH + "/";
    }

    /**
     * Gets Statistics data.
     *
     * @param httpRequest HTTP Servlet Request
     * @return game model and view
     */
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ModelAndView statistics(final HttpServletRequest httpRequest) {
        UserVO userVO = getOrCreateUserVO(httpRequest);
        long totalRounds = applicationService.getTotalRounds();
        long firstRounds = applicationService.getFirstRounds();
        long secondRounds = applicationService.getSecondRounds();
        long totalDraws = applicationService.getDraws();

        HashMap<String, Object> map = new HashMap<>();
        map.put("page", new PageVO<>(
                "title.statistics",
                "statistics",
                new StatisticsVO(
                        totalRounds,
                        firstRounds,
                        secondRounds,
                        totalDraws
                )
        ));
        return new ModelAndView("main", map);
    }

    /**
     * User Authorization.
     *
     * @param httpRequest HTTP Servlet Request
     * @return current user VO
     */
    private UserVO getOrCreateUserVO(final HttpServletRequest httpRequest) {
        UserVO userVO = (UserVO) httpRequest.getSession().getAttribute("user");

        UserEntity userEntity = applicationService.getOrCreateUser(
                userService.toEntity(userVO)
        );
        userVO = userService.toView(userEntity);

        httpRequest.getSession().setAttribute("user", userVO);
        return userVO;
    }

    /**
     * Gets greetings.
     *
     * @return greetings model and view
     */
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView exception() {
        throw new ApplicationException(
                "error.unsupported.operation",
                "This is a test error page"
        );
    }
}
