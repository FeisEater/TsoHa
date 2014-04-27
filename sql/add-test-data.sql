-- Deprecated. Please use app itself to add test-data.
INSERT INTO regusers VALUES (
    DEFAULT,
    'FeisEater',
    'myemail@zmail.com',
    'salasana123',
    TIMESTAMP '2004-10-19 10:23:54',
    false,
    DEFAULT
);

INSERT INTO regusers VALUES (
    DEFAULT,
    'matti',
    'matti@matti.matti',
    'teppo',
    TIMESTAMP '2013-12-01 00:00:01',
    false,
    DEFAULT
);

INSERT INTO regusers VALUES (
    DEFAULT,
    'moderator',
    'mod@quesans.com',
    'moderator',
    TIMESTAMP '2013-12-01 00:00:01',
    true,
    DEFAULT
);

INSERT INTO questions VALUES (
    DEFAULT,
    'How to dispose of a body?',
    'If there are any policemen reading this post, please be assured that I am merely joking.',
    2,
    false,
    TIMESTAMP '2012-12-25 04:23:54'
);

INSERT INTO questions VALUES (
    DEFAULT,
    'Wash away pee from pants?',
    'I got scared and peed my pants. What is the best way to unpee my pants?',
    1,
    false,
    TIMESTAMP '2013-01-01 12:23:54'
);

INSERT INTO tags VALUES (
    DEFAULT,
    'dead',
    TIMESTAMP '2012-12-25 04:23:54'
);

INSERT INTO tags VALUES (
    DEFAULT,
    'notguilty',
    TIMESTAMP '2012-12-25 04:23:54'
);

INSERT INTO tags VALUES (
    DEFAULT,
    'accident',
    TIMESTAMP '2012-12-25 04:23:54'
);

INSERT INTO tags VALUES (
    DEFAULT,
    'wash',
    TIMESTAMP '2013-01-01 12:23:54'
);

INSERT INTO tagstoquestions VALUES (
    1,
    1
);

INSERT INTO tagstoquestions VALUES (
    2,
    1
);

INSERT INTO tagstoquestions VALUES (
    3,
    1
);

INSERT INTO tagstoquestions VALUES (
    3,
    2
);

INSERT INTO tagstoquestions VALUES (
    4,
    2
);

INSERT INTO answers VALUES (
    DEFAULT,
    'Talk to your parents.',
    true,
    TIMESTAMP '2012-12-25 05:23:54',
    TIMESTAMP '2012-12-25 06:23:54',
    1,
    1
);

INSERT INTO answers VALUES (
    DEFAULT,
    'Wash your jeans with your tears.',
    false,
    TIMESTAMP '2013-01-01 13:23:54',
    TIMESTAMP '2013-01-01 13:23:54',
    2,
    2
);