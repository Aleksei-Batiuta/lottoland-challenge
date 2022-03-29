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

package com.batyuta.challenge.lottoland.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresource.ITemplateResource;

import java.util.Locale;
import java.util.Map;

@Configuration
public class JspHtmlConfiguration {
//    @Autowired
//    WebApplicationContext webApplicationContext;
//
//    @Bean
//    public SpringResourceTemplateResolver thymeleafTemplateResolver(){
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver() {
//            @Override
//            protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration, String ownerTemplate, String template, String resourceName, String characterEncoding, Map<String, Object> templateResolutionAttributes) {
//                return super.computeTemplateResource(configuration, ownerTemplate, template, resourceName, characterEncoding, templateResolutionAttributes);
//            }
//        };
//        templateResolver.setApplicationContext(webApplicationContext);
//        templateResolver.setOrder(9);
//        templateResolver.setPrefix("/templates/");
//        templateResolver.setSuffix("");
//        return templateResolver;
//    }
//
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine springTemplateEngine= new SpringTemplateEngine();
//        springTemplateEngine.setTemplateResolver(thymeleafTemplateResolver());
//        springTemplateEngine.setEnableSpringELCompiler(true);
//        return springTemplateEngine;
//    }
//
//    @Bean
//    public ThymeleafViewResolver thymeleafViewResolver(){
//        final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver() {
//            @Override
//            public View resolveViewName(String viewName, Locale locale) throws Exception {
//                return super.resolveViewName(viewName, locale);
//            }
//        };
//        viewResolver.setViewNames(new String[] {"*.html"});
//        viewResolver.setExcludedViewNames(new String[] {"*.jsp"});
//        viewResolver.setTemplateEngine(templateEngine());
//        viewResolver.setCharacterEncoding("UTF-8");
//        return viewResolver;
//    }
//
//    @Bean
//    public InternalResourceViewResolver jspViewResolver(){
//        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setOrder(10);
//        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix("/WEB-INF/views/jsp/");
//        viewResolver.setSuffix("");
//        viewResolver.setViewNames("*.jsp");
//        return viewResolver;
//    }
}
