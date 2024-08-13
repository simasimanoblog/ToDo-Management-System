package com.dmm.task.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.entity.TasksDto;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.service.TaskService;

//アノテーションを追加
@Controller
public class MainController {
	// @Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	@Autowired
	private TasksRepository tasksRepository;

	private final TaskService taskService;

	public MainController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping("/main")
    public String getCalendar(@RequestParam(value = "date", required = false) String date, Model model) {
        LocalDate currentDate = date != null ? LocalDate.parse(date) : LocalDate.now();
        List<List<LocalDate>> calendar = taskService.generateCalendar(currentDate);

        // 日付を「yyyy年MM月」の形式でフォーマット
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月");
        String formattedMonth = currentDate.format(formatter);

        Map<LocalDate, List<Tasks>> tasks = tasksRepository.findAll().stream()
                .collect(Collectors.groupingBy(Tasks::getDate));
        
        model.addAttribute("matrix", calendar);
        model.addAttribute("month", formattedMonth);
        model.addAttribute("prev", currentDate.minusMonths(1).withDayOfMonth(1));
        model.addAttribute("next", currentDate.plusMonths(1).withDayOfMonth(1));

        // タスクデータを日付ごとにグループ化してMapに格納
        //Map<LocalDate, List<TasksDto>> tasks = taskService.getTasksForMonth(currentDate);
        model.addAttribute("tasks", tasks);

        return "main";
	}
	
   
    public TasksDto convertToDto(Tasks task) {
        return new TasksDto(task.getId(), task.getTitle(), task.getName(), task.getText(), task.getDate(), task.getDone());
    }

    public Tasks convertToEntity(TasksDto taskDto) {
        return new Tasks(taskDto.getId(), taskDto.getTitle(), taskDto.getName(), taskDto.getText(), taskDto.getDate(), taskDto.getDone());
    }

}
