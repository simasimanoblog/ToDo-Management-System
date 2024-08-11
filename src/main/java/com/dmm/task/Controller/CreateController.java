package com.dmm.task.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.form.CreateForm;

//アノテーションを追加
@Controller
public class CreateController {
	// @Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	@Autowired
	private TasksRepository tasksRepository;

	// アノテーション付きのメソッド追加
	@GetMapping("/main/create")
	String showCreateForm(@RequestParam("date") LocalDate date, Model model) {
		Tasks tasks = new Tasks();
		tasks.setDate(date);
		// データベースに保存
		tasksRepository.save(tasks);
				
		// 必要に応じて、新しいタスクのデフォルト値などをセットする
		//model.addAttribute("date", date);
		//model.addAttribute("task", new Tasks());  // TaskDtoはフォームのデータバインド用
        // テンプレートは src/main/resources/templates/create.html とします。
		return "create";
	}
	
	// マッピング設定
	@PostMapping("/create")
	public String registerCreate(@Validated CreateForm createForm, BindingResult bindingResult) {
		// バリデーションの結果、エラーがあるかどうかチェック
		if (bindingResult.hasErrors()) {
			// エラーがある場合は編集画面を返す
			return "create";
		}
		Tasks tasks = new Tasks();
		tasks.setTitle(createForm.getTitle());
		tasks.setDate(createForm.getDate());
		tasks.setName(createForm.getName());
		tasks.setText(createForm.getText());

		// データベースに保存
		tasksRepository.save(tasks);
		// ユーザ一覧画面へリダイレクト
		return "redirect:/main";

	}
	
}
