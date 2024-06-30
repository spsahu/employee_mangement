package com.employeemanagement.employeedetails.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Table(name = "emp_table")
@Entity
public class EmployeeEntity implements Serializable {
    @Column(name="emp_id")
    @Id
    Integer empId;

    @Column(name="emp_name")
    String empName;

    @Column(name="emp_sal")
    String empSal;

    @Column(name="emp_mail_id")
    String empMailId;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpSal() {
        return empSal;
    }

    public void setEmpSal(String empSal) {
        this.empSal = empSal;
    }

    public String getEmpMailId() {
        return empMailId;
    }

    public void setEmpMailId(String empMailId) {
        this.empMailId = empMailId;
    }

    @Override
    public String toString() {
        return "EmployEntity{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", empSal='" + empSal + '\'' +
                ", empMailId='" + empMailId + '\'' +
                '}';
    }
}
