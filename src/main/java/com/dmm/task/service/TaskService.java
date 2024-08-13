package com.dmm.task.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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
                break;
            }
        }
		
		return calendar;
	}
}
