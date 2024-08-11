package com.dmm.task.data.entity;

import java.time.LocalDate;

public class TasksDto {

	private Long id;
	private String title;
	private String name;
	private String text;
	private LocalDate date;
	private Boolean done;
	
	public TasksDto(Long id, String title, String name, String text, LocalDate date, Boolean done) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.name = text;
        this.date = date;
        this.done = done;
    }
	public Long getId() {
	    return this.id;
	}
	public void setId(Long id) {
	    this.id = id;
	}
	public String getTitle() {
	    return this.title;
	}
	public void setTitle(String title) {
	    this.title = title;
	}
	public String getName() {
	    return this.name;
	}
	public void setName(String name) {
	    this.name = name;
	}
	public String getText() {
	    return this.text;
	}
	public void setText(String text) {
	    this.text = text;
	}
	public LocalDate getDate() {
	    return this.date;
	}
	public void setDate(LocalDate date) {
	    this.date = date;
	}
	public Boolean getDone() {
	    return this.done;
	}
	public void setDone(Boolean done) {
	    this.done = done;
	}

    // オプションでtoString, equals, hashCodeメソッドをオーバーライド
    @Override
    public String toString() {
        return "TaskDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", done=" + done +
                '}';
    }
	
}