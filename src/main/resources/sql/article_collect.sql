DROP TABLE IF EXISTS `article_collect`;
/**
文章和收藏表是多对多的关系
 */
CREATE TABLE `article_collect`(
  `articleId` int NOT NULL COMMENT '文章id',
  `authorId` int NOT NULL COMMENT '文章作者id',
  `collectId` int NOT NULL COMMENT '收藏夹id',
  `userId` INT NOT NULL COMMENT '用户id,虽然通过收藏夹id也能判断该条记录是不是属于该用户的,但是很麻烦'
)DEFAULT CHARSET=utf8;




/**
用户1的默认收藏夹里收藏了10篇文章
 */
INSERT INTO article_collect(articleId,authorId, collectId,userId) VALUES (1,1,1,1);
INSERT INTO article_collect(articleId,authorId, collectId,userId) VALUES (2,1,1,1);
INSERT INTO article_collect(articleId,authorId, collectId,userId) VALUES (3,1,1,1);
INSERT INTO article_collect(articleId,authorId, collectId,userId) VALUES (4,1,1,1);
INSERT INTO article_collect(articleId,authorId, collectId,userId) VALUES (5,1,1,1);
INSERT INTO article_collect(articleId,authorId, collectId,userId) VALUES (6,1,1,1);
INSERT INTO article_collect(articleId,authorId, collectId,userId) VALUES (7,1,1,1);
INSERT INTO article_collect(articleId,authorId, collectId,userId) VALUES (8,1,1,1);
INSERT INTO article_collect(articleId,authorId, collectId,userId) VALUES (9,1,1,1);
INSERT INTO article_collect(articleId,authorId, collectId,userId) VALUES (10,1,1,1);


/**
用户1的第二个收藏夹里收藏了两篇文章
 */
INSERT INTO article_collect(articleId,authorId, collectId,userId) VALUES (1,1,7,1);
INSERT INTO article_collect(articleId,authorId, collectId,userId) VALUES (2,1,7,1);


