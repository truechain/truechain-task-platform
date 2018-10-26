-- tangbinqi 
ALTER TABLE `sys_user`
	ADD COLUMN `recommend_share_code` VARCHAR(255) NULL COMMENT '推荐码' AFTER `recommend_user_id`,
	ADD COLUMN `recommend_resource` VARCHAR(255) NULL COMMENT '推荐源，渠道' AFTER `recommend_share_code`;
	
ALTER TABLE `bs_task`
	ADD COLUMN `entered_people_num` INT(11) NOT NULL DEFAULT '0' COMMENT '已报名人数',
	ADD COLUMN `completed_people_num` INT(11) NOT NULL DEFAULT '0' COMMENT '已完成人数',
	ADD COLUMN `is_entered_full` SMALLINT(6) NOT NULL DEFAULT '0' COMMENT '是否报名已满(0-未满，1-已满)',
	ADD COLUMN `is_completed_full` SMALLINT(6) NOT NULL DEFAULT '0' COMMENT '是否完成已满(0-未满，1-已满)';

--	ALTER TABLE `sys_user`
--	DROP COLUMN `recommend_share_code`,
--	DROP COLUMN `recommend_share_code2`;
