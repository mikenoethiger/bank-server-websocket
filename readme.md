[protocol](https://github.com/mikenoethiger/bank-server-socket#protocol) | [bank-client](https://github.com/mikenoethiger/bank-client) | [bank-server-socket](https://github.com/mikenoethiger/bank-server-socket) | [bank-server-websocket](https://github.com/mikenoethiger/bank-server-websocket) | [bank-server-graphql](https://github.com/mikenoethiger/bank-server-graphql) | [bank-server-rabbitmq](https://github.com/mikenoethiger/bank-server-rabbitmq)

# About

Websocket implementation of the bank server backend using [Tyrus](https://tyrus-project.github.io/) implementation of [JSR 356 - Java API for WebSocket](http://java.net/projects/websocket-spec).The client counterpart can be found [here](https://github.com/mikenoethiger/bank-client/tree/master/src/main/java/bank/websocket).

# Run

From your IDE, run `bank.Server` as a java application.

Or with gradle `gradle run`

Or with docker `docker run --rm -p 5003:5003 mikenoethiger/bank-server-websocket`

> By default the websocket endpoint is available on `localhost:5003/bank`, this can be changed in the `Server` class (constants at the top.)
