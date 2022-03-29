/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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
'use strict';
import {CreateDialog} from "./createDialog";
import {RoundList} from "./roundList";

const React = require('react');
const ReactDOM = require('react-dom');
const when = require('when');
const client = require('./client');
const follow = require('./follow'); // function to hop multiple links by "rel"
// const stompClient = require('./websocket-listener');
const root = '/api';

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            rounds: [], attributes: [], page: 1, pageSize: 2, links: {}
        };
        this.updatePageSize = this.updatePageSize.bind(this);
        this.onCreate = this.onCreate.bind(this);
        this.onUpdate = this.onUpdate.bind(this);
        this.onDelete = this.onDelete.bind(this);
        this.onNavigate = this.onNavigate.bind(this);
        this.refreshCurrentPage = this.refreshCurrentPage.bind(this);
        this.refreshAndGoToLastPage = this.refreshAndGoToLastPage.bind(this);
    }

    loadFromServer(pageSize) {
        follow(client, root, [
            {rel: 'rounds', params: {size: pageSize}}]
        ).then(roundCollection => {
            return client({
                method: 'GET',
                path: roundCollection.entity._links.profile.href,
                headers: {'Accept': 'application/schema+json'}
            }).then(schema => {
                /**
                 * Filter unneeded JSON Schema properties, like uri references and
                 * subtypes ($ref).
                 */
                Object.keys(schema.entity.properties).forEach(function (property) {
                    if (schema.entity.properties[property].hasOwnProperty('format') &&
                        schema.entity.properties[property].format === 'uri') {
                        delete schema.entity.properties[property];
                    } else if (schema.entity.properties[property].hasOwnProperty('$ref')) {
                        delete schema.entity.properties[property];
                    }
                });
                this.schema = schema.entity;
                this.links = roundCollection.entity._links;
                return roundCollection;
            });
        }).then(roundCollection => {
            this.page = roundCollection.entity.page;
            return roundCollection.entity._embedded.rounds.map(employee =>
                client({
                    method: 'GET',
                    path: employee._links.self.href
                })
            );
        }).then(roundPromises => {
            return when.all(roundPromises);
        }).done(rounds => {
            this.setState({
                page: this.page,
                employees: rounds,
                attributes: Object.keys(this.schema.properties),
                pageSize: pageSize,
                links: this.links
            });
        });
    }

    onCreate(newRounds) {
        follow(client, root, ['rounds']).done(response => {
            client({
                method: 'POST',
                path: response.entity._links.self.href,
                entity: newRounds,
                headers: {'Content-Type': 'application/json'}
            })
        })
    }

    onUpdate(round, updatedRound) {
        if (round.entity.manager.name === this.state.loggedInManager) {
            updatedRound["manager"] = round.entity.manager;
            client({
                method: 'PUT',
                path: round.entity._links.self.href,
                entity: updatedRound,
                headers: {
                    'Content-Type': 'application/json',
                    'If-Match': round.headers.Etag
                }
            }).done(response => {
                /* Let the websocket handler update the state */
            }, response => {
                if (response.status.code === 403) {
                    alert('ACCESS DENIED: You are not authorized to update ' +
                        round.entity._links.self.href);
                }
                if (response.status.code === 412) {
                    alert('DENIED: Unable to update ' + round.entity._links.self.href +
                        '. Your copy is stale.');
                }
            });
        } else {
            alert("You are not authorized to update");
        }
    }

    onDelete(round) {
        client({method: 'DELETE', path: round.entity._links.self.href}
        ).done(response => {/* let the websocket handle updating the UI */
            },
            response => {
                if (response.status.code === 403) {
                    alert('ACCESS DENIED: You are not authorized to delete ' +
                        round.entity._links.self.href);
                }
            });
    }

    onNavigate(navUri) {
        client({
            method: 'GET',
            path: navUri
        }).then(roundCollection => {
            this.links = roundCollection.entity._links;
            this.page = roundCollection.entity.page;
            return roundCollection.entity._embedded.employees.map(round =>
                client({
                    method: 'GET',
                    path: round._links.self.href
                })
            );
        }).then(roundPromises => {
            return when.all(roundPromises);
        }).done(rounds => {
            this.setState({
                page: this.page,
                rounds: rounds,
                attributes: Object.keys(this.schema.properties),
                pageSize: this.state.pageSize,
                links: this.links
            });
        });
    }

    updatePageSize(pageSize) {
        if (pageSize !== this.state.pageSize) {
            this.loadFromServer(pageSize);
        }
    }

    refreshAndGoToLastPage(message) {
        follow(client, root, [{
            rel: 'rounds',
            params: {size: this.state.pageSize}
        }]).done(response => {
            if (response.entity._links.last !== undefined) {
                this.onNavigate(response.entity._links.last.href);
            } else {
                this.onNavigate(response.entity._links.self.href);
            }
        })
    }

    refreshCurrentPage(message) {
        follow(client, root, [{
            rel: 'rounds',
            params: {
                size: this.state.pageSize,
                page: this.state.page.number
            }
        }]).then(roundCollection => {
            this.links = roundCollection.entity._links;
            this.page = roundCollection.entity.page;
            return roundCollection.entity._embedded.employees.map(round => {
                return client({
                    method: 'GET',
                    path: round._links.self.href
                })
            });
        }).then(roundPromises => {
            return when.all(roundPromises);
        }).then(rounds => {
            this.setState({
                page: this.page,
                rounds: rounds,
                attributes: Object.keys(this.schema.properties),
                pageSize: this.state.pageSize,
                links: this.links
            });
        });
    }

    componentDidMount() {
        this.loadFromServer(this.state.pageSize);
        // stompClient.register([
        //     {route: '/topic/newEmployee', callback: this.refreshAndGoToLastPage},
        //     {route: '/topic/updateEmployee', callback: this.refreshCurrentPage},
        //     {route: '/topic/deleteEmployee', callback: this.refreshCurrentPage}
        // ]);
    }

    render() {
        return (
            <div>
                <CreateDialog attributes={this.state.attributes}
                              onCreate={this.onCreate}/>
                <RoundList page={this.state.page}
                              rounds={this.state.rounds}
                              links={this.state.links}
                              pageSize={this.state.pageSize}
                              attributes={this.state.attributes}
                              onNavigate={this.onNavigate}
                              onUpdate={this.onUpdate}
                              onDelete={this.onDelete}
                              updatePageSize={this.updatePageSize}
                              loggedInManager={this.state.loggedInManager}/>
            </div>
        )
    }
}

ReactDOM.render(
    <App loggedInManager={document.getElementById('managername').innerHTML}/>,
    document.getElementById('react')
)
