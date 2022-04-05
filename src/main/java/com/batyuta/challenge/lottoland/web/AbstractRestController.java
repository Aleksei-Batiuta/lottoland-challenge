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

import com.batyuta.challenge.lottoland.exception.DataException;
import com.batyuta.challenge.lottoland.model.BaseEntity;
import com.batyuta.challenge.lottoland.service.AbstractEntityService;
import com.batyuta.challenge.lottoland.service.LightweightService;
import com.batyuta.challenge.lottoland.vo.BaseVO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public abstract class AbstractRestController<T extends BaseEntity<T>, V extends BaseVO> {
    protected final AbstractEntityService<T> service;
    protected final LightweightService<T, V> lightweightService;


    public AbstractRestController(AbstractEntityService<T> entityService, LightweightService<T, V> lightweightService) {
        this.service = entityService;
        this.lightweightService = lightweightService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<EntityModel<V>> findAll(@RequestParam(required = false) Integer page,
                                        @RequestParam(required = false) Integer size,
                                        @RequestParam(required = false) String sortString) {
        Sort sort = null;
        if (sortString != null) {
            String[] split = sortString.split(",");
            Sort.Direction direction = null;
            if (split.length > 0) {
                direction = Sort.Direction.valueOf(split[0].toUpperCase());
            }
            if (split.length > 1) {
                String[] fields = Arrays.copyOfRange(split, 1, split.length);
                sort = Sort.by(direction, fields);
            }
        }
        return findAll(page, size, sort);
    }

    protected List<EntityModel<V>> findAll(Integer page, Integer size, Sort sort) {
        Pageable pageable = null;
        if (page != null) {
            if (size != null) {
                if (sort != null) {
                    pageable = PageRequest.of(page, size, sort);
                } else {
                    pageable = PageRequest.of(page, size);
                }
            }
        } else {
            if (size != null) {
                pageable = Pageable.ofSize(size);
            }
        }
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }
        Pageable finalPageable = pageable;
        return lightweightService
                .getViews(service.findAll(pageable))
                .stream()
                .map(v->getEntityModel(v, finalPageable))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public EntityModel<V> findById(@PathVariable Long id) {
        Optional<T> byId = service.findById(id);
        if (byId.isPresent()) {
            V view = lightweightService.toView(byId.get());
            return getEntityModel(view, Pageable.unpaged());

        } else {
            throw new DataException("error.data.entity.not.found", id);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public EntityModel<V> save(@RequestBody V view) {
        T entity = lightweightService.toEntity(view);
        view = lightweightService.toView(service.save(entity));
        return getEntityModel(view, Pageable.unpaged());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public EntityModel<V> put(@RequestParam Long id,
                              @RequestBody V view) {
        T entity = lightweightService.toEntity(view);
        entity.setId(id);
        view = lightweightService.toView(service.replace(entity));
        return getEntityModel(view, Pageable.unpaged());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public EntityModel<V> patch(@PathVariable Long id, @RequestBody V view) {
        T entity = lightweightService.toEntity(view);
        entity.setId(id);
        view = lightweightService.toView(service.update(entity));
        return getEntityModel(view, Pageable.unpaged());
    }

    private EntityModel<V> getEntityModel(V view, Pageable pageable) {
        Long viewId = view.getId();
        EntityModel<V> model = EntityModel.of(
                view,
                linkTo(methodOn(this.getClass()).findById(viewId)).withSelfRel());
        if (pageable.isUnpaged()) {
            pageable = PageRequest.of(0, 10);
        }
        model.add(
                linkTo(methodOn(this.getClass()).findAll(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort())).withRel("all")
        );
        return model;
    }
}
