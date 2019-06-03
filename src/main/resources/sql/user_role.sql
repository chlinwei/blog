DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role(
  userId int NOT NULL COMMENT '用户id',
  roleId int NOT NULL COMMENT '角色id'
)DEFAULT CHARSET=utf8;


INSERT INTO user_role(userId,roleId)VALUES (1,1);
INSERT INTO user_role(userId,roleId)VALUES (1,3);
INSERT INTO user_role(userId,roleId)VALUES (2,1);



INSERT INTO user_role(userId,roleId)VALUES (3,1);
INSERT INTO user_role(userId,roleId)VALUES (4,1);
INSERT INTO user_role(userId,roleId)VALUES (5,1);
