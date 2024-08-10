package com.dmm.task.form;


import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CreateForm {

	// titleへのバリデーション設定を追加
	@NotBlank
	@Size(min = 1, max = 255)
	private String title;

	// dateへのバリデーション設定を追加
	@NotBlank
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDateTime date;
	
	// nameへのバリデーション設定を追加
	@Size(min = 1, max = 255)
	private String name;

	// textへのバリデーション設定を追加
	private String text;

}