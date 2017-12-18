# ice-hockey-manager
Manage your favorite hockey team!

### Run the app [prod]
`mvn clean install && cd ice-hockey-manager-web && mvn jetty:run-war`

###### Rest is accessible at: `http:/localhost:8080/pa165/rest`

###### Frontend is accessible at: `http:/localhost:8080/pa165`

### Run the app [dev]

`mvn clean install && cd ice-hockey-manager-rest mvn tomcat7:run`

In another terminal:

`cd ice-hockey-manager-web npm run start`

###### Rest is accessible at: `http:/localhost:8080/pa165/rest`

###### Frontend is accessible at: `http:/localhost:3000`

## REST API (all formats are JSON)

### Teams

#### Create team 

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

#### Add hockey player to team

URL: `http:/localhost:8080/pa165/rest/teams/addHockeyPlayer`

method: POST 

data format: `{  
  "teamId": 1,
  "hockeyPlayerId": 1
}`

#### Remove hockey player from team 

URL: `http:/localhost:8080/pa165/rest/teams/removeHockeyPlayer`

method: POST 

data format: `{  
  "teamId": 1,
  "hockeyPlayerId": 1
}`

### Hockey Players

#### Create player 

URL: `http:/localhost:8080/pa165/rest/players/create`

method: PUT 

data format: `{  
  "name":"Jaromir Jagr",
  "post":"RIGHT_WING",  
  "attSkill":99,
  "defSkill":15,   
  "price":100.00    
}`

#### Get all players

URL: `http:/localhost:8080/pa165/rest/players/all`

method: GET

##### Get player by team

URL: `http:/localhost:8080/pa165/rest/players/getByTeam/{teamId}`

method: GET 

#### Get free agents (players without a team)

URL: `http:/localhost:8080/pa165/rest/players/getFreeAgents`

method: GET

#### Find player by id 

URL: `http:/localhost:8080/pa165/rest/players/{id}`

method: GET


#### Get player by name 

URL: `http:/localhost:8080/pa165/rest/players/getByName/{name}`

method: GET


#### Delete player by Id 

URL: `http:/localhost:8080/pa165/rest/players/{id}`

method: DELETE

#### Get player by position 

URL: `http:/localhost:8080/pa165/rest/players/getByPost/{post}`

method: GET

#### Get player by attack skill

URL: `http:/localhost:8080/pa165/rest/players/getByAttSkill/{attSkill}`

method: GET

#### Get player by defense skill

URL: `http:/localhost:8080/pa165/rest/players/getByDefSkill/{defSkill}`

method: GET

#### Get player by price

URL: `http:/localhost:8080/pa165/rest/players/getByPrice/{price}`

method: GET

### Managers

#### Get all managers

URL: `http:/localhost:8080/pa165/rest/managers/all`

method: GET

#### Find manager by Id 

URL: `http:/localhost:8080/pa165/rest/managers/{id}`

method: GET

#### Find manager by email 

URL: `http:/localhost:8080/pa165/rest/managers/byemail?email=email`

method: GET

#### Find manager by username

URL: `http:/localhost:8080/pa165/rest/managers/byusername?username=username`

method: GET


### Games

#### Create game 

URL: `http:/localhost:8080/pa165/rest/games/create`

method: POST

data format: `{  
  "firstTeamId":1,
  "secondTeamId":2,  
  "startTime":"2030-01-01T18:00:00"
}`

#### Delete game by Id 

URL: `http:/localhost:8080/pa165/rest/games/{id}`

method: DELETE

#### Cancel game

URL: `http:/localhost:8080/pa165/rest/games/cancel/{id}`

method: PUT

#### Retrieve game

URL: `http:/localhost:8080/pa165/rest/games/retrieve/{id}`

method: PUT

##### Change start time

URL: `http:/localhost:8080/pa165/rest/games/{id}`

method: PUT

data format: `{  
  "id": null,
  "startTime":"2030-01-01T18:00:00"
}`

#### Find game by Id

URL: `http:/localhost:8080/pa165/rest/games/{id}`

method: GET


#### Get game by team

URL: `http:/localhost:8080/pa165/rest/games/byteam?teamId={id}`

method: GET

#### Get all games

URL: `http:/localhost:8080/pa165/rest/games/all`

method: GET

#### Get scheduled games (not played games with state OK)

URL: `http:/localhost:8080/pa165/rest/games/scheduled`

method: GET

#### Play games

URL: `http:/localhost:8080/pa165/rest/games/play`

method: PUT

