package com.dmm.task.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.entity.Users;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.form.EditForm;
import com.dmm.task.service.AccountUserDetails;

//アノテーションを追加
@Controller
public class EditController {
	// @Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	@Autowired
	private TasksRepository tasksRepository;

	@GetMapping("/main/edit/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model) {

		//idに該当するタスクが存在する場合は変数に退避
		Tasks task = tasksRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));

		//パラメータ.id
		model.addAttribute("id", id);
		//DB.タイトル
		model.addAttribute("title", task.getTitle());
		//DB.日付
		model.addAttribute("date", task.getDate());
		//DB.ユーザー名
		model.addAttribute("name", task.getName());
		//DB.タスク内容
		model.addAttribute("text", task.getText());
		//画面.実行フラグ
		model.addAttribute("done", task.getDone());
		//タスク
		model.addAttribute("task", task);
		return "edit"; // edit.html にレンダリング
	}

	// マッピング設定
	@PostMapping("/main/edit/{id}")
	public String updateTask(@Valid @ModelAttribute("task") EditForm editForm, BindingResult result, Model model) {
		// バリデーションの結果、エラーがあるかどうかチェック
		if (result.hasErrors()) {
			// エラーがある場合は編集画面を返す
			return "edit";
		}

		// ログインユーザー情報
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AccountUserDetails userDetails = (AccountUserDetails) auth.getPrincipal();
		Users loginUser = userDetails.getUser();
		// タスク
		Tasks tasks = new Tasks();
		// 画面.タイトル
		tasks.setId(editForm.getId());
		// 画面.タイトル
		tasks.setTitle(editForm.getTitle());
		// 画面.タイトル
		tasks.setDate(editForm.getDate());
		// ログインユーザー.名前
		tasks.setName(loginUser.getName());
		// 画面.内容
		tasks.setText(editForm.getText());
		// 画面.実行フラグ
		tasks.setDone(editForm.getDone());

		// データベースに保存
		tasksRepository.save(tasks);
		// ユーザ一覧画面へリダイレクト
		return "redirect:/main";

	}
	

	// deleteTasksメソッドを追加
	// リクエストマッピング設定を追加
	@PostMapping("/main/delete/{id}")
	// 処理の中でidを使えるように、引数にidを追加
	public String deleteTask(@PathVariable Long id) {
		// 指定したIDのユーザーを削除
		tasksRepository.deleteById(id);
		return "redirect:/main";
	}
}
