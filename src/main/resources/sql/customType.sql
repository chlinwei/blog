DROP TABLE IF EXISTS customType;
CREATE TABLE customType(
  id int PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL COMMENT '文章分类的名称,自定义分类名称',
  userId int NOT NULL COMMENT '用户id'
)DEFAULT CHARSET=utf8;

INSERT INTO customType(id, name,userId) VALUES (1,'java开发',1);
INSERT INTO customType(id, name,userId) VALUES (2,'linux运维',1);
INSERT INTO customType(id, name,userId) VALUES (3,'运营',1);
INSERT INTO customType(id, name,userId) VALUES (4,'销售',1);

INSERT INTO customType(id, name,userId) VALUES (5,'java',2);
INSERT INTO customType(id, name,userId) VALUES (6,'linux',2);
INSERT INTO customType(id, name,userId) VALUES (7,'云服务开发高级工程师',2);
INSERT INTO customType(id, name,userId) VALUES (8,'项目经理',2);
