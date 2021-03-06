# REST API

This REST API is build on Spring boot with Oracle 11g as database.

# Project Structure 

- Code is present in various `packages` in `src/main/java`
- `application.properties` in `src/main/resources`
- test classeses in `src/test/java`
- `pom.xml`

structure of `src/main/java`
```
└───contactmgmt		#ContactmgmtApplication.java - to run springbootapplication
    ├───api		#Controller
    ├───dao		#database related
    ├───entity		#db-entities
    ├───exceptions	#custom exceptions and exceptions handler
    ├───model		#DTO's
    ├───security	#Security
    ├───service		#service layer
    └───validations	#validations related file
```
# Profiles

| Environments  | Databases |
| ------------- | ------------- |
| dev  | MySQL |
| test  | In-memory(h2)  |
| prod  | Oracle |

# REST API Details
- GET :
    - /contacts             - fetch all the contacts in the database table contact.
    - /contacts/{phoneNo}   - fetch a single contact with given phone number.
- POST:
    - /contacts -           - Creates a new contact with the JSON request.
- PUT:
    - /contacts/{phoneNo}    - Updates the contact with given phone no with the new request.
- PATCH:
    - /contacts/{phoneNo}    - Modifies the contact with given phone no with the new request.
- DELETE:
    - /contacts/{phoneNo}    - Deletes the contact with given phone no.

>***Note:*** *{phoneNo} must be of 10 digits*

# Running the project 
- Import as a maven project
- run a clean install by right clicking on pom.xml
- test profile is active now, it uses in-mem database. H2 console can be viewed at runtime [here](http://localhost:3010/v1/h2/)
- Profile can be changed in application.yml (spring.profile.active='either test/dev/prod')
- right click on the project and run as a springboot application.
 
 After starting the application hit [this](http://localhost:3010/v1/contacts/) URL through any browser, postman or use [swaggerui](http://localhost:3010/v1/swagger-ui.html). 
 
 It will ask for authentication 

```
Username: admin
Password: contact1
```

### Sample Request

```
{
    "first_name": "Ketan",
    "last_name": "Yadav",
    "email_id": "kyadav073@gmail.com",
    "phone_no": "7387431338",
    "status": "Active"
}
```
 
# REST API documentation

I have implemented it through swagger. 
Please find the details at [swaggerui](http://localhost:3010/v1/swagger-ui.html) after starting the application.
 
# TODOs
- [x] unit test for each endpoint
- [x] Few validations are still pending.
- [x] logger (use org.slf4j.Logger)
- [x] json fields snake_casing
- [x] remove other verbose like: printing sql queries at server side
- [x] API versioning
- [x] Profiles
