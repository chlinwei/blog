DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `userName` VARCHAR(255) UNIQUE  NOT NULL COMMENT '用户名',
  `passwd` VARCHAR(255) NOT NULL COMMENT '密码',
  `gender` CHAR(4) COMMENT '性别,可以为男,女,未知',
  `avatarImgUri` VARCHAR(255) COMMENT '头像的url',
  `personalBrief` VARCHAR(1000) COMMENT '个人简介',
  `birthday` VARCHAR(255) COMMENT '生日',
  `email` VARCHAR(255) UNIQUE COMMENT '邮箱',
  `phone` VARCHAR(20) UNIQUE COMMENT '电话',
  `lastLoginTime` VARCHAR(50) COMMENT '最近登录时间',
  `sign` VARCHAR(50) COMMENT '个性签名',
  `createTime` VARCHAR(20)  NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间,用varchar类型可以去掉.0'
)DEFAULT CHARSET=utf8 ;
INSERT INTO user(email, phone, userName, passwd, gender, avatarImgUri, personalBrief,birthday) VALUES (
    '862350707@qq.com','15345710576','lwlw','54F8350C9CED3646D68689030AD13685','女','','我叫lw,毕业于湖北科技学院','2017-10-18'
);


INSERT INTO user(email, phone, userName, passwd, gender, avatarImgUri, personalBrief,birthday) VALUES (
  '525345@qq.com','14345710576','机器人1','54F8350C9CED3646D68689030AD13685','女','','我是机器人1,毕业于北京大学','2018-11-19'
);

INSERT INTO user(email, phone, userName, passwd, gender, avatarImgUri, personalBrief,birthday) VALUES (
  '5253415@qq.com','143457105761','机器人2','54F8350C9CED3646D68689030AD13685','女','','我是机器人2,毕业于北京大学','2018-11-19'
);
INSERT INTO user(email, phone, userName, passwd, gender, avatarImgUri, personalBrief,birthday) VALUES (
  '523253415@qq.com','14354457105761','机器人3','54F8350C9CED3646D68689030AD13685','女','','我是机器人3,毕业于北京大学','2018-11-19'
);
INSERT INTO user(email, phone, userName, passwd, gender, avatarImgUri, personalBrief,birthday) VALUES (
  '5253413415@qq.com','1637105761','机器人4','54F8350C9CED3646D68689030AD13685','女','','我是机器人4,毕业于北京大学','2018-11-19'
);
INSERT INTO user(email, phone, userName, passwd, gender, avatarImgUri, personalBrief,birthday) VALUES (
  '225345@qq.com','12345710576','机器人5','54F8350C9CED3646D68689030AD13685','男','','我叫机器人5,毕业于清华大学','2018-11-19'
);

INSERT INTO user(email, phone, userName, passwd, gender, avatarImgUri, personalBrief,birthday) VALUES (
  '25625345@qq.com','15456718598','机器人6','54F8350C9CED3646D68689030AD13685','女','','我叫机器人6,毕业于清华大学','2018-11-19'
);




