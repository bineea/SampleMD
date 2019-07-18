# Dump of table sample_comment
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sample_user`;
DROP TABLE IF EXISTS `sample_role_resource`;
DROP TABLE IF EXISTS `sample_resource`;
DROP TABLE IF EXISTS `sample_role`;

CREATE TABLE `sample_resource` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `list` int(11) NOT NULL COMMENT '菜单排序',
  `menu_id` varchar(32) DEFAULT NULL COMMENT '对应上级菜单id',
  `menu_type` varchar(10) NOT NULL COMMENT '菜单类型',
  `name` varchar(200) NOT NULL COMMENT '资源名称',
  `request_method` varchar(10) DEFAULT NULL COMMENT '资源请求方式',
  `url` varchar(300) DEFAULT NULL COMMENT '链接',
  `logo_style` varchar(128) DEFAULT NULL COMMENT 'logo样式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '菜单资源表';

CREATE TABLE `sample_role` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `info` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `list` int(11) NOT NULL COMMENT '排序',
  `name` varchar(30) NOT NULL COMMENT '角色名称',
  `system` int(1) NOT NULL COMMENT '是否系统自带',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '角色表';

CREATE TABLE `sample_role_resource` (
  `id` varchar(32) NOT NULL,
  `resource_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `resource_id` (`resource_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `resource_id` FOREIGN KEY (`resource_id`) REFERENCES `sample_resource` (`id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `sample_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sample_user` (
`id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称' ,
`male`  varchar(16) NULL DEFAULT NULL COMMENT '性别' ,
`email`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱' ,
`login_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号' ,
`pass_wd`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码' ,
`status`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号状态' ,
`role_id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id' ,
`profile_picture` blob NULL DEFAULT NULL COMMENT '头像',
PRIMARY KEY (`id`),
UNIQUE INDEX `email` (`email`) USING BTREE ,
UNIQUE INDEX `loginName` (`login_name`) USING BTREE,
CONSTRAINT `roleId` FOREIGN KEY (`role_id`) REFERENCES `sample_role` (`id`)
)
ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci COMMENT='用户表，用于保存用户信息。';