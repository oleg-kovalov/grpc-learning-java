# grpc-learning-java
This repository is an example of gRPC services interaction in Java.\
It is also a companion to [grpc-learning-rest-proxy](https://github.com/oleg-kovalov/grpc-learning-rest-proxy) project written in Go.\
Projects could be used together to demonstrate language interoperability.

Example scenarious :
* Go gRPC server, Java gRPC client
* Java gRPC server, Go gRPC-gateway, generic REST client

## Installation
Import project as gradle project to your IDE.\
Under the hood gradle plugin [google/protobuf-gradle-plugin](https://github.com/google/protobuf-gradle-plugin) is used for .proto generation.\
Please visit the link for more details.

## Usage
Generate Java classes from .proto file and compile the project
```bash
$ gradlew clean generateProto build
```
Start Java gRPC server
```java
user.server.Server.java
```
Start Java gRPC client 
```java
user.client.Client.java
```

## Language interoperability
In many scenarious client and server could be differently implemented.\
gRPC being platform agnostic by design allows such communication, using same .proto files across all participants as the single source of truth.

In order to try such scenario current project could be used together with [grpc-learning-rest-proxy](https://github.com/oleg-kovalov/grpc-learning-rest-proxy) project written in Go.

Start Golang gRPC server
```bash
$ go run user/server/server.go
```

Start Java gRPC client
```java
user.client.Client.java
```

## gRPC-gateway
gRPC-gateway is a project aimed to support REST communication in a gRPC-centralized application.\
Thus gRPC could be added to existing RESTful application with backward compatibility for existing REST clients.\
This is achieved via additional reverse-proxy server which translates a RESTful HTTP API into gRPC.\
For more information please visit [grpc-ecosystem/grpc-gateway](https://github.com/grpc-ecosystem/grpc-gateway) repository.\
![gRPC-gateway architecture](https://docs.google.com/drawings/d/12hp4CPqrNPFhattL_cIoJptFvlAqm5wLQ0ggqI5mkCg/pub?w=749&amp;h=370)

to try scenario with gRPC-gateway current project could be used together with [grpc-learning-rest-proxy](https://github.com/oleg-kovalov/grpc-learning-rest-proxy).

Start Java gRPC server
```java
user.server.Server.java
```
Start Golang gRPC-gateway
```bash
$ go run user/gateway/gateway.go
```

You can make calls using a generic REST client.\
Each call would be intercepted by gRPC-gateway and translated to gRPC call that would be processed on Java gPRC server.
