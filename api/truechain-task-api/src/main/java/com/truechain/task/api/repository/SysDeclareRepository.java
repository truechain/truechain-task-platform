package com.truechain.task.api.repository;

import com.truechain.task.model.entity.SysDeclare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysDeclareRepository extends JpaRepository<SysDeclare, Long> {

    SysDeclare findFirstByOrderByVersionDesc();
}
