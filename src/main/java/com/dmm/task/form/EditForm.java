package com.dmm.task.form;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class EditForm {

	// titleへのバリデーション設定を追加
	@NotBlank
	@Size(min = 1, max = 255)
	private String title;

	// dateへのバリデーション設定を追加
	@Column(columnDefinition = "DATE")
	@NotBlank
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate date;

	// nameへのバリデーション設定を追加
	@Size(min = 1, max = 255)
	private String name;

	// textへのバリデーション設定を追加
	@Column(columnDefinition = "TEXT")
	private String text;

	@Column(columnDefinition = "DONE")
	private Boolean done;

}