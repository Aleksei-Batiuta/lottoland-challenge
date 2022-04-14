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

export default class RestService {
  static instance = null;

  constructor() {
    this.findAllRounds = this.findAllRounds.bind(this);
    this.newRound = this.newRound.bind(this);
    this.cleanRounds = this.cleanRounds.bind(this);

    this.getStatistics = this.getStatistics.bind(this);

    this.doRequest = this.doRequest.bind(this);
  }

  static getInstance() {
    if (RestService.instance == null) {
      RestService.instance = new RestService();
    }
    return RestService.instance;
  }

  getCsrfToken() {
    return document.cookie.replace(/(?:^|.*;\s*)XSRF-TOKEN\s*=\s*([^;]*).*$|^.*$/, '$1');
  }

  newRound(processor, errorCallback) {
    this.doRequest(
      '/api/rounds/generate',
      'POST',
      { 'X-XSRF-TOKEN': this.getCsrfToken() },
      processor,
      errorCallback
    );
  }

  cleanRounds(processor, errorCallback) {
    this.doRequest(
      '/api/rounds/',
      'DELETE',
      { 'X-XSRF-TOKEN': this.getCsrfToken() },
      processor,
      errorCallback
    );
  }

  findAllRounds(page, size, sort, processor, errorCallback) {
    let path =
      '/api/rounds/?' +
      new URLSearchParams({
        page: page,
        size: size,
        sort: sort
      });
    this.doRequest(path, 'GET', { 'X-XSRF-TOKEN': this.getCsrfToken() }, processor, errorCallback);
  }

  getStatistics(processor) {
    this.doRequest('/api/rounds/statistics', 'GET', new Headers(), processor);
  }

  doRequest(path, method, headers, processor, errorCallback) {
    fetch(path, {
      method: method,
      headers: headers
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        if (data.status?.codeValue >= 400) {
          throw new Error(data.body.message);
        }
        processor(data.body);
      })
      .catch((err) => {
        console.log(err);
        if (errorCallback !== null && errorCallback !== undefined) {
          errorCallback(err);
        }
      });
  }
}
