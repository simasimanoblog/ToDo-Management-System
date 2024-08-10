package com.dmm.task.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//アノテーションを追加
@Controller
public class LoginController {
	// アノテーション付きのメソッド追加
	@GetMapping("/loginForm")
	String loginForm() {
		return "login";
	}
}
