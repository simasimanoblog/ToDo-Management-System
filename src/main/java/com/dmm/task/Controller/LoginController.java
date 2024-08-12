package com.dmm.task.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Users;
import com.dmm.task.data.repository.UsersRepository;

//アノテーションを追加
@Controller
public class LoginController {
	
	// @Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	@Autowired
	private UsersRepository usersRepository;

	// アノテーション付きのメソッド追加
	@GetMapping("/login")
	String loginForm(Model model) {
		return "login";
	}
	
    @PostMapping("/authenticate")
    String authenticate(String userName, String password, HttpSession session, Model model) {
        // ユーザー情報をリポジトリから取得
        Users user = usersRepository.findByUserNameAndPassword(userName, password);

        if (user != null) {
            // ユーザー情報をセッションに保存
            session.setAttribute("LoginUser", user);
            // ログイン後の処理
            return "redirect:/main"; // ログイン後のリダイレクト先を指定
        } else {
            // ログイン失敗時の処理
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
    }
	
}
