package com.employeemanagement.employeedetails.repository;

import com.employeemanagement.employeedetails.entity.EmployeeEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EntityRepoositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    /*@Test
    @Sql("/create-table.sql") // Assuming you have a SQL script to create the table
    public void testBatchUpdate() {
        // Create a list of Person objects to be inserted
        List<EmployeeEntity> list =setEmployessEntity();

        // Insert the list of persons using batchUpdate
        int[][] updateCounts = jdbcTemplate.batchUpdate(
                "INSERT INTO person(id, first_name, last_name) VALUES (?, ?, ?)",
                list,
                list.size(),
                (ps, employ) -> {
                    ps.setString(1, employ.getEmpId());
                    ps.setString(2, employ.getEmpName());
                    ps.setString(3, employ.getEmpMailId());
                });

        // Verify that the correct number of rows were inserted
        assertEquals(list.size(), Arrays.stream(updateCounts).count());

        // You can also add additional assertions or queries to verify the data in the database
    }
*/
    private List<EmployeeEntity> setEmployessEntity() {

        List<EmployeeEntity> list = new ArrayList<>();
        EmployeeEntity emp = new EmployeeEntity();
        emp.setEmpId(111);
        emp.setEmpName("Swarna");
        emp.setEmpSal("1000000");
        emp.setEmpMailId("s@gmail.com");
        list.add(emp);

        emp = new EmployeeEntity();
        emp.setEmpId(222);
        emp.setEmpName("swapna");
        emp.setEmpSal("20000000");
        emp.setEmpMailId("swapna@gmail.com");
        list.add(emp);

        return list;
    }

}