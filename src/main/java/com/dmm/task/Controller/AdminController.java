package com.dmm.task.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.entity.TasksDto;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.service.TaskService;


@RestController
@RequestMapping("/admin")
public class AdminController {
	// @Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	@Autowired
	private TasksRepository tasksRepository;

    private final TaskService taskService;

    public AdminController(TaskService taskService) {
        this.taskService = taskService;
    }
    public TasksDto convertToDto(Tasks task) {
        return new TasksDto(task.getId(), task.getTitle(), task.getName(), task.getText(), task.getDate(), task.getDone());
    }

    public Tasks convertToEntity(TasksDto taskDto) {
        return new Tasks(taskDto.getId(), taskDto.getTitle(), taskDto.getName(), taskDto.getText(), taskDto.getDate(), taskDto.getDone());
    }
}