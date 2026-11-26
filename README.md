# Social messaging microservices

A Java 17 / Spring Boot project with separate services for users and messages. It uses Spring Data JPA and MySQL, with GitHub OAuth for sign-in.

## Run locally

1. Install JDK 17 and MySQL, then clone this repository. Each service includes a Maven wrapper.
2. Create the `userdb` and `messagedb` databases. In each service's `src/main/resources/application.properties`, set your local database URL and credentials. The checked-in defaults use ports 3306 and 3307 respectively; change them if both databases share one MySQL server.
3. Configure your own GitHub OAuth client ID and secret for `usermanagementservice`, with callback `http://localhost:8081/login/oauth2/code/github`. Keep local credentials out of Git.
4. In separate terminals, run `.\mvnw.cmd spring-boot:run` from `usermanagementservice` and `messageservice` on Windows. On macOS/Linux, use `sh mvnw spring-boot:run`.

The user service listens on port 8081 and the message service on 8082. JPA is configured to update the tables on startup. This is a coursework backend, with no standalone web frontend or hosted demo.
