DROP TABLE IF EXISTS `collect`;

/**
收藏夹表,收藏夹和文章是多对多的关系
硬性规定:每个用户最多100个收藏夹,每个收藏夹最多包含500篇文章
 */
create table `collect`(
  `id` int primary key auto_increment,
  `name` varchar(50) not null comment '收藏夹的名称',
  `brief` varchar(500) comment '收藏夹简介',
  `userId` int not null comment '用户id,即这个收藏夹是属于谁',
  `createtime` varchar(20)  not null  default current_timestamp comment '创建时间',
  `isDefault` int DEFAULT 0 COMMENT '用这个字段来标识默认收藏夹,如果为1:表示默认收藏夹,如果时0:表示普通收藏夹'
)DEFAULT CHARSET=utf8;


/**
创建默认收藏夹
 */

INSERT INTO `collect`(name, brief, userId,isDefault)VALUES ('默认收藏夹','默认收藏夹',1,1);
INSERT INTO `collect`(name, brief, userId,isDefault)VALUES ('默认收藏夹','默认收藏夹',2,1);
INSERT INTO `collect`(name, brief, userId,isDefault)VALUES ('默认收藏夹','默认收藏夹',3,1);
INSERT INTO `collect`(name, brief, userId,isDefault)VALUES ('默认收藏夹','默认收藏夹',4,1);
INSERT INTO `collect`(name, brief, userId,isDefault)VALUES ('默认收藏夹','默认收藏夹',5,1);
INSERT INTO `collect`(name, brief, userId,isDefault)VALUES ('默认收藏夹','默认收藏夹',6,1);



/**
用户1的自定义收藏夹
*/
INSERT INTO `collect`(name, brief, userId) VALUES ('java','java开发',1);
INSERT INTO `collect`(name, brief, userId) VALUES ('linux','linux运维',1);
INSERT INTO `collect`(name, brief, userId) VALUES ('故事','我的故事',1);




