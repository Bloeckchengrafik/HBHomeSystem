CREATE TABLE IF NOT EXISTS `homes`
(
    id       INT(11)      NOT NULL AUTO_INCREMENT,
    uuid     VARCHAR(36)  NOT NULL,
    name     VARCHAR(255) NOT NULL,
    x        FLOAT        NOT NULL,
    y        FLOAT        NOT NULL,
    z        FLOAT        NOT NULL,
    yaw      FLOAT        NOT NULL,
    pitch    FLOAT        NOT NULL,
    world    VARCHAR(255) NOT NULL,
    material VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
)