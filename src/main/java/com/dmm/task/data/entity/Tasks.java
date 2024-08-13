package com.dmm.task.data.entity;


import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Tasks {
	// tasksテーブルのプライマリーキーidに付けるアノテーション
	@Id 
	// idがMySQLのauto_incrementの場合、自動生成させるためにアノテーションを付ける
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	// tasksテーブルのid
	private Long id;
	private String title;
	private String name;
	private String text;
	private LocalDate date;
	private Boolean done;

	public Tasks(Long id, String title, String name, String text, LocalDate date, Boolean done) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.text = text;
        this.date = date;
        this.done = done;
    }

	public Tasks() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	
}