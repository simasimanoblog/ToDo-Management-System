package com.dmm.task.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.dmm.task.form.CreateForm;
import com.dmm.task.service.AccountUserDetails;

//アノテーションを追加
@Controller
public class CreateController {
	// @Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	@Autowired
	private TasksRepository tasksRepository;

	@GetMapping("/main/create/{date}")
	public String showCreateForm(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, Model model) {
		//パラメータ.日付を初期値としてセット
		model.addAttribute("date", date);
		model.addAttribute("task", new CreateForm());
		return "create"; // create.html にレンダリング
	}

	// マッピング設定
	@PostMapping("/main/create")
	public String registerCreate(@ModelAttribute("task") CreateForm createForm, BindingResult result, Model model) {
		
		// バリデーションの結果、エラーがあるかどうかチェック
		if (result.hasErrors()) {
			// エラーがある場合は登録画面を返す
			return "create";
		}

		//ログインユーザー情報
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AccountUserDetails userDetails = (AccountUserDetails) auth.getPrincipal();
        Users loginUser = userDetails.getUser();

    	//タスク
		Tasks tasks = new Tasks();
		//画面.タイトル
		tasks.setTitle(createForm.getTitle());
		//画面.タイトル
		tasks.setDate(createForm.getDate());
		//ログインユーザー.名前
		tasks.setName(loginUser.getName());
		//画面.内容
		tasks.setText(createForm.getText());
		//画面.実行フラグ
		tasks.setDone(false);

		// データベースに保存
		tasksRepository.save(tasks);
		
		// カレンダ画面へリダイレクト
		return "redirect:/main";
	}

}
