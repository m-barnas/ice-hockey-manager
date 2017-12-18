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

### REST API (all formats are JSON)

#### Teams

##### Create team 

URL: `http:/localhost:8080/pa165/rest/teams/create`

method: PUT 

data format: `{  
  "name":"HC Sparta test d Praha",
  "competitionCountry":"CZECH_REPUBLIC",  
  "humanPlayerId":null,   
  "budget":7000.00    
}`

#### Get all teams

URL: `http:/localhost:8080/pa165/rest/teams/all`

method: GET

#### Find team by Id 

URL: `http:/localhost:8080/pa165/rest/teams/{id}`

method: GET


#### Get team by name 

URL: `http:/localhost:8080/pa165/rest/teams/getByName/{name}`

method: GET


#### Delete team by Id 

URL: `http:/localhost:8080/pa165/rest/teams/{id}`

method: DELETE

#### Get teams by competition country 

URL: `http:/localhost:8080/pa165/rest/teams/getByCompetitionCountry/{competitionCountry}`

method: GET

##### Spend money from team budget 

URL: `http:/localhost:8080/pa165/rest/teams/spendMoneyFromBudget`

method: POST 

data format: `{  
  "teamId": 1,
  "amount": 20
}`

#### Get price of team

URL: `http:/localhost:8080/pa165/rest/teams/{id}/price`

method: GET

#### Get attack power of team

URL: `http:/localhost:8080/pa165/rest/teams/{id}/attack`

method: GET

#### Get defense power of team

URL: `http:/localhost:8080/pa165/rest/teams/{id}/defense`

method: GET

##### Add hockey player to team

URL: `http:/localhost:8080/pa165/rest/teams/addHockeyPlayer`

method: POST 

data format: `{  
  "teamId": 1,
  "hockeyPlayerId": 1
}`

##### Remove hockey player from team 

URL: `http:/localhost:8080/pa165/rest/teams/removeHockeyPlayer`

method: POST 

data format: `{  
  "teamId": 1,
  "hockeyPlayerId": 1
}`




