DROP TABLE IF EXISTS article;

CREATE TABLE article(
  id int PRIMARY KEY  AUTO_INCREMENT,
  articleTitle VARCHAR(255) NOT NULL COMMENT '文章名',
  articleContent LONGTEXT NOT NULL COMMENT '文章内容',
  articleTags VARCHAR(255) NOT NULL COMMENT '文章标签,最多可以设置5个,每个以;分割,每个最大长度为20个字符',
  articleType VARCHAR(255) NOT NULL COMMENT '文章类型:原创，转载',
  `createTime` VARCHAR(20)  NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间,用varchar类型可以去掉.0',
  `updateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  summary VARCHAR(1500) COMMENT '文章摘要,不填,默认为内容前200字',
  customTypeId INT NOT NULL  COMMENT '文章类型的id,自定义的类型',
  userId int NOT NULL COMMENT '文章是属于哪个用户的'
)DEFAULT CHARSET=utf8;


/**
原创20篇
 */

INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题1','内容1','开发;运维','原创','摘要1',1,1);

INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题2','内容2','开发;运维','原创','摘要2',2,1);


INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题3','内容3','开发;运维','原创','摘要1',3,1);

INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题4','内容4','开发;运维','原创','摘要4',4,1);


INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题5','内容5','开发;运维','原创','摘要5',1,1);


INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题6','内容6','开发;运维','原创','摘要5',2,1);


INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题7','内容7','开发;运维','原创','摘要5',3,1);

INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题8','内容8','开发;运维','原创','摘要5',4,1);

INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题9','内容9','开发;运维','原创','摘要5',1,1);

INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题10','内容10','开发;运维','原创','摘要5',2,1);
INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题11','内容11','开发;运维','原创','摘要5',3,1);

INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题12','内容12','开发;运维','原创','摘要5',4,1);

INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题13','内容13','开发;运维','原创','摘要5',1,1);

INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题14','内容14','开发;运维','原创','摘要5',2,1);

INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
summary, customTypeId, userId)
VALUES ('标题15','内容15','开发;运维','原创','摘要5',3,1);

INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题16','内容16','开发;运维','原创','摘要5',4,1);


/**
转载
 */


INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题17','内容17','开发;运维','转载','摘要5',1,1);

INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题18','内容18','开发;运维','转载','摘要5',2,1);

INSERT INTO article(articleTitle, articleContent, articleTags, articleType,
                    summary, customTypeId, userId)
VALUES ('标题19','内容19','开发;运维','转载','摘要5',3,1);


