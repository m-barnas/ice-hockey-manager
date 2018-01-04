# ice-hockey-manager
Manage your favorite hockey team!

### Configuration:

* Rest is accessible through http://localhost:8080/pa165/rest.
* You can try already preconfigured Admin account: `admin@icehockeymanager.com` with password `test`

### Authentication:

It is needed to authenticate yourself before any requests which changes data on the server (except basic registration). 

* All find operations are accessible without authentication.
* You may still get 401 Forbidden if you are not authorized for that particular operation.

##### Registration

To register martin@m.com with password 123456

```
curl -X POST -H 'Content-Type: application/json' -d '{
    "username":"martin",
    "email":"martin@m.com",
    "role":"USER",
    "password":"123456"}' http://localhost:8080/pa165/rest/register
```

##### Authentication OAuth2

1. Get your client id and client secret:
  * Client Id: `rest-client`
  * Client Secret: `58aa46b5-ddb1-4a29-bed9-55b9f3521280`

2. Generate Authorization header
  * Header (client id + secret in Base64):

    `Authorization: Basic cmVzdC1jbGllbnQ6NThhYTQ2YjUtZGRiMS00YTI5LWJlZDktNTViOWYzNTIxMjgw`

3. Authenticate yourself as martin@m.com with password 123456

    ```
    curl -X POST -H "Authorization: Basic cmVzdC1jbGllbnQ6NThhYTQ2YjUtZGRiMS00YTI5LWJlZDktNTViOWYzNTIxMjgw" "http://localhost:8080/pa165/rest/oauth/token?grant_type=password&username=martin@m.com&password=123456"
    ```

4. Get a response

    You will get access token valid for 10 minutes

    ```json
    {
      "access_token": "e1aaa981-9384-4761-b2ae-f21a316794a5",
      "token_type": "bearer",
      "refresh_token": "d40db753-2b3f-4822-bfbc-509d11a32638",
      "expires_in": 600,
      "scope": "read write trust",
      "role": "USER",
      "userId": 13
    }
    ```

5. Setup header before api request

    Setup authorization header before any request to the API:

    `"Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5"`

    ```
    curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer e1aaa981-9384-4761-b2ae-f21a316794a5" -d '{
        "name": "Conocybe filaris",
        "type": "POISONOUS"
    }' "http://localhost:8080/pa165/rest/teams/create"
    ```

6. Refresh expired token
  * You need to get a new access token in step 3 after the old one has expired.
  * You can call step 3 again to also check the remaining time

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

