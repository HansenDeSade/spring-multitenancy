# Purpose
This is kind of prototype or proof of concept to show the possibilities of multitenancy in a microservice environment. There are 3 different approaches to achieve multitenancy regarding the persistence of your data:
* shared database - a tenant specific identification per data record
* separated schema - each tenant has it's own schema on the same database
* separated database - each tenant has it's own database

This prototype follows the idea of separated database since this approach represents the highest grade of isolation and therefor more security. The tradeoff might be a worse performance. The goal of this prototype is to investigate the performance impact as well.

# Idea
The implemented REST-Service has one examlified use: getting a tenant specific string. The determination, which database to use, is made by a JWT-Token, that contains the authenticated user and a tenantid. There is no complex user management in this prototype since that was not focused. There are 3 hard coded users called "userA", "userB" and "userC", that are assigned to 3 different h2 databases "tenant1", "tenant2" and "tenant3". Before handling any request, an interceptor extracts the tenant-identifier from the token and stores it as a request-attribute. As soon as there is any database interaction, the related datasource will be identified by the "DataSourceBasedMultiTenantConnectionProviderImpl" and the connection will be switched.

# Installation
The application is built with gradle. A simple gradlew clean build should work just fine. The 3 h2 databases will be initialized on application start and should exist only in memory. The examplified data records will be inatialized too. 

# Examples
Authentication:
To keep it simple all users have the same password "test". Each user is linked to it's own database, which identifier will be stored in the JWT-Token, as long as the authentication is successfull.
`curl -X POST \
  http://localhost:8080/authenticate \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
	"username": "userA",
	"password": "test"
}'`
The call will provide you a brandnew JWT-Token for authorization, that will expire after 24 hours. The token is provided as a response-header "authorization" and looks like this:  "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQSIsImV4cCI6MTUxNDYyODM0NiwiaXNzIjoiTXVsdGl0ZW5hbmN5IG51bGwiLCJpYXQiOjE1MTQ1NDE5NDYsImF1ZCI6InNjaG5laWRlciJ9.Cd9WJPRdUXcO5jT_Pp75ejZK_OHlZsFM7FGM33LbTxNHILbxi2a6W5I6xGBQ_6C7AvWiLE6W8cILX_2yPEn7AA", as long as the response code is 200-Ok.

Request with JWT-Token based authorization:
The token expires after 24 hours, so you'll have to make a authentication (see above) first, to get a new one.
`curl -X GET \
  http://localhost:8080/tenantinfo \
  -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQSIsImV4cCI6MTUxNDYyODM0NiwiaXNzIjoiTXVsdGl0ZW5hbmN5IG51bGwiLCJpYXQiOjE1MTQ1NDE5NDYsImF1ZCI6InNjaG5laWRlciJ9.Cd9WJPRdUXcO5jT_Pp75ejZK_OHlZsFM7FGM33LbTxNHILbxi2a6W5I6xGBQ_6C7AvWiLE6W8cILX_2yPEn7AA' \
  -H 'cache-control: no-cache' \`
  
