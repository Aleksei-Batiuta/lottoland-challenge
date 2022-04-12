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
import com.batyuta.challenge.lottoland.model.UserEntity;
import com.batyuta.challenge.lottoland.service.AbstractEntityService;
import com.batyuta.challenge.lottoland.service.LightweightService;
import com.batyuta.challenge.lottoland.vo.BaseVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Abstract REST Entity Controller.
 *
 * @param <T> Entity Type
 * @param <V> Entity VO Type
 */
public abstract class RestAbstractController<T extends BaseEntity<T>, V extends BaseVO> {

	/**
	 * Security HTTP Header.
	 */
	public static final String CSRF = "X-XSRF-TOKEN";

	/**
	 * Default page settings.
	 */
	public static final int DEFAULT_PAGE_NUMBER = 0;

	/**
	 * Default page settings.
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * Entity's service.
	 */
	private final AbstractEntityService<T> service;

	/**
	 * Entity's lightweight service.
	 */
	private final LightweightService<T, V> lightweightService;

	/**
	 * Default constructor.
	 * @param entityService entity service
	 * @param lightweight entity lightweight service
	 */
	public RestAbstractController(final AbstractEntityService<T> entityService,
			final LightweightService<T, V> lightweight) {
		this.service = entityService;
		this.lightweightService = lightweight;
	}

	/**
	 * Getter of entity's lightweight service.
	 * @return lightweight service
	 */
	public AbstractEntityService<T> getService() {
		return service;
	}

	/**
	 * Getter of entity's service.
	 * @return service
	 */
	public LightweightService<T, V> getLightweightService() {
		return lightweightService;
	}

	/**
	 * Find all entities.
	 * @param user optional entity's user
	 * @param page page number
	 * @param size page size
	 * @param sort sort type
	 * @return entities
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Page<V> findAll(final @RequestHeader(value = CSRF, required = false) UserEntity user,
			final @RequestParam(required = false) Integer page, final @RequestParam(required = false) Integer size,
			final @RequestParam(required = false) Sort sort) {
		Pageable pageable = buildPageable(page, size, sort);
		return lightweightService.getViews(service.findAll(pageable));
	}

	/**
	 * Build pageable.
	 * @param page page number
	 * @param size page size
	 * @param sort sort type
	 * @return page settings
	 */
	protected Pageable buildPageable(final Integer page, final Integer size, final Sort sort) {
		Pageable pageable = null;
		if (page != null) {
			if (size != null) {
				if (sort != null) {
					pageable = PageRequest.of(page, size, sort);
				}
				else {
					pageable = PageRequest.of(page, size);
				}
			}
		}
		else {
			if (size != null) {
				pageable = Pageable.ofSize(size);
			}
		}
		if (pageable == null) {
			pageable = Pageable.unpaged();
		}
		return pageable;
	}

	/**
	 * Gets entity by ID.
	 * @param id entity ID
	 * @return entity
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public EntityModel<V> findById(final @PathVariable Long id) {
		Optional<T> byId = service.findById(id);
		if (byId.isPresent()) {
			V view = lightweightService.toView(byId.get());
			return getEntityModel(view, Pageable.unpaged());

		}
		else {
			throw new DataException("error.data.entity.not.found", id);
		}
	}

	/**
	 * Creates a new entity.
	 * @param view entity
	 * @return a new entity
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public EntityModel<V> save(final @RequestBody V view) {
		T entity = lightweightService.toEntity(view);
		return getEntityModel(lightweightService.toView(service.save(entity)), Pageable.unpaged());
	}

	/**
	 * Update entity.
	 * @param id entity ID
	 * @param view entity
	 * @return updated entity
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public EntityModel<V> put(final @RequestParam Long id, final @RequestBody V view) {
		T entity = lightweightService.toEntity(view);
		entity.setId(id);
		return getEntityModel(lightweightService.toView(service.replace(entity)), Pageable.unpaged());
	}

	/**
	 * Delete entity.
	 * @param id entity ID
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(final @PathVariable Long id) {
		service.deleteById(id);
	}

	/**
	 * Replace entity.
	 * @param id entity ID
	 * @param view entity's view
	 * @return Replaced entity
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public EntityModel<V> patch(final @PathVariable Long id, final @RequestBody V view) {
		T entity = lightweightService.toEntity(view);
		entity.setId(id);
		return getEntityModel(lightweightService.toView(service.update(entity)), Pageable.unpaged());
	}

	/**
	 * Gets EntityModel for view.
	 * @param view view
	 * @param pageSettings page settings
	 * @return entity model
	 */
	protected EntityModel<V> getEntityModel(final V view, final Pageable pageSettings) {
		Long viewId = view.getId();
		EntityModel<V> model = EntityModel.of(view, linkTo(methodOn(this.getClass()).findById(viewId)).withSelfRel());
		Pageable pageable = pageSettings;
		if (pageable == null || pageable.isUnpaged()) {
			pageable = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
		}
		model.add(linkTo(methodOn(this.getClass()).findAll(null, pageable.getPageNumber(), pageable.getPageSize(),
				pageable.getSort())).withRel("all"));
		return model;
	}

}
