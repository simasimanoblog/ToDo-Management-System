package com.dmm.task.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.form.CreateForm;

//アノテーションを追加
@Controller
public class CreateController {
	// @Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	@Autowired
	private TasksRepository tasksRepository;

	/*
	// アノテーション付きのメソッド追加
	@GetMapping("/create")
	String RegistForm(Model model) {
		// ユーザーリスト取得処理を追加
		List<Tasks> tasks = tasksRepository.findAll();
		// 取得したリストをテンプレートに渡す
		model.addAttribute("tasks", tasks);
		// テンプレートは src/main/resources/templates/create.html とします。
		return "create";
	}
	*/

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
