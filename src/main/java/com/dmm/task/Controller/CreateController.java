package com.dmm.task.Controller;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.dmm.task.form.CreateForm;

//アノテーションを追加
@Controller
public class CreateController {
	// @Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	@Autowired
	private TasksRepository tasksRepository;

	@GetMapping("/main/create/{date}")
	public String showCreateForm(@PathVariable("date") String dateStr, Model model) {
		LocalDate date = LocalDate.parse(dateStr);
		model.addAttribute("date", date);
		model.addAttribute("task", new CreateForm());
		return "create"; // createTaskForm.html にレンダリング
	}

	// マッピング設定
	@PostMapping("/main/create")
	public String registerCreate(HttpSession session, @ModelAttribute("task") CreateForm createForm,
			BindingResult result, Model model) {
		// バリデーションの結果、エラーがあるかどうかチェック
		if (result.hasErrors()) {
			// エラーがある場合は編集画面を返す
			return "create";
		}

		// セッションからログインユーザー情報を取得
		Users loginUser = (Users) session.getAttribute("LoginUser");
		if (loginUser != null) {

			Tasks tasks = new Tasks();
			tasks.setTitle(createForm.getTitle());
			tasks.setDate(createForm.getDate());
			tasks.setName(loginUser.getName());
			tasks.setText(createForm.getText());
			tasks.setDone(false);

			// データベースに保存
			tasksRepository.save(tasks);
			// ユーザ一覧画面へリダイレクト
			return "redirect:/main";
		} else {
			return "create"; // セッション切れ等でログインページにリダイレクト
		}

	}

}
