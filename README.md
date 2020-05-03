# REST API

This is REST API build on Spring boot with Oracle 11g as database.

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
    ├───service		#service laye
    └───validations	#validations related file
```
# Profiles

| Environments  | Databases |
| ------------- | ------------- |
| dev  | MySQL |
| test  | In-memory  |
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

***Note: phoneNo must be of 10 digits***

# Running the project 
	- Import as a maven project
    - run a clean install by right clicking on pom.xml
    - add your database details in application.properties
    - right click on the project and run as a springboot application.
 
 After starting the application hit [this](http://localhost:3010/v1/contacts/) URL through any browser or postman. 
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
    "email_id": "ky@gmail.com",
    "phone_no": "9009002000",
    "status": "Active"
}
```
 
# REST API documentation

I have implemented it through swagger 
Please find the details at [this](http://localhost:3010/v1/swagger-ui.html) url after starting the application.
 
# TODOs
- [x] unit test for each endpoint
- [x] Few validations are still pending.
- [x] logger (use org.slf4j.Logger)
- [x] json fields snake_casing
- [x] remove other verbose like: printing sql queries at server side
- [x] API versioning
 
