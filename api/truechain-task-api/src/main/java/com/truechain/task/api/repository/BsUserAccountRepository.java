package com.truechain.task.api.repository;

import com.truechain.task.model.entity.BsUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BsUserAccountRepository extends JpaRepository<BsUserAccount, Long> {
}
