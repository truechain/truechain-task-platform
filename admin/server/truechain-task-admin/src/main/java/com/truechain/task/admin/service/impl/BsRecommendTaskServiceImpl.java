package com.truechain.task.admin.service.impl;

import com.google.common.collect.Maps;
import com.truechain.task.admin.repository.BsRecommendTaskRepository;
import com.truechain.task.admin.service.BsRecommendTaskService;
import com.truechain.task.model.entity.BsRecommendTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class BsRecommendTaskServiceImpl implements BsRecommendTaskService {

    @Autowired
    private BsRecommendTaskRepository bsRecommendTaskRepository;

    @Override
    public Map<Long,BsRecommendTask> getMyRecommendUser(Collection<Long> ids) {
        List<BsRecommendTask> bsRecommendTasks = bsRecommendTaskRepository.getMyRecommendUser(ids);
        Map<Long,BsRecommendTask> map = Maps.newHashMap();
        bsRecommendTasks.forEach(item->{
            map.put(item.getRecommendUser().getId(),item);
        });
        return map;
    }

    @Override
    public Map<Long,Integer> getMyRecommendCount(Collection<Long> ids) {
        Map<Long,Integer> result = Maps.newHashMap();
        List<Object> objects = bsRecommendTaskRepository.getMyRecommendCount(ids);
        objects.forEach(obj->{
            Object[] objArray = (Object[])obj;
            Long id = Long.parseLong(objArray[0].toString());
            Integer count = Integer.parseInt(objArray[1].toString());
            result.put(id,count);
        });
        return result;
    }
}
