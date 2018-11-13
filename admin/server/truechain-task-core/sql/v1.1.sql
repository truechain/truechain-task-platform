-- tangbinqi 
ALTER TABLE `sys_user`
	ADD COLUMN `recommend_share_code` VARCHAR(255) NULL COMMENT '推荐码' AFTER `recommend_user_id`,
	ADD COLUMN `recommend_resource` VARCHAR(255) NULL COMMENT '推荐源，渠道' AFTER `recommend_share_code`;
	
ALTER TABLE `bs_task`
	ADD COLUMN `review_time` VARCHAR(255) NOT NULL COMMENT '审核时间' AFTER `id`,
	ADD COLUMN `entered_people_num` INT(11) NOT NULL DEFAULT '0' COMMENT '已报名人数',
	ADD COLUMN `completed_people_num` INT(11) NOT NULL DEFAULT '0' COMMENT '已完成人数',
	ADD COLUMN `is_entered_full` SMALLINT(6) NOT NULL DEFAULT '0' COMMENT '是否报名已满(0-未满，1-已满)',
	ADD COLUMN `is_completed_full` SMALLINT(6) NOT NULL DEFAULT '0' COMMENT '是否完成已满(0-未满，1-已满)';

-- shiming
alter table bs_task_user drop column audit_status,drop column audit_result;
ALTER TABLE bs_task_user  ADD `referral_num` decimal(18,0) COMMENT '推荐人奖励';    
ALTER TABLE  bs_task_user  ADD  `finish_level` int(255) COMMENT '完成级别'; 
ALTER TABLE  sys_user  ADD  `wx_image_url` varchar(255) COMMENT '微信头像url';

-- chenqian
CREATE TABLE `config_manage` (
	`id` INT(20) NOT NULL AUTO_INCREMENT,
	`manage_name` VARCHAR(255) NULL DEFAULT NULL COMMENT '管理名称' COLLATE 'utf8_bin',
	`create_id` VARCHAR(255) NULL DEFAULT NULL COMMENT '创建人id' COLLATE 'utf8_bin',
	`type_name` VARCHAR(255) NULL DEFAULT NULL COMMENT '管理类型' COLLATE 'utf8_bin',
	`config_data` VARCHAR(255) NULL DEFAULT NULL COMMENT '数据值' COLLATE 'utf8_bin',
	`config_type` INT(11) NULL DEFAULT NULL COMMENT '数据类型,0-数字，1-字符串，2-枚举',
	`create_time` VARCHAR(255) NULL DEFAULT NULL COMMENT '创建时间',
	`create_user` VARCHAR(255) NULL DEFAULT NULL COMMENT '创建人' COLLATE 'utf8_bin',
	`update_id` VARCHAR(255) NULL DEFAULT NULL COMMENT '更新人ID' COLLATE 'utf8_bin',
	`update_time` VARCHAR(255) NULL DEFAULT NULL COMMENT '更新时间',
	`update_user` VARCHAR(255) NULL DEFAULT NULL COMMENT '更新人员' COLLATE 'utf8_bin',
	PRIMARY KEY (`id`)
)
COMMENT='配置管理'
COLLATE='utf8_bin'
ENGINE=InnoDB
AUTO_INCREMENT=4
;

--zhouduanyang
ALTER TABLE bs_user_account_detail ADD lssuing_state int(11) DEFAULT '0' COMMENT '奖励发放状态（0-未发放，1-发放）';

-- 导出  视图 task_platform_test.v1 结构
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `v1`;
CREATE ALGORITHM=UNDEFINED DEFINER=`admin`@`%` SQL SECURITY DEFINER VIEW `v1` AS select `t`.`id` AS `id`,`t`.`people_num` AS `people_num`,count(`t3`.`id`) AS `entered_people_num`,(`t`.`people_num` = count(`t3`.`id`)) AS `is_entered_full` from ((`bs_task` `t` left join `bs_task_detail` `t2` on(((`t`.`id` = `t2`.`task_id`) and (`t`.`people_num` is not null)))) left join `bs_task_user` `t3` on((`t2`.`id` = `t3`.`task_detail_id`))) where (`t`.`people_num` is not null) group by `t`.`id`;


-- 导出  视图 task_platform_test.v2 结构
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `v2`;
CREATE ALGORITHM=UNDEFINED DEFINER=`admin`@`%` SQL SECURITY DEFINER VIEW `v2` AS select `t`.`id` AS `id`,count(`t3`.`id`) AS `completed_people_num`,(`t`.`people_num` = count(`t3`.`id`)) AS `is_completed_full` from ((`bs_task` `t` left join `bs_task_detail` `t2` on(((`t`.`id` = `t2`.`task_id`) and (`t`.`people_num` is not null)))) left join `bs_task_user` `t3` on(((`t2`.`id` = `t3`.`task_detail_id`) and (`t3`.`task_status` = 4)))) where (`t`.`people_num` is not null) group by `t`.`id`;


-- 导出  视图 task_platform_test.v3 结构
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `v3`;
CREATE ALGORITHM=UNDEFINED DEFINER=`admin`@`%` SQL SECURITY DEFINER VIEW `v3` AS select `v1`.`id` AS `id`,`v1`.`people_num` AS `people_num`,`v1`.`entered_people_num` AS `entered_people_num`,`v1`.`is_entered_full` AS `is_entered_full`,`v2`.`completed_people_num` AS `completed_people_num`,`v2`.`is_completed_full` AS `is_completed_full` from (`v1` join `v2`) where (`v1`.`id` = `v2`.`id`);

ALTER TABLE `bs_task_user`
	CHANGE COLUMN `task_status` `task_status` INT(11) NOT NULL COMMENT '状态(0=未提交,1=待审核,-1=取消,2=审核通过,3=未通过审核,4=已发放)' ;
update bs_task_user set task_Status = 4 where task_Status = 1 and create_time < '2018-11-11'    ;