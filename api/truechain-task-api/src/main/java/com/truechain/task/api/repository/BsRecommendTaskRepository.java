package com.truechain.task.api.repository;

import com.truechain.task.model.entity.BsRecommendTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BsRecommendTaskRepository extends JpaRepository<BsRecommendTask, Long> {
}
