package com.dmm.task.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.entity.TasksDto;
import com.dmm.task.data.repository.TasksRepository;

@Service
public class TaskService {

    // 既存のメソッドと同様にリポジトリを注入
    private final TasksRepository taskRepository;

    public TaskService(TasksRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<List<LocalDate>> generateCalendar(LocalDate currentDate) {
        List<List<LocalDate>> calendar = new ArrayList<>();
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        DayOfWeek firstDayOfWeek = firstDayOfMonth.getDayOfWeek();
        LocalDate startDate = firstDayOfMonth.minusDays(firstDayOfWeek.getValue() % 7);

        for (int week = 0; week < 6; week++) {
            List<LocalDate> weekList = new ArrayList<>();
            for (int day = 0; day < 7; day++) {
                weekList.add(startDate);
                startDate = startDate.plusDays(1);
            }
            calendar.add(weekList);
        }
        return calendar;
    }

    public Map<LocalDate, List<TasksDto>> getTasksForMonth(LocalDate currentDate) {
        List<Tasks> tasks = taskRepository.findByDueDateBetween(
                currentDate.withDayOfMonth(1),
                currentDate.withDayOfMonth(currentDate.lengthOfMonth())
        );

        Map<LocalDate, List<TasksDto>> taskMap = new HashMap<>();
        for (Tasks task : tasks) {
        	LocalDate date = task.getDate();
            taskMap.computeIfAbsent(date, k -> new ArrayList<>()).add(convertToDto(task));
        }

        return taskMap;
    }

    private TasksDto convertToDto(Tasks task) {
        return new TasksDto(task.getId(), task.getTitle(), task.getName(), task.getText(), task.getDate(), task.getDone());
    }
}
