# Backend

![dark_logo](https://user-images.githubusercontent.com/69918609/136712595-77589669-b92d-4d69-bade-ccb2b95f0f56.png)  <br>

<img src="https://img.shields.io/github/stars/InnoTutor/Backend?style=social">   ᅠ [![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/InnoTutor/Backend/blob/main/LICENSE) ᅠ   [![Hits-of-Code](https://hitsofcode.com/github/InnoTutor/Backend?branch=main)](https://hitsofcode.com/github/InnoTutor/Backend/view?branch=main)<br><br><br>
**Developers**: Daniil Livitn, Roman Soldatov
<br><br>

## Contents
- [Requirements](#requirements)
- [API](#api)
- [Database](#database)
- [Google credentials](#google-credentials)
- [Hosting and CI](#hosting-and-ci)
- [How to install locally](#how-to-install-locally)
- [Code analysis](#code-analysis)
- [Want to contribute?](#want-to-contribute)

This is the repository with Backend code for the InnoTutor project. The backend is designed as REST API, so you need the frontend part to access it. Alternatively, you can use [Postman](https://www.postman.com) or [Swagger](https://swagger.io).
The main description of the project is available [here](https://github.com/InnoTutor/README)

# Requirements
**Technical Stack**: <br>
* **Java, [Spring Framework](https://spring.io/projects/spring-framework), Firebase, Docker, PostgreSQL**: <br>
  * IntelliJ IDEA IDE
> For this open-source project, we used the simplest registration way via Google [Firebase](https://firebase.google.com)
> - as a website user, you need to have a Google account
> - as a developer, who is going to host this project version, you need to get your own Google credentials.
> 
> In case of using this project in **Innopolis University** we'll change authentication to **innopolis email** login page.

## Stakeholders and their roles 👤
* **Backend Development Team**
  * **Developer 1** – Backend developer. Implement the backend on Java/Spring which interacts with the PostgreSQL database.
  * **Developer 2** – Backend developer. Implement the backend on Java/Spring which returns the result to the frontend via REST API.
  * **Developer 3** – Backend developer. Write tests for the Backend. <br>
 **Stake**: Development process

# API
There is documentation with all requests which backend supports.
You can read about API documentation [here](https://documenter.getpostman.com/view/16213957/UUy65PgU)

# Database
We use Docker and PostgreSQL for the database.
Here is the database [diagram](https://github.com/InnoTutor/README/blob/main/UMLDiagrams/DatabaseDiagram.md)

- If you want to import our database, you can use [restore.sql](restore.sql) file with empty tables.
- In [application.properties](/src/main/resources/application.properties) file specify your database's `URL`, `username` and `password`.
> - Guide how to import your database: [link](https://www.postgresql.org/docs/9.1/backup-dump.html)
> - Guide how to use PostgreSQL in Docker: [link](https://youtu.be/aHbE3pTyG-Q)

# Google credentials
To log in, you need to provide your Google credentials created [here](https://firebase.google.com).
Then open [application.properties](/src/main/resources/application.properties) file and input your JSON Google credentials file as a single string in `GOOGLE_CREDENTIALS` field.
You can use this online service to convert JSON into a string with escape characters: [link](https://www.freeformatter.com/json-escape.html)
> In case of any problems refer to this [guide](https://medium.com/@renceabishek/how-to-add-google-api-credentials-key-on-heroku-spring-boot-16b03e2a2363)

# Hosting and CI
We use [Heroku](https://heroku.com/) to host the backend. [Here](https://innotutor.herokuapp.com) is the deployed version of it.
For continuous integration, we have to make a pull request into the `main` branch. Then Heroku will automatically deploy a new version on the server.

To build the project on Heroku's server you need to provide **config vars**: `DATABASE_URL` and `GOOGLE_CREDENTIALS`. Also, add this buildpack `https://github.com/buyersight/heroku-google-application-credentials-buildpack.git`
> Guide how to work with config vars: [link](https://devcenter.heroku.com/articles/config-vars)

# How to install locally
- Download this project
- Open it in IntelliJ IDEA
- Set up database and Google credentials (check instructions above [Database](#database) and [Google credentials](#google-credentials))
- Open `InnotutorBackendApplication` java class and press `ctrl+R`
> Guide how to run Spring project: [link](https://www.jetbrains.com/help/idea/your-first-spring-application.html)
## Another way of install project but without IDE (note: you should have Maven installed)
1) Clone this repository to your machine using: `https://github.com/InnoTutor/Backend.git`
2) You need to create a database and specify the path to it in the `application.properties`. Besides this, you need to specify your username, password and google credentials. <br><br> Note: Path to `application.properties`: [application.properties](/src/main/resources/application.properties)
```
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/innotutor}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:postgres}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:root}
...
GOOGLE_CREDENTIALS=<Your Google Credentials>
```
3) Using the command line, navigate to the root folder of the project and run the command:
```
mvn clean install
``` 
4) After a successful build, you need to run the command:
```
java -jar .target/innotutor_backend-0.0.1-SNAPSHOT.jar

or

java -jar target/innotutor_backend-0.0.1-SNAPSHOT.jar
```

# Code analysis
* [The result of PMD static analyzer](https://github.com/InnoTutor/README/blob/main/StaticAnaylyzer/result.md). Do not be scared by such a huge number of violations. We fixed all the violations that we could fix. Other violations can not be fixed due to spring framework-specific code which requires to follow special name convention, so the framework could understand fields and create particular [Beans](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-definition).

# Want to contribute?
You can contribute to this project. Just fork the repository from the `develop` branch, implement changes you want to propose and make a pull request.
Also, there are [issues](https://github.com/InnoTutor/Backend/issues), so feel free to submit a new one or participate in existing.
