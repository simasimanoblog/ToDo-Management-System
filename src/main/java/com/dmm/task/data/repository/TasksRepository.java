package com.dmm.task.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dmm.task.data.entity.Tasks;

public interface TasksRepository extends JpaRepository<Tasks, Long> {
	//List<Tasks> findByDate(LocalDate date);
    //List<Tasks> findByDateBetween(LocalDate startDate, LocalDate endDate);

}