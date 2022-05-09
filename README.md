# Food Recipe Spring Boot REST API
A recipe REST API built in Spring Boot. It allows you to create, update, delete, and view recipes. Besides, you can post comment for each recipe as well as rate it.

## Getting Started
<ul>
  <li>Clone and open in Intellij Idea IDE</li>
  <li>Change database connection config in src/main/resources/application.properties</li>
  <li>Install maven dependencies using IDE auto import or using the command mvn install</li>
  <li>Run the app using mvn spring-boot:run</li>
  <li>Browse http//localhost:8080/</li>
</ul>

## Overview
This project also make use of Spring Security to perform authentication and authorization for the requested REST API endpoints. The specific permissions of each role are
shown below.

<ul>
  <li><b>Admin: </b>Basically, delete operations need admin access. Admin has permission to delete members and comments written by members.</li>
  <li><b>Member: </b>Member can modify his/her address, create recipes, like and rate recipes and delete recipes.</li>
</ul>

GET operations can be performed without any authentication.


<img src="https://user-images.githubusercontent.com/93772280/167477028-f9af901f-9b26-4f09-be7b-b029354ec309.PNG" width="700">

## API Doc & Sample


```
```

## Built With
<ul>
  <li>Java</li>
  <li>Spring Boot</li>
  <li>Spring Data JPA</li>
  <li>Spring Security</li>
  <li>Maven</li>
</ul>





