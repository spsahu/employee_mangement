package com.employeemanagement.employeedetails.service;

import com.employeemanagement.employeedetails.dto.EmployeeDto;
import com.employeemanagement.employeedetails.entity.EmployeeEntity;
import com.employeemanagement.employeedetails.entity.EmployeeOutboxEntity;
import com.employeemanagement.employeedetails.helper.EmployeeHelper;
import com.employeemanagement.employeedetails.repository.EmployeeOutboxRepository;
import com.employeemanagement.employeedetails.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepoository;

    @Autowired
    EmployeeOutboxRepository employeeOutboxRepository;


    /*for saving one Employee details*/
    public EmployeeDto saveEmployee(EmployeeDto employeeDto){
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties( employeeDto, employeeEntity );
        EmployeeOutboxEntity employeeOutboxEntity = new EmployeeOutboxEntity();
        employeeOutboxEntity.setEventId(UUID.randomUUID().toString());
        employeeOutboxEntity.setEventStatus("ADD");
        employeeOutboxEntity.setOldData(null);

        String newDate = EmployeeHelper.entityToString(employeeEntity);
        employeeOutboxEntity.setNewData(newDate);
        employeeOutboxEntity.setCreateDate(EmployeeHelper.getTimestamp());
        employeeOutboxEntity.setUpdateDate(EmployeeHelper.getTimestamp());

        EmployeeEntity saveEntity = employeeRepoository.save(employeeEntity);
        employeeOutboxRepository.save(employeeOutboxEntity);
        BeanUtils.copyProperties( saveEntity, employeeDto);
        return employeeDto;
    }

    /*for saving bulk of Employee details*/
    public void save(MultipartFile file) {
        try {
            List<EmployeeDto> employeeDtoList = EmployeeHelper.retrieveDataFromExcel(file.getInputStream());
            List<EmployeeEntity> employeeEntityList= new ArrayList<>();
            List<EmployeeOutboxEntity> employeeOutboxEntities= new ArrayList<>();
            for(EmployeeDto dto:employeeDtoList){
                EmployeeEntity entity = new EmployeeEntity();
                BeanUtils.copyProperties(dto, entity);
                EmployeeOutboxEntity employeeOutboxEntity = new EmployeeOutboxEntity();
                employeeOutboxEntity.setEventId(UUID.randomUUID().toString());
                employeeOutboxEntity.setEventStatus("ADD");
                employeeOutboxEntity.setOldData(null);

                String newDate = EmployeeHelper.entityToString(entity);
                employeeOutboxEntity.setNewData(newDate);
                employeeOutboxEntity.setCreateDate(EmployeeHelper.getTimestamp());
                employeeOutboxEntity.setUpdateDate(EmployeeHelper.getTimestamp());

                employeeEntityList.add(entity);
                employeeOutboxEntities.add(employeeOutboxEntity);
            }

            employeeRepoository.saveAll(employeeEntityList);
            employeeOutboxRepository.saveAll(employeeOutboxEntities);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EmployeeDto> getAllEmployee(){
        EmployeeDto employeeDto = new EmployeeDto();
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        List<EmployeeEntity> employeeEntityList = employeeRepoository.findAll();
        if( !employeeEntityList.isEmpty() ) {
            for (EmployeeEntity employeeEntity : employeeEntityList) {
                BeanUtils.copyProperties(employeeEntity, employeeDto);
                employeeDtoList.add(employeeDto);
            }
        }
        return employeeDtoList;
    }

    public EmployeeDto getEmployeeById(Integer empId){
        Optional<EmployeeEntity> employeeEntity = employeeRepoository.findById(empId);
        if(employeeEntity.isPresent()) {
            EmployeeDto employeeDto = new EmployeeDto();
            BeanUtils.copyProperties( employeeEntity.get(), employeeDto );
            return employeeDto;
        }
        return null;
    }


    public String updateEmployee(EmployeeDto employeeDto){
        Optional<EmployeeEntity> employeeEntity = employeeRepoository.findById(employeeDto.getEmpId());
        if (employeeEntity.isPresent()){
            EmployeeEntity employee = employeeEntity.get();
            employee.setEmpName(employeeDto.getEmpName());
            employee.setEmpMailId(employeeDto.getEmpMailId());
            employee.setEmpSal(employeeDto.getEmpSal());

            EmployeeEntity updateEntity = employeeRepoository.save(employee);

            EmployeeOutboxEntity employeeOutboxEntity = new EmployeeOutboxEntity();
            employeeOutboxEntity.setEventId(UUID.randomUUID().toString());
            employeeOutboxEntity.setEventStatus("UPDATE");

            String oldData = EmployeeHelper.entityToString(employee);
            employeeOutboxEntity.setOldData(oldData);

            String newDate = EmployeeHelper.entityToString(updateEntity);
            employeeOutboxEntity.setNewData(newDate);
           /* employeeOutboxEntity.setCreateDate(employeeEntity.getC());*/
            employeeOutboxEntity.setUpdateDate(EmployeeHelper.getTimestamp());
            employeeOutboxRepository.save(employeeOutboxEntity);
            return "update Successfully";
        }
        return null;
    }

    public String deleteEmployeeById(Integer empId){
        Optional<EmployeeEntity> employeeEntity = employeeRepoository.findById( empId );
        if (employeeEntity.isPresent()){
            employeeRepoository.deleteById(empId);
            EmployeeOutboxEntity employeeOutboxEntity = new EmployeeOutboxEntity();
            employeeOutboxEntity.setEventId(UUID.randomUUID().toString());
            employeeOutboxEntity.setEventStatus("DEL");

            String oldData = EmployeeHelper.entityToString(employeeEntity.get());
            employeeOutboxEntity.setOldData(oldData);

            employeeOutboxEntity.setNewData(null);
            /* employeeOutboxEntity.setCreateDate(employeeEntity.getC());*/
            employeeOutboxEntity.setUpdateDate(EmployeeHelper.getTimestamp());
            employeeOutboxRepository.save(employeeOutboxEntity);
            return "Employee with Employee Id : "+empId+" deleted successifully";
        }
        else{
            return "Employee not found with Employee id: "+empId;
        }
    }
}
