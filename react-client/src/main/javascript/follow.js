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
module.exports = function follow(api, rootPath, relArray) {
    const root = api({
        method: 'GET',
        path: rootPath
    });
    return relArray.reduce(function (root, arrayItem) {
        const rel = typeof arrayItem === 'string' ? arrayItem : arrayItem.rel;
        return traverseNext(root, rel, arrayItem);
    }, root);

    function traverseNext(root, rel, arrayItem) {
        return root.then(function (response) {
            if (hasEmbeddedRel(response.entity, rel)) {
                return response.entity._embedded[rel];
            }
            if (!response.entity._links) {
                return [];
            }
            if (typeof arrayItem === 'string') {
                return api({
                    method: 'GET',
                    path: response.entity._links[rel].href
                });
            } else {
                return api({
                    method: 'GET',
                    path: response.entity._links[rel].href,
                    params: arrayItem.params
                });
            }
        });
    }

    function hasEmbeddedRel(entity, rel) {
        return entity._embedded && entity._embedded.hasOwnProperty(rel);
    }
};
