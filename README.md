# Backend

![logo](https://user-images.githubusercontent.com/44948387/136674082-18921bd1-b4f5-40d3-81bf-f98028c159c6.png)   <br>

<img src="https://img.shields.io/github/stars/InnoTutor/Backend?style=social">   ᅠ [![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/InnoTutor/Backend/blob/main/LICENSE) ᅠ   [![Hits-of-Code](https://hitsofcode.com/github/InnoTutor/Backend?branch=main)](https://hitsofcode.com/github/InnoTutor/Backend/view?branch=main)<br><br><br>
**Authors**: Daniil Livitn, Roman Soldatov, Emil Khabibulin, Tasneem Toolba
<br><br>

Repository with Backend code for InnoTutor project. It is written on Java/Spring.

You can read about API documentation 
[here](https://documenter.getpostman.com/view/16213957/UUy65PgU)

## Contents

- [What is the goal of this project?](#project_goal)
- [Requirements](#requirements)
- [Design](#design)
- [Architecture](#architecture)
- [Code](#code)
- [Want to contribute?](#contribution)

# What is the goal of this project?
The problem we are trying to solve is to fulfil students’ needs in some hard courses with the help of other students who are/were good at those hard courses, and make the university consider such help as an official student activity so that those students who helped others, get awarded. 
* Build a platform for **Innopolis University** students to share their knowledge, where students that are good in some courses and want to help others know where to inform others about this willingness to help.
* The website needs to manage how each of the students and tutors finds each other and communicate.
* The built platform should be able to manage meeting schedules, tutors’ and students’ availability.
* The system should handle authentication with **university accounts**.
* The build application needs to award the tutors (university students) by the internal university’s currency (**innopoints**) or by discussing other awards with the university itself.

## Demo
* Here is a [video demo](https://www.youtube.com/watch?v=dQw4w9WgXcQ) of our project
* You can try it for yourself here https://innotutor.github.io/Frontend/

# Requirements
**Technical Stack**: <br>
* **Flutter (FrontEnd)**: UI cross-platform applications for Android, IOS, and WEB
  * Visual Studio Code and Android Studio are used as an IDE
  * Link to the repository with detailed information: [Frontend Repository](https://github.com/InnoTutor/Frontend)
* **Java Spring Boot, Firebase, Docker, PostgreSQL (BackEnd)**: <br>
  * IntelliJ IDEA IDE
  * Link to the repository with detailed information: [Backend Repository](https://github.com/InnoTutor/Backend)
  * Link to the [API documentation](https://documenter.getpostman.com/view/16213957/UUy65PgU)
> For this open-source project, we used the simplest registration way via Google [Firebase](https://firebase.google.com)
> - as a website user, you need to have a Google account
> - as a developer, who is going to host this project version, you need to get your own Google credentials.
> 
> In case of using this project in **Innopolis University** we'll change authentication to **innopolis email** login page.

<br><br>
## Glossary 📝
* **User**: anyone who uses the “InnoTutor” website.
* **Tutor**: a university student who offers help in some academic courses.
* **Student**: a university student who asked for help in some academic courses.
* **Requests list**: user’s list of help requests that he/she uploaded so tutors could see them.
* **Services list**: tutor’s list of courses that he/she is going to teach.
* **CV Card**: some kind of portfolio uploaded by a tutor on the profile. A tutor can specify the subject, description and preferences about online/offline and private/group meetings. A card can be *reserved* so no one can see it except a tutor. All uploaded **CVs** will be shown in the user's **Services list** and in **Tutors list** if this card was not reserved.
* **Request Card**: a card created by a **student** asking for help for a particular subject with preferences of **session format and type**. Any tutor can respond to this request and add a **student** to his/her **My Students** list. A **student** can hide any of his/her cards, so no one can see it. **Request Cards** can be seen in **Students list** if this card was not hidden.
* **Tutors list**: list of tutors offering their help given their preferences, e.g. online/offline, subject, and whether in a group or not.
* **Students list**: list of students who asked for help. All tutors can see those students.
* **My Students**: list of tutor’s students who asked him/her for help. A tutor can manage these students via removing them or creating sessions with them.
* **Session**: an upcoming meeting which contains a tutor, list of students, subject, date, time, offline/online format, private/group type and description with information about room number or conference link. A session can be created only by a tutor specifying his/her students from the “My Students” list.
* **Session format**: the meeting will be held online or offline.
* **Session type**: meeting will be *private* (only for one student) or *group* (for several students).
* **My schedule**: list of **user**'s upcoming sessions.
* **Tutor's rating**: rating which each tutor has for his/her services. Students can rate **CV cards** so others can see the tutor's service quality.
* **Innopolis University**: University in Russian Federation, Republic of Tatarstan, Innopolis city. Link to the [university website](https://innopolis.university). 
* **Innopoints**: the internal currency of the Innopolis University with which you can make purchases in a special store or use in the canteen to buy lunches.
* **Innopolis email**: an email that can get only students and staff of Innopolis University.

## Stakeholders and their roles 👤
* **End User**: university students that would be either as tutors for students, or students who will be responsible for continuous feedback as a use (**tutors ratings** will be provided)<br>
 **Stake**: Service Quality
* **Development Team**
  * **Developer 1** – Backend developer. Implement the backend on Java/Spring which interacts with the PostgreSQL database.
  * **Developer 2** – Backend developer. Implement the backend on Java/Spring which returns the result to the frontend via REST API.
  * **Developer 3** – Backend developer. Write tests for the Backend.
  * **Developer 4** – Frontend developer. Integrate backend with frontend and implement the logic of UI elements.
  * **Developer 5** – Frontend developer. Create and implement a user-friendly design using Flutter, containing all the features made on the backend where all developers will test their parts before integrating parts. <br>
 **Stake**: Development process
* **Project Manager & lead**:  Professor Yegor Bugaenko and our TA Aleksandr Tsupko 
  * Professor and TA: managing the project by controlling the entire creation process, considering problems, needs during the process, giving advice (*call to action* from lectures and practices from labs) and considering testing the final product.<br>
 **Stake**: managing project
* **Customer**: Professor Yegor Bugaenko (internal customer), University administration (external customer)
  * Professor: provides everything that’s needed to fulfil the project needs, including instructing and giving advice.
  * University administration: responsible for accepting and delivering the final product to the end-user.
## Backlog 📃
You can check our backlog with user stories by following the [link](https://github.com/InnoTutor/README/blob/main/UserStories/userStories.md)

## Non-functional requirements ✅
To check the non-functional requirements of our project, follow the [link](https://github.com/InnoTutor/README/blob/main/NonFunctionalRequirements/NonFunctionalRequirements.md)

## Project plan according to RUP 🗒
To check our RUP open this [link](https://docs.google.com/document/d/1Sv2SmeUKpcUjFgHgZRe0B2ob0WqhSORy/edit?usp=sharing&ouid=104971567017082470840&rtpof=true&sd=true)

# Design
## Diagrams 📊
* [Class Diagram](https://github.com/InnoTutor/README/blob/main/UMLDiagrams/ClassDiagram.md) <p>
* [Sequence Diagram](https://github.com/InnoTutor/README/blob/main/UMLDiagrams/SequenceDiagram.md) <p>
* [Database Diagram](https://github.com/InnoTutor/README/blob/main/UMLDiagrams/DatabaseDiagram.md) <p>
* [Use Case Diagram](https://github.com/InnoTutor/README/blob/main/UMLDiagrams/UseCaseDiagram.md) <p>

 
## Design Patterns
 * How do we used **SOLID**? 
   * **S**(ingle responsibility) - each database entity has only one responsibility representing its data. Moreover, each class has only one purpose. For example, class *AverageRating* has a constructor with the final field of the rating list and has a method responsible for calculating the average card's rating exactly for a particular tutor's list.
   * **O**(pen-closed) - each entity is opened for extension and closed for modification. It means that it is possible to add new fields or new elements to the entity(open) and, at the same time, the entity can be used by other classes (closed).
   * **L**(iskov substitution) - we did not use this principle since we do not use inheritance in the project.
   * **I**(nterface segregation) - we did not use this principle since we did not create special interfaces to be implemented by classes.
   * **D**(ependency inversion) - we created interfaces and used them instead of objects (examples: in backend in services.utility.sessionconverter.SessionConverter and dto.searcher.UserCard)
 * **Observer pattern**- to notify tutors about students requests and notify students about tutors responds
 * **Adapter pattern** - to interpret database data to another convenient form for the frontend
 
# Architecture
 To check the architecture of our application, you can open diagrams by following the links below.
* [Static View](https://github.com/InnoTutor/README/blob/main/UMLDiagrams/StaticViewDiagram.md) <p>
* [Dynamic View](https://github.com/InnoTutor/README/blob/main/UMLDiagrams/DynamicViewDiagram.md) <p>
* [Allocation View](https://github.com/InnoTutor/README/blob/main/UMLDiagrams/AllocationViewDiagram.md)<p>

# Code
 * [The result of PMD static analyzer](https://github.com/InnoTutor/README/blob/main/StaticAnaylyzer/result.md). Do not be scared by such a huge number of violations. We fixed all the violations that we could fix. Other violations can not be fixed due to spring framework-specific code which requires to follow special name convention, so the framework could understand fields and create particular [Beans](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-definition).
 * Test coverage - !TODO!
 
# Want to contribute?
You can contribute in the frontend and backend. Just fork the repository from the `develop` branch, implement changes you want to propose and make a pull request.
Also, there are issues in [backend](https://github.com/InnoTutor/Backend/issues) and [frontend](https://github.com/InnoTutor/Frontend/issues), feel free to submit a new one or participate in existing.

<br>
<br>

