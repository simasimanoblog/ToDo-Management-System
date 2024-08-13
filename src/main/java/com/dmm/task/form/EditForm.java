package com.dmm.task.form;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class EditForm {

	private Long id; // IDプロパティを追加

	// titleへのバリデーション設定を追加
	@NotBlank
	@Size(min = 1, max = 255)
	private String title;

	// dateへのバリデーション設定を追加
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	// nameへのバリデーション設定を追加
	private String name;

	// textへのバリデーション設定を追加
	@Column(columnDefinition = "TEXT")
	private String text;

	@Column(columnDefinition = "DONE")
	private Boolean done;

}