CREATE TABLE regusers (
    r_id SERIAL PRIMARY KEY,
    nick varchar(16) NOT NULL CHECK (nick <> '') UNIQUE,
    email varchar(64),
    password varchar(128) NOT NULL CHECK (password <> ''),
    joined timestamp,
    avatar bytea DEFAULT NULL
);

CREATE TABLE questions (
    q_id SERIAL PRIMARY KEY,
    title varchar(96) NOT NULL CHECK (title <> ''),
    body text,
    r_id integer REFERENCES regusers,
    asked timestamp,
    flags integer CHECK (flags > -1) DEFAULT 0
);

CREATE TABLE tags (
    t_id SERIAL PRIMARY KEY,
    tag varchar(12) NOT NULL CHECK (tag <> '') UNIQUE,
    firsttagged timestamp
);

CREATE TABLE tagstoquestions (
    t_id integer REFERENCES tags,
    q_id integer REFERENCES questions,
    PRIMARY KEY (t_id, q_id)
);

CREATE TABLE answers (
    a_id SERIAL PRIMARY KEY,
    body text,
    rating integer CHECK (rating > -1) DEFAULT 0,
    flags integer CHECK (flags > -1) DEFAULT 0,
    approvedbyasker boolean DEFAULT false,
    answered timestamp,
    lastedited timestamp,
    r_id integer REFERENCES regusers,
    q_id integer REFERENCES questions
);