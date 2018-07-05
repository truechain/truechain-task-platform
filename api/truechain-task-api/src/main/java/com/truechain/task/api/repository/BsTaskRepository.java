package com.truechain.task.api.repository;

import com.truechain.task.model.entity.BsTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BsTaskRepository extends JpaRepository<BsTask, Long> {
}
