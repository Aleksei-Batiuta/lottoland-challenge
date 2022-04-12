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

import com.batyuta.challenge.lottoland.vo.ErrorBody;
import com.batyuta.challenge.lottoland.vo.RestResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Custom Error Controller.
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class RestErrorController implements ErrorController {

	/**
	 * Errors handling.
	 * @param request request
	 * @return error response
	 */
	@RequestMapping
	public RestResponse<ErrorBody> error(final HttpServletRequest request) {
		HttpStatus status = getStatus(request);
		if (status == HttpStatus.NO_CONTENT) {
			return RestResponse.status(new ErrorBody("No Content"), status);
		}
		Object message = request.getAttribute("message");
		return RestResponse.status(
				new ErrorBody(
						message != null ? message.toString()
								: request.getServletPath()
										+ (request.getQueryString() != null ? "?" + request.getQueryString() : "")),
				status);
	}

	private HttpStatus getStatus(final HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		try {
			return HttpStatus.valueOf(statusCode);
		}
		catch (Exception ex) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

}
