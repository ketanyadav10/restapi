# restapi

This is REST API build on Spring boot with oracle 11g as database.
#Folder Structure 
Code is present in various 
  packages in src/main/java
  application.properties in src/main/resources
  test classeses in src/test/java
  pom.xml
  
 REST API Details:
  GET :
    /contacts             - fetch all the contacts in the database table contact.
    /contacts/{phoneNo}   - fetch a single contact with given phone number.
  POST:
    /contacts -           - Creates a new contact with the JSON request.
  PUT:
   /contacts/{phoneNo}    - Updates the contact with given phone no with the new request.
  PATCH:
   /contacts/{phoneNo}    - Modifies the contact with given phone no with the new request.
  DELETE:
   /contacts/{phoneNo}    - Deletes the contact with given phone no.
#Running the project 
 - run a clean install by right clicking on pom.xml
 - right click on the project and run as a springboot application.
 
 After starting the application hit the below URL through any browser or postman. 
 http://localhost:3010/evocon/contacts/
 It will ask for authentication 
    Username: admin
    Password: contact1
 Sample Request: 
     {
        "firstName": "Ketan",
        "lastName": "Yadav",
        "emailId": "ky@gmail.com",
        "phoneNo": "9009002000",
        "status": "Active"
    }

 

 
 For REST API documentation: 
 I have implemented it through swagger 
 Please find the details at http://localhost:3010/evocon/swagger-ui.html after starting the application.
 
