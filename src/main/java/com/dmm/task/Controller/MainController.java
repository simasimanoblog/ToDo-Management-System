package com.dmm.task.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.repository.TasksRepository;

//アノテーションを追加
@Controller
public class MainController {
	// @Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	@Autowired
	private TasksRepository tasksRepository;

	// アノテーション付きのメソッド追加
	@GetMapping("/main")
	String MainForm() {
		return "main";
	}

	/*
	// アノテーション付きのメソッド追加
	@GetMapping("/create")
	String RegistForm(Model model) {
		// Modelに空のUserFormを追加
		CreateForm createForm = new CreateForm();
		model.addAttribute("createForm", createForm);
		// テンプレートは src/main/resources/templates/create.html とします。
		return "create";
	}

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

	// deleteTasksメソッドを追加
	// リクエストマッピング設定を追加
	@PostMapping("/main/delete/{id}")
	// 処理の中でidを使えるように、引数にidを追加
	public String deleteTasks(@PathVariable Long id) {
		// 指定したIDのユーザーを削除
		tasksRepository.deleteById(id);
		return "redirect:/main";
	}

}
