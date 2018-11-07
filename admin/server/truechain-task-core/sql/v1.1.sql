-- tangbinqi 
ALTER TABLE `sys_user`
	ADD COLUMN `recommend_share_code` VARCHAR(255) NULL COMMENT '推荐码' AFTER `recommend_user_id`,
	ADD COLUMN `recommend_resource` VARCHAR(255) NULL COMMENT '推荐源，渠道' AFTER `recommend_share_code`;
	
ALTER TABLE `bs_task`
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
	`config_type` INT(11) NULL DEFAULT NULL COMMENT '数据id,0-数字，1-字符串，2-枚举',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	`create_user` VARCHAR(255) NULL DEFAULT NULL COMMENT '创建人' COLLATE 'utf8_bin',
	`update_id` VARCHAR(255) NULL DEFAULT NULL COMMENT '更新人ID' COLLATE 'utf8_bin',
	`update_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
	`update_user` VARCHAR(255) NULL DEFAULT NULL COMMENT '更新人员' COLLATE 'utf8_bin',
	PRIMARY KEY (`id`)
)
COMMENT='配置管理'
COLLATE='utf8_bin'
ENGINE=InnoDB
AUTO_INCREMENT=4
;

