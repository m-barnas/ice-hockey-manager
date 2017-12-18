# ice-hockey-manager
Manage your favorite hockey team!

### Run the app [prod]
`mvn clean install && cd ice-hockey-manager-web mvn jetty:run-war`

###### Rest is accessible at: `http:/localhost:8080/pa165/rest`

###### Frontend is accessible at: `http:/localhost:8080/pa165`

### Run the app [dev]

`mvn clean install && cd ice-hockey-manager-rest mvn tomcat7:run`

In another terminal:

`cd ice-hockey-manager-web npm run start`

###### Rest is accessible at: `http:/localhost:8080/pa165/rest`

###### Frontend is accessible at: `http:/localhost:3000`
