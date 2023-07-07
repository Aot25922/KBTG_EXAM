package com.jbtg.exam.repository;

import com.jbtg.exam.entity.employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository <employee, Integer> {
}
