DROP TABLE IF EXISTS role;
CREATE TABLE role(
  id int PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) UNIQUE NOT NULL
)DEFAULT CHARSET=utf8;


INSERT INTO role(id,name)VALUES (1,'ROLE_USER');
INSERT INTO role(id,name)VALUES (2,'ROLE_ADMIN');
INSERT INTO role(id,name)VALUES (3,'ROLE_SUPERADMIN');
