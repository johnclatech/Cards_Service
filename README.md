# Cards_Service
A card Project for my Interview

The swagger for the Card-service API runs on :
http://localhost:8081/swagger-ui/index.html

The First step is to run the /register API to register a user and
assign a role to that user (scoped roles : MEMBER,ADMIN).

After registering, maintain the username (email) and password
for the next step /login

The /login endpoint delivers a bearer token to configure in the swagger
Authorise pop-up.

The Properties File :
DB access applications properties does not require you to pass a password
therefore the API can run as is as long as you have a MYSQL instance running under root user and no password
else configure your password and user here.

The API runs on a configurable port: 8081
