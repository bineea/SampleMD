INSERT INTO `sampledb`.`sample_resource` (`id`, `list`, `menu_id`, `menu_type`, `name`, `request_method`, `url`, `logo_style`) VALUES ('xtgl', '2', NULL, 'COLUMN', '系统管理', NULL, NULL, 'fa fa-gears (alias)');
INSERT INTO `sampledb`.`sample_resource` (`id`, `list`, `menu_id`, `menu_type`, `name`, `request_method`, `url`, `logo_style`) VALUES ('xtgl_ddgl', '3', 'xtgl', 'COLUMN', '调度管理', NULL, NULL, NULL);
INSERT INTO `sampledb`.`sample_resource` (`id`, `list`, `menu_id`, `menu_type`, `name`, `request_method`, `url`, `logo_style`) VALUES ('xtgl_ddgl_ddrw', '1', 'xtgl_ddgl', 'MENU', '调度任务', 'GET', NULL, NULL);
INSERT INTO `sampledb`.`sample_resource` (`id`, `list`, `menu_id`, `menu_type`, `name`, `request_method`, `url`, `logo_style`) VALUES ('xtgl_jsgl', '2', 'xtgl', 'MENU', '角色管理', 'GET', 'app/acl/role/manage', NULL);
INSERT INTO `sampledb`.`sample_resource` (`id`, `list`, `menu_id`, `menu_type`, `name`, `request_method`, `url`, `logo_style`) VALUES ('xtgl_yhgl', '1', 'xtgl', 'MENU', '用户管理', 'GET', 'app/acl/user/manage', NULL);
INSERT INTO `sampledb`.`sample_resource` (`id`, `list`, `menu_id`, `menu_type`, `name`, `request_method`, `url`, `logo_style`) VALUES ('xtgl_zygl', 3, 'xtgl', 'MENU', '资源管理', 'GET', 'app/acl/resource/manage', NULL);

commit;

INSERT INTO `sampledb`.`sample_role`(`id`, `info`, `list`, `name`, `system`) VALUES ('admin', '', 1, '系统管理员', 1);
commit;

INSERT INTO `sampledb`.`sample_role_resource`(`id`, `resource_id`, `role_id`) VALUES ('592a9d2c6dad438db9501145dc7da8ee', 'xtgl', 'admin');
INSERT INTO `sampledb`.`sample_role_resource`(`id`, `resource_id`, `role_id`) VALUES ('4ddd80a75bb44b97a1caebefb4c8e5f8', 'xtgl_ddgl', 'admin');
INSERT INTO `sampledb`.`sample_role_resource`(`id`, `resource_id`, `role_id`) VALUES ('070b15ddbdd74cdbb29ae1133994f1b3', 'xtgl_ddgl_ddrw', 'admin');
INSERT INTO `sampledb`.`sample_role_resource`(`id`, `resource_id`, `role_id`) VALUES ('8090fc154911445bbbe3deaec513eb78', 'xtgl_jsgl', 'admin');
INSERT INTO `sampledb`.`sample_role_resource`(`id`, `resource_id`, `role_id`) VALUES ('85d8fde87acf42a0921b3a74fc6d02d3', 'xtgl_yhgl', 'admin');
INSERT INTO `sampledb`.`sample_role_resource`(`id`, `resource_id`, `role_id`) VALUES ('927c7b41fcfe48d792cc7719c76b8481', 'xtgl_zygl', 'admin');
commit;

INSERT INTO `sampledb`.`sample_user`(`id`, `name`, `male`, `email`, `login_name`, `pass_wd`, `status`, `role_id`, `profile_picture`) VALUES ('default', '管理员', 'MALE', '981344903@qq.com', 'admin', '0LI9I223001894855XX526INN1N2XN74X9LN1912', 'NORMAL', 'admin', NULL);
commit;