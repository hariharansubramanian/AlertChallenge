# Alert Challenge
Myra Medicine backend alert challenge.

# Problem Statement

## Alerting System
There are many operations happening in the system which require monitoring. For example, when a transcription task is
assigned to a pharmacist in the call center, it has to be started within 1 minute and completed within 5 minutes. If the
timelines are not met, the concerned parties must be notified. The requirement is to build a system to monitor these events
and which is fast and robust.
The solution should implement three APIs


* ## Listing alerts
1. This is an API that will list all the alerts that have happened in the system and not yet been cleared.

2. It should output a list of alerts with reference_id , delay and description

```
Response:
Code: 200

{
    "alerts": [
                {
                    "reference_id": "transcription_start_1",
                    "delay": 60,
                    "description": "Transcription not yet started"
                },
                {
                    "reference_id": "transcription_complete_1",
                    "delay": 300,
                    "description": "Transcription not yet completed"
                }
            ]
}
```
* ## Posting an alert
1. This will take three parameters: reference_id , delay , description

2. The delay parameter is time in seconds after which the alert should appear in the listing API

```
Response:
Code: 201

{
    "alert": {
                "reference_id": "transcription_start_1",
                "delay": 60,
                "description": "Transcription not yet started"
    }
}
```
* ## Revoking an alert
1. This API will clear the alert from the system.
2. It will take reference_id as the parameter
3. It should be able to handle both cases, if an alert is already listed, it should be removed and if an alert is still within the
delay period, it should never be shown

```
Response:
Code: 204
{}
```
Expected load is 10 requests per second for each API. Response time should be within 100 ms.

## Deliverable requirements
1. You can choose stack of your preference.
2. It’s preferred to use macOS or Linux environment for coding if not then it must run on either of those.
3. It’s mandatory to use Git for version control.
4. We expect you to send us a zip/tarball of your source code which should include Git metadata (the .git folder in the
tarball so we can look at your commit logs and understand how your solution evolved).We expect you to do commits in
regular interval.
5. It’s mandatory to include a Readme file which must contain the following:
Overview of the tech stack, language etc.
Brief description of reason for using the specific tech stack
Infrastructure requirements for running your solution
Setup instructions, automated deployment of the program and dependencies to development and test environment
is a plus
6. It's a huge plus if you have written unit test cases and automated them.


# Solution Overview

### Technologies used:

    * Spring framework
    * Gradle build tool
    * Undertow servlet container
    * Postgresql database

My solution was implemented using technologies which promote:

* Portability
    
    * Rest server runs within the JVM
* Command line execution

    * Gradle commands to clean, run unit tests, run integration tests, uploadArtifacts, build and run server
* Verbose configurations and integrations
    * Integrated application.yml file providing Environment based configuration
    * Each environment can have a different configuration for its Logging, Datasources, Server context, Server Port, Security and much more
* Dependency Injection
    
    * All components and resources are easily replaced with test-oriented resources
    * Reduced Dependencies and Boilerplate code
* Scalability & Maintainability
    * Layered architecture
    * Modular, Resuable and Readable Code
    * Stability backed with Unit and Integration test cases
* Lightweight
    * Undertow is extremely lightweight, at runtime, with a simple embedded server uses less than 4Mb of heap space.

## Directory Structure
```
\---src
    +---integrationTest
    |   +---java
    |   |   \---life.dev.hari.alertChallenge
    |   |       |   WebIntegrationTestBase.java
    |   |       |
    |   |       +---controller
    |   |       |       AlertControllerIntegrationTest.java
    |   |       |
    |   |       \---repository
    |   |               AlertRepositoryIntegrationTest.java
    |   |
    |   \---resources
    |       \---testData
    |           \---postAlert
    |                   postAlertMustReturnBadRequestForNullDelay.json
    |                   postAlertMustReturnBadRequestForNullDescription.json
    |                   postAlertMustReturnBadRequestForNullReferenceId.json
    |                   postAlertMustReturnCreatedForValidAlert.json
    |
    +---main
    |   +---java
    |   |   \---life
    |   |       \---dev
    |   |           \---hari
    |   |               \---alertChallenge
    |   |                   |   AlertChallengeApplication.java
    |   |                   |
    |   |                   +---builder
    |   |                   |       AlertBuilder.java
    |   |                   |
    |   |                   +---controller
    |   |                   |   |   AlertController.java
    |   |                   |   |
    |   |                   |   \---myraRestException
    |   |                   |       +---customExceptions
    |   |                   |       |       DuplicateAlertException.java
    |   |                   |       |       IllegalAlertArgumentsException.java
    |   |                   |       |       IllegalAlertDeletionException.java
    |   |                   |       |
    |   |                   |       \---exceptionHandler
    |   |                   |               ErrorResponse.java
    |   |                   |               MyraRestExceptionHandlingAdviser.java
    |   |                   |
    |   |                   +---model
    |   |                   |       AbstractEntity.java
    |   |                   |       Alert.java
    |   |                   |
    |   |                   +---repository
    |   |                   |       AlertRepository.java
    |   |                   |
    |   |                   +---service
    |   |                   |   |   AlertService.java
    |   |                   |   |
    |   |                   |   \---ServiceImpl
    |   |                   |           AlertServiceImpl.java
    |   |                   |
    |   |                   +---utilities
    |   |                   |       JsonDeserializer.java
    |   |                   |
    |   |                   \---validator
    |   |                           AlertValidator.java
    |   |
    |   \---resources
    |           application.yml
    |
    \---test
        \---java
            \---life
                \---dev
                    \---hari
                        \---alertChallenge
                            |   AlertChallengeApplicationTests.java
                            |
                            \---validator
                                    AlertValidatorTest.java




```

# Features

While creatingthis project, my intention was to create a readily bootstrapped integrated project solution for any future REST API needs.

Some of the Features and integrations incorporated in this project are:

* <i><b>Multi-Environment configuration profiles</i></b>:  Dynamic configuration for different enviroments like 'common', 'local', 'dev', 'pre-prod' and 'prod'.

* <i><b>Custom Exception Handling</i></b>: Construct your own response error titles via redirection handlers
* <i><b>Generic Json Deserializer</i></b>: Deserialize Json Strings into any custom Object or arrays of custom objects.
* <i><b>Json File Content Reader</i></b>: Convert contents of any json file into a raw byte stream to be sent as a requestBody or responseBody.
* <i><b>Builder pattern for building objects</i></b>: Easily create and set property values of your custom objects using the Builder classes. Enables chained-setters.
* <i><b>Lambda Expressions</i></b>: Avoided all usages of for loops and for each, using Lambda expressions to filter and compute through Collections.
* <i><b>Layered architecture</i></b>: Modular, Re-usable and easily maintable code by separating concerns into different domains like Controller, Service, Model, Utility, Validator, Repository, Resources, Test, IntegrationTest
* <i><b>Powerful testing support</i></b>: JUnit4 for unit tests, Spring MockMvc for integration test cases.
* <i><b> Provision for custom tasks</i></b>: Using build.gradle file, create custom gradle tasks in groovy, apply them sequentially or parallely and re-arranging them in any order you wish, Make tasks depend on others, or execute tasks in between other standard tasks to provide a truly custom tailored experience for your application and dev operations.
* <i><b> Live-Monitoring</i></b>: Monitor and track your application/server Memory usage Metrics, Status, trace, logs, mappings etc using default GET REST endpoints.    
* <i><b>Future Roadmap</i></b>: Provisions for custom SSL-Stateless-Security, Logging, Database Versioning using Flyway and liquibase, Cloud support, WebSocket Session server for Realtime-updates etc   


# Installation

#### Pre-requisites
* Linux/MAC/Windows OS
* [Java JDK 1.8+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [PgAdmin3+](https://www.pgadmin.org/download/)

#### Database setup

   Using PgAdmin3 follow the screenshots posted in the link below:
  <blockquote class="imgur-embed-pub" lang="en" data-id="a/G08mq"><a href="//imgur.com/G08mq">PostgreSQL DB Setup</a></blockquote><script async src="//s.imgur.com/min/embed.js" charset="utf-8"></script>

  Note the following property values: 
  * Postgres Server Hostname - default is 'localhost'
  * Postgres Server Port - default is '5432'
  * Login Role Username - "alertsadmin" as per instructions
  * Login Role Password - "password" as per instructions
  * Database name - "alertdb" as per instructions
 
#### Application properties setup
* In the extracted zip directory,
Navigate and open file: 
```            
AlertChallenge\src\main\resources\application.yml 
```
By default you have the working config as per screenshot instructions:
* If you still feel like changing your server context path or port, navigate to the 'common' profile section
```
spring:
  profiles: common

server:
  contextPath: /
  port: 8800
  ```
  #### With the above configuration your rest requests will need to target the server host root:
  ```
  http://localhost:8800 
```
* If you provided custom names for your user role, database and credentials, Navigate to the 'local' profile section and Enter 'username','password' and 'url' accordingly.

Working config as per instructions from screenshots:
```
spring:
  profiles: local
  jpa:
    database-platform:  org.hibernate.dialect.PostgreSQLDialect
    show-sql: true
    hibernate:
      ddl-auto: create
  datasource:
    driverClassName: org.postgresql.Driver
    username: alertsadmin                            # insert correct username here
    password: password                             # insert correct password here
    url: jdbc:postgresql://127.0.0.1:5432/alertdb # database server IP and port numbers to be changed
    
```
# Running the application

* Navigate to the project root directory
* ##  Run server
```
./gradlew bootRun   #For Linux and Mac
gradlew bootRun     #For Windows
```

#### Please note to include the server port while sending REST requests 
```
http://localhost:8800/alerts
```

# View Test Reports

## Unit Tests
```
./gradlew test   #For Linux and Mac
gradlew test     #For Windows
```
The Unit Test report can now be opened by opening file:
```
/AlertChallenge/build/reports/tests/index.html
``` 

## Integration Tests
```
./gradlew integrationTest   #For Linux and Mac
gradlew integrationTest     #For Windows
```
The Integration Test report can now be opened by opening file:
```
/AlertChallenge/build/reports/tests/index.html
``` 

# Monitoring and Status

* ## View the Status of your application
```
Method:GET 
Endpoint: http://localhost:8800/health
```
* ## View the Latest application logs
```
Method:GET 
Endpoint: http://localhost:8800/logfile
```

* ## View your application trace
```
Method:GET 
Endpoint: http://localhost:8800/trace
```

* ## View the run-time memory and performance metrics
```
Method:GET 
Endpoint: http://localhost:8800/metrics
```


# API Reference


