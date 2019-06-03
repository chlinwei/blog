/**
友情链接
 */
DROP TABLE IF EXISTS `friendlink`;

CREATE TABLE `friendlink`(
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(60) COMMENT '名称',
  `url` VARCHAR(100) COMMENT '网址'
) DEFAULT CHARSET=utf8;


INSERT INTO `friendlink` VALUES (1,'张海洋博客','https://www.zhyocean.cn/');
INSERT INTO `friendlink` VALUES (2,'阿里巴巴图片库','https://www.iconfont.cn/');
INSERT INTO `friendlink` VALUES (3,'51cto博客','https://blog.51cto.com/');
INSERT INTO `friendlink` VALUES (4,'bilibili','https://www.bilibili.com/');
INSERT INTO `friendlink` VALUES (5,'github','https://github.com/');
INSERT INTO `friendlink` VALUES (6,'shell脚本','https://www.cnblogs.com/f-ck-need-u/p/7048359.html#auto_id_0');
INSERT INTO `friendlink` VALUES (7,'优质图片网站','https://www.pexels.com/zh-cn/');

