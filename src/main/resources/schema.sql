DROP TABLE IF EXISTS officers;
CREATE TABLE officers (
    id           INTEGER NOT NULL AUTO_INCREMENT,
    officer_rank VARCHAR(20) NOT NULL,
    first_name   VARCHAR(50) NOT NULL,
    last_name    VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);