package com.employeemanagement.employeedetails.repository;

import com.employeemanagement.employeedetails.entity.EmployeeOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeOutboxRepository extends JpaRepository<EmployeeOutboxEntity, String> {
}
