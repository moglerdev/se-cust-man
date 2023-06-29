```
  ██████ ▓█████  ▄████▄   █    ██   ██████ ▄▄▄█████▓ ███▄ ▄███▓ ▄▄▄       ███▄    █ 
▒██    ▒ ▓█   ▀ ▒██▀ ▀█   ██  ▓██▒▒██    ▒ ▓  ██▒ ▓▒▓██▒▀█▀ ██▒▒████▄     ██ ▀█   █ 
░ ▓██▄   ▒███   ▒▓█    ▄ ▓██  ▒██░░ ▓██▄   ▒ ▓██░ ▒░▓██    ▓██░▒██  ▀█▄  ▓██  ▀█ ██▒
  ▒   ██▒▒▓█  ▄ ▒▓▓▄ ▄██▒▓▓█  ░██░  ▒   ██▒░ ▓██▓ ░ ▒██    ▒██ ░██▄▄▄▄██ ▓██▒  ▐▌██▒
▒██████▒▒░▒████▒▒ ▓███▀ ░▒▒█████▓ ▒██████▒▒  ▒██▒ ░ ▒██▒   ░██▒ ▓█   ▓██▒▒██░   ▓██░
▒ ▒▓▒ ▒ ░░░ ▒░ ░░ ░▒ ▒  ░░▒▓▒ ▒ ▒ ▒ ▒▓▒ ▒ ░  ▒ ░░   ░ ▒░   ░  ░ ▒▒   ▓▒█░░ ▒░   ▒ ▒ 
░ ░▒  ░ ░ ░ ░  ░  ░  ▒   ░░▒░ ░ ░ ░ ░▒  ░ ░    ░    ░  ░      ░  ▒   ▒▒ ░░ ░░   ░ ▒░
░  ░  ░     ░   ░         ░░░ ░ ░ ░  ░  ░    ░      ░      ░     ░   ▒      ░   ░ ░ 
      ░     ░  ░░ ░         ░           ░                  ░         ░  ░         ░ 
                ░                                                                   
```

![coveralls](https://gitlab.com/moglerdev/cxx_cam/-/raw/split_code/COVERAGE.svg)
![ci_cd](https://img.shields.io/github/actions/workflow/status/moglerdev/se-cust-man/main.yml?style=for-the-badge)
![branch size](https://img.shields.io/github/repo-size/moglerdev/se-cust-man?style=for-the-badge)

**For more detailed information about the project, please refer to the [Wiki Page](https://github.com/moglerdev/se-cust-man/wiki)**

## About It 

The ongoing project at HTWG University in the field of Software Engineering focuses on the agile development of a Customer Relationship Management (CRM) system. The project aims to design a well-thought-out software architecture. The CRM system assists businesses in managing customer relationships and includes features such as contact management, sales activities, and marketing initiatives. By utilizing agile methodologies like Scrum or Kanban and leveraging modern technologies, the project aims to create a flexible, scalable, and maintainable solution. The project team collaborates closely with stakeholders and users to gather continuous feedback and make adjustments to the CRM system according to requirements and quality standards.

## Technologies Used

In addition to the dependencies mentioned earlier, the project also utilized the following technologies:

- **Scala**: The primary programming language used in the project. Scala is a statically typed, functional and object-oriented language that runs on the Java Virtual Machine (JVM). It provides powerful language features and interoperability with Java.

- **sbt**: The build tool used in the project. sbt (Scala Build Tool) is a popular build tool for Scala projects. It provides dependency management, compilation, testing, packaging, and deployment capabilities.

- **PostgreSQL**: The chosen database management system for the project. PostgreSQL is a powerful, open-source relational database that provides robustness, scalability, and SQL support.

- **JSON**: JSON (JavaScript Object Notation) was used for data interchange in the project. It is a lightweight data format that is easy to read and write for humans, and it is widely supported by various programming languages and frameworks.

- **Docker**: Docker was used for containerization and deployment purposes. Docker enables the creation and management of lightweight, isolated containers that encapsulate an application and its dependencies, providing a consistent and reproducible environment across different systems.

These technologies were selected to leverage their capabilities and benefits in terms of scalability, productivity, data management, interoperability, and containerization.

### Dependencies

Summary of Dependencies Used:

- **ScalaFX**: "org.scalafx" % "scalafx_2.13" % "20.0.0-R31" - Scala library for creating JavaFX applications.
- **PostgreSQL**: "org.postgresql" % "postgresql" % "42.6.0" - PostgreSQL JDBC driver for connecting to a PostgreSQL database.
- **Google Guice**: "com.google.inject" % "guice" % "7.0.0" - Dependency injection framework for Java.
- **Scala Guice**: "net.codingwell" %% "scala-guice" % "7.0.0" - Integration library for using Guice with Scala.
- **Scala XML**: "org.scala-lang.modules" %% "scala-xml" % "2.1.0" - Scala library for working with XML.
- **ScalaTest**: "org.scalatest" %% "scalatest" % "3.2.15" % "test" - Scala testing framework.
- **Circe (JSON library)**: "io.circe" %% "circe-core", "io.circe" %% "circe-generic", "io.circe" %% "circe-parser" - JSON parsing and encoding library for Scala.
- **Java-Dotenv**: "io.github.cdimascio" % "java-dotenv" % "5.2.2" - Java library for loading environment variables from a .env file.
- **Mockito**: "org.mockito" % "mockito-core" % "5.4.0" % Test - Mocking framework for testing in Java.
- **ScalaTestPlus Mockito**: "org.scalatestplus" %% "mockito-3-4" % "3.2.10.0" % Test - Integration between ScalaTest and Mockito for testing.

These dependencies were used in the project to provide functionality and support for various aspects of the software development process, such as user interface development, database connectivity, dependency injection, JSON handling, testing, and more.


### Design Patterns in use

1. [Factory](https://refactoring.guru/design-patterns/factory-method)
    - CustomerHandler -> create Insert, Update, Delete, ...
    - Handlers -> createChain
    - AccountService, AddressService, HistoryService, ProjectService, CustomerService -> `def getInstance(apiType: String)` 
2. [Chain of Responsibility](https://refactoring.guru/design-patterns/chain-of-responsibility)
    - Handlers (trait)
    - AddressHandler
    - AuthHandlers
    - CustomerHandler
3. [Proxy](https://refactoring.guru/design-patterns/proxy)
    - AccountService
    - AddressService
    - CustomerService
    - HistoryService
    - ProjectService
4. [Builder](https://refactoring.guru/design-patterns/builder)
    - AccountBuilder
    - Builder (trait)
    - CustomerBuilder
    - SqlBuilder (WIP)

## Installation

```bash
git clone https://github.com/moglerdev/se-cust-man.git
```

### SBT

```bash
sbt bloopInstall
```

### Docker

```bash
docker compose --build
```

## Usage

Once the application is up and running, you can use it to manage and analyze your customer interactions. 

### Start

#### Docker

```bash
docker compose up -d "db"
docker compose up "cli"
```

#### SBT

```bash
sbt run [--gui]
```

### Customer

```bash
prompt> project ls [-o <order=name|email|phone|address>]
prompt> customer help 
prompt> customer set [-i id] -n <name> -e <email> -p <phone number> -a <address>
prompt> customer rm [ids ...]
prompt> customer open [id]
```

### Project (after open customer)

```bash
prompt> project ls -o <order=title|description|priority>
prompt> project help 
prompt> project set [-i <id:int>] -t <title:string> -d <description:string> -p <priority:int>
prompt> project rm <ids ...>
prompt> project open <id:int>
```

### Tasks (after open project)

```bash
prompt> task ls -o <order=title|description|deadline>
prompt> task help
prompt> task set [-i <id:int>] -t <title:string> -d <description:string> -e <priority:int>
prompt> task rm <ids ...>
```

## MIT License

Copyright (c) 2023 Christopher Jaeger and Dennis Schulze

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
