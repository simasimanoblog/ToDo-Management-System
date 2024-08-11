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
		// その月の1日を取得
		LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
		// 1日が何曜日か取得 (1: 月曜日, 7: 日曜日)
		DayOfWeek firstDayOfWeek = firstDayOfMonth.getDayOfWeek();
		// 前月分の日付を取得
		LocalDate startDate = firstDayOfMonth.minusDays(firstDayOfWeek.getValue() % 7);
		 
        // カレンダー作成ループ
        List<LocalDate> weekList = new ArrayList<>();
        LocalDate targetDate = startDate;
        while (true) {
            weekList.add(targetDate);

            // 土曜日（週の終わり）になったら週リストをカレンダーマトリックスに追加
            if (targetDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            	calendar.add(weekList);
                weekList = new ArrayList<>();
            }

            // 次の日を設定
            targetDate = targetDate.plusDays(1);

            // 現在の月末かつ日曜日ならループを抜ける
            if (targetDate.isAfter(currentDate.withDayOfMonth(currentDate.lengthOfMonth())) &&
        		targetDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                //weekList.add(targetDate);
            	//calendar.add(weekList);
                break;
            }
        }
		
		return calendar;
	}

	public Map<LocalDate, List<TasksDto>> getTasksForMonth(LocalDate currentDate) {
		List<Tasks> tasks = taskRepository.findByDateBetween(currentDate.withDayOfMonth(1),
				currentDate.withDayOfMonth(currentDate.lengthOfMonth()));

		Map<LocalDate, List<TasksDto>> taskMap = new HashMap<>();
		for (Tasks task : tasks) {
			LocalDate date = task.getDate();
			taskMap.computeIfAbsent(date, k -> new ArrayList<>()).add(convertToDto(task));
		}

		return taskMap;
	}

	private TasksDto convertToDto(Tasks task) {
		return new TasksDto(task.getId(), task.getTitle(), task.getName(), task.getText(), task.getDate(),
				task.getDone());
	}
}
