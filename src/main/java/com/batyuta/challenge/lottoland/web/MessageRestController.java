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

import com.batyuta.challenge.lottoland.model.UserEntity;
import com.batyuta.challenge.lottoland.service.MessageResolverService;
import com.batyuta.challenge.lottoland.service.UserEntityService;
import com.batyuta.challenge.lottoland.service.UserLightweightService;
import com.batyuta.challenge.lottoland.vo.UserVO;
import org.springframework.hateoas.EntityModel;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;
import java.util.Map;

@RestController("Message REST Controller")
@RequestMapping("/api/messages")
public class MessageRestController {
    private MessageResolverService service;

    public MessageRestController(MessageResolverService service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public EntityModel<Map<String, String>> getLanguageMapJson(
            @RequestParam(name = "lang",required = false) String lang, Locale locale) {
        if (StringUtils.hasText(lang)) {
            locale = Locale.forLanguageTag(lang);
        }
        return EntityModel.of(service.getMessages(locale));
    }
}
