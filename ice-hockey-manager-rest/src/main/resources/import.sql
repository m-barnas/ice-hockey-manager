-- create admin with password: adminpass
-- INSERT INTO HUMANPLAYER (ID, EMAIL, PASSWORDHASH, ROLE, USERNAME) VALUES (1,'admin@icehockeymanager.com','1000:0FhmwmqxciKTK4hvh1gCzzKktfTXzMUb:e0QFC0HKrbQUm57DaJHCb7S/','ADMIN','admin');
-- create admin with password: userpass
INSERT INTO HUMANPLAYER (ID, EMAIL, PASSWORDHASH, ROLE, USERNAME) VALUES (2,'user@icehockeymanager.com','1000:7oHkZicdye0Sn4pWj+ttQhc0ZmOghWwd:6wBBhr9OvhsQswaP196YgzgF','USER','user');
-- create HC Kometa Brno with manager user
-- INSERT INTO TEAM (ID, BUDGET, COMPETITIONCOUNTRY, NAME, HUMANPLAYERID) VALUES (1,5000,'CZECH_REPUBLIC','HC Kometa Brno',2);
-- create HC Sparta Praha without any manager
INSERT INTO TEAM (ID, BUDGET, COMPETITIONCOUNTRY, NAME, HUMANPLAYERID) VALUES (2,7000,'CZECH_REPUBLIC','HC Sparta Praha',null);
-- create future game Kometa - Sparta
-- INSERT INTO GAME (ID, FIRSTTEAMSCORE, GAMESTATE, SECONDTEAMSCORE, STARTTIME, FIRSTTEAM_ID, SECONDTEAM_ID) VALUES (1,null,'OK',null,'2030-01-01 18:00:00',1,2);
-- create goalie for Kometa
-- INSERT INTO HOCKEYPLAYER (ID, ATTACKSKILL, DEFENSESKILL, NAME, POST, PRICE, TEAMID) VALUES (1,2,50,'Marek Čiliak','GOALKEEPER',1200,1);
-- create goalie for Sparta
-- INSERT INTO HOCKEYPLAYER (ID, ATTACKSKILL, DEFENSESKILL, NAME, POST, PRICE, TEAMID) VALUES (2,2,50,'FILIP NOVOTNÝ','GOALKEEPER',1200,2);
