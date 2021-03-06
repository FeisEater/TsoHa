CREATE TABLE regusers (
    r_id SERIAL PRIMARY KEY,
    nick varchar(16) NOT NULL CHECK (nick <> '') UNIQUE,
    email varchar(64),
    password bytea NOT NULL,
    salt bytea NOT NULL,
    joined timestamp,
    moderator boolean DEFAULT false,
    avatar bytea DEFAULT NULL
);

CREATE TABLE questions (
    q_id SERIAL PRIMARY KEY,
    title varchar(96) NOT NULL CHECK (title <> ''),
    body text,
    r_id integer REFERENCES regusers ON DELETE SET NULL,
    askerbanned boolean DEFAULT false,
    asked timestamp
);

CREATE TABLE tags (
    t_id SERIAL PRIMARY KEY,
    tag varchar(12) NOT NULL CHECK (tag <> '') UNIQUE,
    firsttagged timestamp
);

CREATE TABLE tagstoquestions (
    t_id integer REFERENCES tags ON DELETE CASCADE,
    q_id integer REFERENCES questions ON DELETE CASCADE,
    PRIMARY KEY (t_id, q_id)
);

CREATE TABLE answers (
    a_id SERIAL PRIMARY KEY,
    body text,
    approvedbyasker boolean DEFAULT false,
    answered timestamp,
    lastedited timestamp,
    r_id integer REFERENCES regusers ON DELETE SET NULL,
    q_id integer REFERENCES questions ON DELETE CASCADE
);

CREATE TABLE flaggedquestions (
    r_id integer REFERENCES regusers ON DELETE SET NULL,
    q_id integer REFERENCES questions ON DELETE CASCADE
);

CREATE TABLE ratedflaggedanswers (
    r_id integer REFERENCES regusers ON DELETE SET NULL,
    a_id integer REFERENCES answers ON DELETE CASCADE,
    flagged boolean,
    rated integer CHECK (rated = 1 OR rated = -1) DEFAULT -1
);
