# Backend

![logo](https://user-images.githubusercontent.com/44948387/136674082-18921bd1-b4f5-40d3-81bf-f98028c159c6.png)   <br>

<img src="https://img.shields.io/github/stars/InnoTutor/Backend?style=social">   ᅠ [![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/InnoTutor/Backend/blob/main/LICENSE) ᅠ   [![Hits-of-Code](https://hitsofcode.com/github/InnoTutor/Backend?branch=main)](https://hitsofcode.com/github/InnoTutor/Backend/view?branch=main)<br><br><br>
**Developers**: Daniil Livitn, Roman Soldatov
<br><br>

## Contents
- [Requirements](#requirements)
- [API](#api)
- [Database](#database)
- [Google credentials](#credentials)
- [Hosting](#hosting)
- [How to install locally](#installation)
- [Code analysis](#code)
- [Want to contribute?](#contribution)

This is the repository with Backend code for InnoTutor project. The backend is designed as REST API, so you need the frontend part to access it. Alternatively, you can use [Postman](https://www.postman.com) or [Swagger](https://swagger.io).
The main description of the project is available [here](https://github.com/InnoTutor/README)

# Requirements
**Technical Stack**: <br>
* **Java, [Spring Framework](https://spring.io/projects/spring-framework), Firebase, Docker, PostgreSQL (BackEnd)**: <br>
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
There is a documentation with all requests which backend supports.
You can read about API documentation [here](https://documenter.getpostman.com/view/16213957/UUy65PgU)

# Database
We use Docker and PostgreSQL for the database.
Here is the database [diagram](https://github.com/InnoTutor/README/blob/main/UMLDiagrams/DatabaseDiagram.md)
If you want import our database, you can use restore.sql file with empty tables.

# Google credentials
To log in you need to provide your Google credentials created [here](https://firebase.google.com).
Then open ![application.properties](/src/main/resources/application.properties) file and past your JSON Google credentials file in `GOOGLE_CREDENTIALS` filed.

# Hosting


# How to install locally

# Code analysis
* [The result of PMD static analyzer](https://github.com/InnoTutor/README/blob/main/StaticAnaylyzer/result.md). Do not be scared by such a huge number of violations. We fixed all the violations that we could fix. Other violations can not be fixed due to spring framework-specific code which requires to follow special name convention, so the framework could understand fields and create particular [Beans](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-definition).

# Want to contribute?
You can contribute in the frontend and backend. Just fork the repository from the `develop` branch, implement changes you want to propose and make a pull request.
Also, there are issues in [backend](https://github.com/InnoTutor/Backend/issues) and [frontend](https://github.com/InnoTutor/Frontend/issues), feel free to submit a new one or participate in existing.
