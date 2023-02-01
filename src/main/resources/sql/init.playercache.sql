CREATE TABLE IF NOT EXISTS `players`
(
    name VARCHAR(255) NOT NULL,
    uuid VARCHAR(36)  NOT NULL,
    PRIMARY KEY (name),
    UNIQUE KEY (uuid)
)