package com.employeemanagement.employeedetails.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeBatchUpdateRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public EmployeeBatchUpdateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

	/*
	 * public void saveCustomerEntity(List<EmployeeEntity> employEntityList1){
	 * String
	 * sql="insert into emp_details.emp_table ( emp_id, emp_name, emp_sal, emp_mail_id ) values(?, ?, ?, ?);"
	 * ; jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
	 * 
	 * @Override public void setValues(PreparedStatement ps, int i) throws
	 * SQLException { EmployeeEntity employEntity = employEntityList1.get(i);
	 * ps.setInt(1,employEntity.getEmpId());
	 * ps.setString(2,employEntity.getEmpName());
	 * ps.setString(3,employEntity.getEmpSal());
	 * ps.setString(4,employEntity.getEmpMailId()); }
	 * 
	 * @Override public int getBatchSize() { return employEntityList1.size(); } });
	 * }
	 */
}
