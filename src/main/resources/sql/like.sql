DROP TABLE IF EXISTS `like`;
CREATE TABLE `like`(
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `typeId` int NOT NULL COMMENT '文章id或者评论id',
  `ownerId` int NOT NULL COMMENT '文章创建者id或者评论创建者id',
  `type` INT NOT NULL COMMENT '点赞类型1:文章,2:评论',
  `userId` int NOT NULL COMMENT '用户id'
)DEFAULT CHARSET=utf8;



/**
用户1给第1篇文章点赞
 */
INSERT `like`(typeId,ownerId, type, userId) VALUES (1,1,1,1);

/**
用户2给第1,2篇文章点赞
 */
INSERT `like`(typeId,ownerId, type, userId) VALUES (1,1,1,2);
INSERT `like`(typeId,ownerId, type, userId) VALUES (2,1,1,2);




/**
用户1给第1篇文章的第1条评论点赞
 */

INSERT `like`(typeId,ownerId, type, userId) VALUES (1,1,2,1);

/**
用户2给第1篇文章的第二条评论点赞
 */
INSERT `like`(typeId,ownerId, type, userId) VALUES (2,2,2,2);


/*
用户1给id为11的回复点赞
 */
INSERT `like`(typeId,ownerId, type, userId) VALUES (11,2,2,1);
