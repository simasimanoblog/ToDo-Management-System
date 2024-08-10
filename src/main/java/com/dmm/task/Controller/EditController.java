package com.dmm.task.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.form.EditForm;

//アノテーションを追加
@Controller
public class EditController {
	// @Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	@Autowired
	private TasksRepository tasksRepository;

	/*
	// アノテーション付きのメソッド追加
	@GetMapping("/edit")
	String EditForm(Model model) {
		// ユーザーリスト取得処理を追加
		List<Tasks> tasks = tasksRepository.findAll();
		// 取得したリストをテンプレートに渡す
		model.addAttribute("tasks", tasks);
		// テンプレートは src/main/resources/templates/edit.html とします。
		return "edit";
	}
	*/

	// マッピング設定
	@PostMapping("/editTask")
	public String registerEdit(@Validated EditForm editForm, BindingResult bindingResult) {
		// バリデーションの結果、エラーがあるかどうかチェック
		if (bindingResult.hasErrors()) {
			// エラーがある場合は編集画面を返す
			return "edit";
		}
		Tasks tasks = new Tasks();
		tasks.setTitle(editForm.getTitle());
		tasks.setDate(editForm.getDate());
		tasks.setName(editForm.getName());
		tasks.setText(editForm.getText());
		tasks.setDone(editForm.getDone());

		// データベースに保存
		tasksRepository.save(tasks);
		// ユーザ一覧画面へリダイレクト
		return "redirect:/main";

	}
}
