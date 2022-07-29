package com.myweb.fxj.service.impl.main.module.event;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.myweb.fxj.mapper.module.event.TaskMapper;
import com.myweb.fxj.mapper.module.event.TaskTypeMapper;
import com.myweb.fxj.pojo.event.Task;
import com.myweb.fxj.pojo.event.TaskType;
import com.myweb.fxj.pojo.status.Notification;
import com.myweb.fxj.service.interfac.main.module.event.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskTypeMapper taskTypeMapper;

    @Override
    public List<Task> getTaskList(int typeId) {
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_id", typeId);
        queryWrapper.eq("is_deleted", 0);
        return taskMapper.selectList(queryWrapper);
    }

    @Override
    public List<TaskType> getTaskType(TaskType taskType) {
        QueryWrapper<TaskType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", taskType.getId());
        queryWrapper.eq("is_deleted", 0);
        // 如果含有类型与年份限制，增加查询条件
        if(StringUtils.hasLength(taskType.getType())) queryWrapper.eq("type", taskType.getType());
        if(StringUtils.hasLength(taskType.getYear().toString())) queryWrapper.eq("year", taskType.getYear());
        return taskTypeMapper.selectList(queryWrapper);
    }

    public int updateTask(Task task) {
        // 无num则是添加
        if(task.getNum() == null) return taskMapper.insert(task);
        // 有num是更新
        else {
            UpdateWrapper<Task> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("num", task.getNum());
            return taskMapper.update(task, updateWrapper);
        }
    }

    public int updateTaskType(TaskType taskType) {
        System.out.println(taskType);
        if(taskType.getTypeId() == null) return taskTypeMapper.insert(taskType);
        else {
            UpdateWrapper<TaskType> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("type_id", taskType.getTypeId());
            return taskTypeMapper.update(taskType, updateWrapper);
        }
    }
}
