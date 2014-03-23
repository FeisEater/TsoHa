INSERT INTO regusers VALUES (
    0,
    'FeisEater',
    'myemail@zmail.com',
    'salasana123',
    TIMESTAMP '2004-10-19 10:23:54',
    DEFAULT
);

INSERT INTO regusers VALUES (
    1,
    'NotSureIfTrollin',
    'otheremail@zmail.fi',
    'göalgböa',
    TIMESTAMP '2013-12-01 00:00:01',
    DEFAULT
);

INSERT INTO questions VALUES (
    0,
    'How to dispose of a body?',
    'If there are any policemen reading this post, please be assured that I am merely joking.',
    1,
    TIMESTAMP '2012-12-25 04:23:54',
    13
);

INSERT INTO questions VALUES (
    1,
    'Wash away pee from pants?',
    'I got scared and peed my pants. What is the best way to unpee my pants?',
    0,
    TIMESTAMP '2013-01-01 12:23:54',
    0
);

INSERT INTO tags VALUES (
    0,
    'dead',
    TIMESTAMP '2012-12-25 04:23:54'
);

INSERT INTO tags VALUES (
    1,
    'notguilty',
    TIMESTAMP '2012-12-25 04:23:54'
);

INSERT INTO tags VALUES (
    2,
    'accident',
    TIMESTAMP '2012-12-25 04:23:54'
);

INSERT INTO tags VALUES (
    3,
    'wash',
    TIMESTAMP '2013-01-01 12:23:54'
);

INSERT INTO tagstoquestions VALUES (
    0,
    0
);

INSERT INTO tagstoquestions VALUES (
    1,
    0
);

INSERT INTO tagstoquestions VALUES (
    2,
    0
);

INSERT INTO tagstoquestions VALUES (
    2,
    1
);

INSERT INTO tagstoquestions VALUES (
    3,
    1
);

INSERT INTO answers VALUES (
    0,
    'Talk to your parents.',
    67,
    0,
    true,
    TIMESTAMP '2012-12-25 05:23:54',
    TIMESTAMP '2012-12-25 06:23:54',
    0,
    0
);

INSERT INTO answers VALUES (
    1,
    'Wash your jeans with your tears.',
    0,
    12,
    false,
    TIMESTAMP '2013-01-01 13:23:54',
    TIMESTAMP '2013-01-01 13:23:54',
    1,
    1
);