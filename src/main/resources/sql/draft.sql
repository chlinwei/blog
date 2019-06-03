/**
草稿表,即未发布的文章存在于草稿表中
 */
DROP TABLE IF EXISTS `draft`;

CREATE TABLE `draft`(
  `id` int PRIMARY KEY  AUTO_INCREMENT,
  `articleTitle` VARCHAR(255) COMMENT '文章名',
  `articleContent` LONGTEXT COMMENT '文章内容',
  `articleTags` VARCHAR(255) COMMENT '文章标签,最多可以设置5个,每个以;分割,每个最大长度为20个字符',
  `articleType` VARCHAR(255) COMMENT '文章类型:原创，转载',
  `createTime` VARCHAR(20)  NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间,用varchar类型可以去掉.0',
  `updateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `summary` VARCHAR(1500) COMMENT '文章摘要,不填,默认为内容前200字',
  `customTypeId` INT COMMENT '文章类型的id,自定义的类型',
  `userId` int NOT NULL COMMENT '文章是属于哪个用户的'
) DEFAULT CHARSET=utf8;


INSERT INTO `draft`(articleTitle, articleContent, articleTags, articleType, summary, customTypeId, userId)
    VALUES ('草稿1','内容1','测试','原创','摘要',1,1);

INSERT INTO `draft`(articleTitle, articleContent, articleTags, articleType, summary, customTypeId, userId)
VALUES ('草稿2','内容2','测试','原创','摘要',2,1);

INSERT INTO `draft`(articleTitle, articleContent, articleTags, articleType, summary, customTypeId, userId)
VALUES ('草稿3','内容3','测试','原创','摘要',3,1);

INSERT INTO `draft`(articleTitle, articleContent, articleTags, articleType, summary, customTypeId, userId)
VALUES ('草稿4','内容4','测试','原创','摘要',3,1);

INSERT INTO `draft`(articleTitle, articleContent, articleTags, articleType, summary, customTypeId, userId)
VALUES ('草稿5','内容5','测试','原创','摘要',3,1);

INSERT INTO `draft`(articleTitle, articleContent, articleTags, articleType, summary, customTypeId, userId)
VALUES ('草稿6','内容6','测试','原创','摘要',3,1);

INSERT INTO `draft`(articleTitle, articleContent, articleTags, articleType, summary, customTypeId, userId)
VALUES ('草稿7','内容7','测试','原创','摘要',3,1);

INSERT INTO `draft`(articleTitle, articleContent, articleTags, articleType, summary, customTypeId, userId)
VALUES ('草稿8','内容8','测试','原创','摘要',3,1);

INSERT INTO `draft`(articleTitle, articleContent, articleTags, articleType, summary, customTypeId, userId)
VALUES ('草稿9','内容9','测试','原创','摘要',3,1);

INSERT INTO `draft`(articleTitle, articleContent, articleTags, articleType, summary, customTypeId, userId)
VALUES ('草稿10','内容10','测试','原创','摘要',3,1);

INSERT INTO `draft`(articleTitle, articleContent, articleTags, articleType, summary, customTypeId, userId)
VALUES ('草稿11','内容11','测试','原创','摘要',3,1);

INSERT INTO `draft`(articleTitle,  userId) VALUES ('草稿12',1);

