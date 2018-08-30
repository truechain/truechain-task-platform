package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.BsUserAccount;
import com.truechain.task.model.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BsUserAccountRepository extends JpaRepository<BsUserAccount, Long> {

    BsUserAccount getByUser(SysUser user);
}
