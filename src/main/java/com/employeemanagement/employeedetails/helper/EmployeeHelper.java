package com.employeemanagement.employeedetails.helper;

import com.employeemanagement.employeedetails.dto.EmployeeDto;
import com.employeemanagement.employeedetails.entity.EmployeeEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class EmployeeHelper {

   static Logger logger = LoggerFactory.getLogger(EmployeeHelper.class);

    public static boolean checkFileValidOrNot(MultipartFile file){
        return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")?true:false;
    }

    public static List<EmployeeDto> retrieveDataFromExcel(InputStream is){
       List<EmployeeDto> employeeDtoList = new ArrayList<>();
        try{
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("sheet1");
            int rowNo=0;

            Iterator<Row> rowIterator = sheet.iterator();
            while(rowIterator.hasNext()){
                EmployeeDto employeeDto = new EmployeeDto();
                Row row = rowIterator.next();
                if(rowNo == 0){
                    rowNo++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellNo =0;
                while (cellIterator.hasNext()){
                  Cell cell= cellIterator.next();
                  switch(cellNo){
                      case 0:
                          employeeDto.setEmpId((int) cell.getNumericCellValue());
                          break;
                      case 1:
                          employeeDto.setEmpName(cell.getStringCellValue());
                          break;
                      case 2:
                          employeeDto.setEmpSal(String.valueOf(cell.getNumericCellValue()));
                          break;
                      case 3:
                          employeeDto.setEmpMailId(cell.getStringCellValue());
                          break;
                      default:
                          break;
                  }
                  cellNo++;
                }
                employeeDtoList.add(employeeDto);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

      return employeeDtoList;
    }


    public static String entityToString(EmployeeEntity employeeEntity){
        ObjectMapper objectMapper =  new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(employeeEntity);
            logger.info(" Given object Converted to Json : "+json);
            return json;
        } catch (JsonProcessingException e) {
            logger.error(" Exception while converting object to json ");
            throw new RuntimeException(e);
        }

    }

    public static Timestamp getTimestamp() {

        Calendar cal = Calendar.getInstance();
        Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
        return timestamp;
    }


}
