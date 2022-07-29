package com.myweb.fxj.service.interfac.main.module.event;

import com.myweb.fxj.pojo.event.Task;
import com.myweb.fxj.pojo.event.TaskType;

import java.util.List;

public interface TaskService {
    /**
     * 根据任务类型获取所有任务
     */
    List<Task> getTaskList(int typeId);

    /**
     * 查询任务类型
     */
    List<TaskType> getTaskType(TaskType taskType);

    /**
     * 更新任务
     */
    int updateTask(Task task);

    /**
     * 更新任务版
     */
    int updateTaskType(TaskType taskType);

}
