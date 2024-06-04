package com.employeemanagement.employeedetails.controller;

import com.employeemanagement.employeedetails.dto.EmployeeDto;
import com.employeemanagement.employeedetails.helper.EmployeeHelper;
import com.employeemanagement.employeedetails.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/create-employee")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto){
      //  log.info( "Inside EmployeeController : "+ employeeDto );
        EmployeeDto employeeDto1 = employeeService.saveEmployee(employeeDto);
        if(employeeDto1 != null)
        return new ResponseEntity<>( employeeDto1,HttpStatus.CREATED );
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/uploadExcel")
    public ResponseEntity<?> saveEmployeeDetailsFromExcel(@RequestParam MultipartFile file){

        if(EmployeeHelper.checkFileValidOrNot(file)){
            employeeService.save(file);
            return ResponseEntity.ok(Map.of("message","File uploaded and data saved successifully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please upload excel sheet");
    }
    @GetMapping("/")
    public ResponseEntity<?> getPingTest(){
        return ResponseEntity.status(HttpStatus.OK).body("I am Alive!!");
    }
}
