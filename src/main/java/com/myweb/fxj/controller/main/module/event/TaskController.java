package com.myweb.fxj.controller.main.module.event;

import com.myweb.fxj.common.utils.result.Result;
import com.myweb.fxj.pojo.event.Task;
import com.myweb.fxj.pojo.event.TaskType;
import com.myweb.fxj.service.interfac.main.module.event.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/event")
public class TaskController {
    @Autowired
    private TaskService taskServiceImpl;

    /**
     * 根据typeId获取任务列表
     */
    @GetMapping("/task/{typeId}")
    public Result getTaskList(@PathVariable("typeId") int typeId) {
        return Result.ok(taskServiceImpl.getTaskList(typeId));
    }

    /**
     * 更新任务
     */
    @PostMapping("/task")
    public Result updateTask(@RequestBody Task task) {
        return Result.ok(taskServiceImpl.updateTask(task));
    }

    /**
     * 根据id、year获取任务版列表
     */
    @GetMapping("/taskType/{id}/{year}")
    public Result getTaskTypeList(@PathVariable("id") Integer id,
                                  @PathVariable("year") Integer year) {
        TaskType taskType = new TaskType(id, year);
        return Result.ok(taskServiceImpl.getTaskType(taskType));
    }

    /**
     * 修改或新增任务板
     */
    @PostMapping("/taskType")
    public Result updateTaskTypeList(@RequestBody TaskType taskType) {
        return Result.ok(taskServiceImpl.updateTaskType(taskType));
    }
}
