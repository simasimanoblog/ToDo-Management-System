package com.dmm.task.form;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	// @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate date;

	// nameへのバリデーション設定を追加
	private String name;

	// textへのバリデーション設定を追加
	private String text;

}