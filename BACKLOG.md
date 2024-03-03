# Backlog

## Task

The client stores its Java microservices participating in the microservice architecture in polyrepo mode.
Currently, they are undergoing a modernization process throughout our entire codebase, and they would like all their microservices to:

- Contain maintainable, readable Java 17 code,
- Idiomatically use Spring Boot,
- Follow RESTful API best practices,
- Be adequately tested with proper coverage.

**The task is to refactor this simple microservice found in this repository according to the above criteria.**

To complement the situation, assume that this is an application already running in production,
under continuous load (400-500 QPS), so new code can only be deployed without downtime.

Do the refactor in a way that achieves the desired result, but if the CI/CD pipeline automatically
deploys your changes (build+test+deploy), it should not affect the business logic, functionality of the microservice,
cause data loss, etc. The refactor must be isolated, meaning that it should not change the clients using the service;
the new code should work with existing clients.

## Legend

- ⚠ TODO
- 🚧 WIP
- ✅ Done
- 🛑 Blocked

## Subtasks

- ✅ Configure gitignore
- ✅ Add testing dependencies
- 🛑 Add more unit tests
- ✅ Add API tests
- ✅ Add Checkstyle
- ✅ Add CI/CD
    - ✅ Build
    - ✅ Run tests
    - ✅ Run Checkstyle
    - ✅ Introduce Sonar scanning
    - ✅ Introduce Snyk scanning
- ✅ Fix reported warnings and errors
- ✅ Utilize Lombok
- ✅ Utilize Jackson
- ⚠ Separate endpoints
- ⚠ Introduce hexagonal architecture
    - ⚠ Extract business logic from controller
    - ⚠ Extract database adapter
    - ⚠ Separate data classes for different "layers"
- 🚧 Optimize DB performance
- ⚠ Fix DB config
- ⚠ Optimize logging
- ⚠ Externalize configuration
- ⚠ Introduce caching

## Further Suggestions

- Migrate to Gradle
- Introduce DB versioning (Flyway, Liquibase)
- Add automatic dependency bumping to CI/CD
- Add Dockerfile and automatic image building to CI/CD
- Move to continuous deployment
