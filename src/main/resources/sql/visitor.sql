DROP TABLE  IF EXISTS `visitor`;

/**
最近访问表
 */
CREATE TABLE `visitor` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `visitorId` int NOT NULL COMMENT '最近访问的用户id',
  `visitTime` VARCHAR(30)  NOT NULL COMMENT '最近访问的时间',
  `userId` INT NOT NULL COMMENT '用户id,即这条记录是属于该用的'
) DEFAULT CHARSET=utf8;

/**
用户1的主页有6人访问
 */
INSERT INTO  visitor(visitorId, visitTime, userId) VALUES (
    2,'2018-19-23 14:34:06',1
);

INSERT INTO  visitor(visitorId, visitTime, userId) VALUES (
  3,'2018-19-23 14:34:06',1
);

INSERT INTO  visitor(visitorId, visitTime, userId) VALUES (
  4,'2018-19-23 14:34:06',1
);

INSERT INTO  visitor(visitorId, visitTime, userId) VALUES (
  5,'2018-19-23 14:34:06',1
);

INSERT INTO  visitor(visitorId, visitTime, userId) VALUES (
  6,'2018-19-23 14:34:06',1
);


INSERT INTO  visitor(visitorId, visitTime, userId) VALUES (
  7,'2018-19-23 14:34:06',1
);



/**
用户2的主页有2人访问
 */
INSERT INTO  visitor(visitorId, visitTime, userId) VALUES (
  1,'2018-19-23 14:34:06',2
);

INSERT INTO  visitor(visitorId, visitTime, userId) VALUES (
  3,'2018-19-23 14:34:06',2
);

