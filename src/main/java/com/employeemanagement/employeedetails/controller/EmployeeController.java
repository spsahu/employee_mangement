package com.employeemanagement.employeedetails.controller;

import com.employeemanagement.employeedetails.dto.EmployeeDto;
import com.employeemanagement.employeedetails.dto.LoginResponse;
import com.employeemanagement.employeedetails.dto.LoginUserDto;
import com.employeemanagement.employeedetails.entity.UserEntity;
import com.employeemanagement.employeedetails.helper.EmployeeHelper;
import com.employeemanagement.employeedetails.service.AuthenticationService;
import com.employeemanagement.employeedetails.service.EmployeeService;
import com.employeemanagement.employeedetails.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
public class EmployeeController {
    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    @Value("auth.api")
    private String authUrl;

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public EmployeeController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }
    @GetMapping("/ping")
    public ResponseEntity<?> getPingTest(){
        return ResponseEntity.status(HttpStatus.OK).body("I am Alive!!");
    }

    RestTemplate restTemplate = new RestTemplate();
    @PostMapping("/create-employee")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto){
       logger.info( "Inside EmployeeController : "+ employeeDto );

        LoginUserDto loginUserDto = new LoginUserDto();

        loginUserDto.setEmail(employeeDto.getEmpMailId());
        loginUserDto.setPassword(employeeDto.getEmpName());

        UserEntity authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

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

    @GetMapping("/getEmployee/{empId}")
    public ResponseEntity<?> getEmployeeById(@RequestHeader(name="Authorization") String authorization, @PathVariable Integer empId){
        if(authorization != null ) {
            byte[] decode = Base64.getDecoder().decode(authorization);
            if( new String( decode ).equals("Admin") ) {
                EmployeeDto employeeById = employeeService.getEmployeeById(empId);
                if (employeeById != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(employeeById);

                }
                return ResponseEntity.status(HttpStatus.OK).body("Employee not found for the given Emp id : " + empId);
            }
            else{
                return ResponseEntity.status(HttpStatus.OK).body(" Please enter valid Authorization value");
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(" Please add Authorization in the header");
        }
    }

    @GetMapping("/getAllEmployee")
    public ResponseEntity<?> getAllEmployee(){
        List<EmployeeDto> allEmployee = employeeService.getAllEmployee();
        if (!allEmployee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(allEmployee);
        }
        return ResponseEntity.status(HttpStatus.OK).body("No Data available");
    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDto employeeDto){
        String updateEmployee = employeeService.updateEmployee(employeeDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateEmployee);
    }

    @DeleteMapping("/delete/{empId}")
    public ResponseEntity<?> deleteEmployee( @PathVariable Integer empId){
        String deleteEmp = employeeService.deleteEmployeeById(empId);
        return ResponseEntity.status(HttpStatus.OK).body(deleteEmp);
    }


}
