package com.employeemanagement.employeedetails.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "emp_table")
@Entity
public class EmployeeEntity {
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
