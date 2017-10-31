-- DROP TABLE public.humanplayer;
-- DROP TABLE public.hockeyplayer;
-- DROP TABLE public.game;
-- DROP TABLE public.team;
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE public.humanplayer
(
    id bigint NOT NULL DEFAULT nextval('humanplayer_id_seq'::regclass),
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    passwordhash character varying(255) COLLATE pg_catalog."default" NOT NULL,
    role character varying(255) COLLATE pg_catalog."default" NOT NULL,
    username character varying(20) COLLATE pg_catalog."default" NOT NULL,
    teamid bigint,
    CONSTRAINT humanplayer_pkey PRIMARY KEY (id),
    CONSTRAINT uk_ifpp0sy972jqr6940iqibqrd6 UNIQUE (username),
    CONSTRAINT uk_tl2qqd55vwaj0cefplkk6l85k UNIQUE (email),
    CONSTRAINT fkjtvbx6ytj3utg60kgmv0er0bw FOREIGN KEY (teamid)
        REFERENCES public.team (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.humanplayer
    OWNER to dbuser;
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE public.team
(
    id bigint NOT NULL DEFAULT nextval('team_id_seq'::regclass),
    budget numeric(19, 2) NOT NULL,
    competitioncountry character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    humanplayerid bigint,
    CONSTRAINT team_pkey PRIMARY KEY (id),
    CONSTRAINT uk_qp2b64w1p994jswu2fgfqa5yd UNIQUE (name),
    CONSTRAINT fk4p5x2o1vcot5jr7cjpsukx9nc FOREIGN KEY (humanplayerid)
        REFERENCES public.humanplayer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT team_budget_check CHECK (budget >= 0::numeric)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.team
    OWNER to dbuser;
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE public.game
(
    id bigint NOT NULL DEFAULT nextval('game_id_seq'::regclass),
    firstteamscore integer,
    gamestate character varying(255) COLLATE pg_catalog."default" NOT NULL,
    secondteamscore integer,
    starttime bytea NOT NULL,
    firstteam_id bigint NOT NULL,
    secondteam_id bigint NOT NULL,
    CONSTRAINT game_pkey PRIMARY KEY (id),
    CONSTRAINT fk5vq0a00md19pxxrwdlj3ac8um FOREIGN KEY (secondteam_id)
        REFERENCES public.team (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkhf3tsx3x8jd0hrb3pstmfr4g0 FOREIGN KEY (firstteam_id)
        REFERENCES public.team (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.game
    OWNER to dbuser;
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE public.hockeyplayer
(
    id bigint NOT NULL DEFAULT nextval('hockeyplayer_id_seq'::regclass),
    attackskill integer NOT NULL,
    defenseskill integer NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    post character varying(255) COLLATE pg_catalog."default" NOT NULL,
    price numeric(19, 2) NOT NULL,
    teamid bigint,
    CONSTRAINT hockeyplayer_pkey PRIMARY KEY (id),
    CONSTRAINT uk_oeyirye46sbr249n170rnon42 UNIQUE (name),
    CONSTRAINT fk97ynenmccfk4ykpry0df0qbf7 FOREIGN KEY (teamid)
        REFERENCES public.team (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT hockeyplayer_attackskill_check CHECK (attackskill <= 99 AND attackskill >= 1),
    CONSTRAINT hockeyplayer_defenseskill_check CHECK (defenseskill <= 99 AND defenseskill >= 1),
    CONSTRAINT hockeyplayer_price_check CHECK (price >= 0::numeric)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.hockeyplayer
    OWNER to dbuser;
------------------------------------------------------------------------------------------------------------------------
