package com.sahil.services.implementations;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sahil.constants.ConstantValues;
import com.sahil.entities.AdminEntity;
import com.sahil.models.hr.report.ApprovedLeaves;
import com.sahil.models.hr.report.ApprovedLeavesResponseDTO;
import com.sahil.models.hr.report.Manager;
import com.sahil.models.manager.report.ApprovedLeavesForReport;
import com.sahil.models.manager.report.ApprovedLeavesReportWrapper;
import com.sahil.services.LeaveService;
import com.sahil.services.ReportGenerator;
import com.sahil.utils.LeaveUtil;

@Service
public class ReportGeneratorImpl implements ReportGenerator{

	@Autowired
	private LeaveService leaveService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void generateReportForHR(List<AdminEntity> admins){
		
		if(!CollectionUtils.isEmpty(admins)){
			ApprovedLeavesResponseDTO approvedLeavesResponseDTO = leaveService.findLeavesForHRReportForCurrentMonth();
			if(approvedLeavesResponseDTO.getCode()==0){
				List<ApprovedLeaves> approvedLeaves = approvedLeavesResponseDTO.getApprovedLeaves();
				if(!CollectionUtils.isEmpty(approvedLeaves)){
					try {
						reportUtilForHR(admins, approvedLeaves);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}
				}
			}
			else{
				System.out.println(approvedLeavesResponseDTO);
			}
		}
		
	}
	
    @Override
	public void generateReportForManager(){
		
	   ApprovedLeavesReportWrapper approvedLeavesReportWrapper = leaveService.findLeavesForManagersForCurrentWeek();
	   if(approvedLeavesReportWrapper.getCode()==0){
		   Map<Manager,List<ApprovedLeavesForReport>> leaves = approvedLeavesReportWrapper.getLeaves();
		   if(!CollectionUtils.isEmpty(leaves)){
			   if(!leaves.keySet().isEmpty() && !leaves.values().isEmpty()){
				   
				   for(Map.Entry<Manager,List<ApprovedLeavesForReport>> entry:leaves.entrySet()){
					   Manager manager = entry.getKey();
					   List<ApprovedLeavesForReport> leavesForReports = entry.getValue();
					   if(Objects.nonNull(manager) && !CollectionUtils.isEmpty(leavesForReports)){
				          try {
							reportUtilForManager(manager,leavesForReports);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println(e.getMessage());
						}
					   }
					  
				   }
				   
				   
			   }
			   
		   }
		   
		   
	   }
	   else{
		   System.out.println(approvedLeavesReportWrapper.getMessage());
	   }
	   
	   
	   
	   
	   	
	}
	
	
	
	private void reportUtilForHR(List<AdminEntity> admins,List<ApprovedLeaves> leaves) throws Exception{
		if(CollectionUtils.isEmpty(admins)){
			return ;
		}
		
		Workbook workbook = null;
		String fileName = "hr"+new Date().getTime()/100+".xlsx";
		if(fileName.endsWith("xlsx")){
			workbook = new XSSFWorkbook();
		}
		else if(fileName.endsWith("xls")){
			workbook = new HSSFWorkbook();
		}
		else{
			throw new Exception("invalid file name, should be xls or xlsx");
		}
		
		Sheet sheet = workbook.createSheet("leaves");
		CellStyle style = workbook.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    style.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
	    
	    short df = workbook.createDataFormat().getFormat("");
	    style.setDataFormat(df);
	    
	    Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
		
		int rowIndex = 0;
		
		String[] columnNames0 = {"Employee","Manager","ApprovedBy"};
		String[] columnNames = {"LeaveId","Code","Name","Code","Name","Code","Name","AppliedOn","From","To","Type","No. of Days","ApprovedOn"};
		
		Row row0 = sheet.createRow(rowIndex++);
		Cell cell0 = row0.createCell(0);
		
		cell0 = row0.createCell(1);
	    cell0.setCellValue(columnNames0[0]);
	    cell0.setCellStyle(style);
	    sheet.addMergedRegion(new CellRangeAddress(0,0,1,2));
	    
	    cell0 = row0.createCell(3);
	    cell0.setCellValue(columnNames0[1]);
	    cell0.setCellStyle(style);
	    sheet.addMergedRegion(new CellRangeAddress(0,0,3,4));

	    cell0 = row0.createCell(5);
	    cell0.setCellValue(columnNames0[2]);
	    cell0.setCellStyle(style);
	    sheet.addMergedRegion(new CellRangeAddress(0,0,5,6));

	    
	    row0 = sheet.createRow(rowIndex++);
	    int colNo = 0;
	    for(String columName:columnNames){
	    	cell0 = row0.createCell(colNo++);
	    	cell0.setCellValue(columName);
	    	cell0.setCellStyle(style);
	    }
	    
	    
	    
	    String prevDept = null;
		for(ApprovedLeaves approvedLeaves:leaves){
			
			if(prevDept==null || !prevDept.equalsIgnoreCase(approvedLeaves.getEmployee().getEmpDepartment())){
				Row row = sheet.createRow(rowIndex++);
				colNo = 0;
				cell0=row.createCell(colNo++);
				cell0.setCellValue("Department");
				cell0.setCellStyle(style);
				
				cell0=row.createCell(colNo++);
				cell0.setCellValue(approvedLeaves.getEmployee().getEmpDepartment());
				cell0.setCellStyle(style);
				
				prevDept = approvedLeaves.getEmployee().getEmpDepartment();
			}
			
			Row row = sheet.createRow(rowIndex++);
			colNo = 0;
			row.createCell(colNo++).setCellValue(approvedLeaves.getLeaveId());
			row.createCell(colNo++).setCellValue(approvedLeaves.getEmployee().getEmpCode());	
			row.createCell(colNo++).setCellValue(approvedLeaves.getEmployee().getEmpName());
			row.createCell(colNo++).setCellValue(approvedLeaves.getManager().getEmpCode());
			row.createCell(colNo++).setCellValue(approvedLeaves.getManager().getEmpName());
			row.createCell(colNo++).setCellValue(approvedLeaves.getApprovedBy().getEmpCode());
			row.createCell(colNo++).setCellValue(approvedLeaves.getApprovedBy().getEmpName());
			row.createCell(colNo++).setCellValue(approvedLeaves.getAppliedOn());
			row.createCell(colNo++).setCellValue(approvedLeaves.getFromDate());
			row.createCell(colNo++).setCellValue(approvedLeaves.getToDate());
			row.createCell(colNo++).setCellValue(approvedLeaves.getLeaveType());
			row.createCell(colNo++).setCellValue(approvedLeaves.getNoOfDays());
			row.createCell(colNo++).setCellValue(approvedLeaves.getApprovedOn());
		}
		
		
		// Resize all columns to fit the content size
        for(int i = 0; i < columnNames.length; i++) {
            sheet.autoSizeColumn(i);
        }

		FileOutputStream fos = new FileOutputStream(ConstantValues.HR_REPORT_DIRECTORY+"/"+fileName);
		workbook.write(fos);
		fos.close();
		System.out.println(fileName + " written successfully");
		
		
		for(AdminEntity adminEntity:admins){
			sendMailForHR(adminEntity,fileName);
		}
		
	}

	
	private void reportUtilForManager(Manager manager,List<ApprovedLeavesForReport> leaves) throws Exception {
		Workbook workbook = null;
		String fileName = "manager"+new Date().getTime()/100+".xlsx";
		if(fileName.endsWith("xlsx")){
			workbook = new XSSFWorkbook();
		}
		else if(fileName.endsWith("xls")){
			workbook = new HSSFWorkbook();
		}
		else{
			throw new Exception("invalid file name, should be xls or xlsx");
		}
		
		Sheet sheet = workbook.createSheet("leaves");
		CellStyle style = workbook.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    style.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
	    
	    short df = workbook.createDataFormat().getFormat("");
	    style.setDataFormat(df);
	    
	    Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
		
		int rowIndex = 0;
		
		String[] columnNames0 = {"Employee","ApprovedBy"};
		String[] columnNames = {"LeaveId","Code","Name","Code","Name","AppliedOn","From","To","Type","No. of Days","ApprovedOn"};
		
		Row row0 = sheet.createRow(rowIndex++);
		Cell cell0 = row0.createCell(0);
		
		cell0 = row0.createCell(1);
	    cell0.setCellValue(columnNames0[0]);
	    cell0.setCellStyle(style);
	    sheet.addMergedRegion(new CellRangeAddress(0,0,1,2));
	    
	    cell0 = row0.createCell(3);
	    cell0.setCellValue(columnNames0[1]);
	    cell0.setCellStyle(style);
	    sheet.addMergedRegion(new CellRangeAddress(0,0,3,4));

	    row0 = sheet.createRow(rowIndex++);
	    int colNo = 0;
	    for(String columName:columnNames){
	    	cell0 = row0.createCell(colNo++);
	    	cell0.setCellValue(columName);
	    	cell0.setCellStyle(style);
	    }
	    
	    for(ApprovedLeavesForReport approvedLeaves:leaves){
	      	Row row = sheet.createRow(rowIndex++);
			colNo = 0;
			row.createCell(colNo++).setCellValue(approvedLeaves.getLeaveId());
			row.createCell(colNo++).setCellValue(approvedLeaves.getEmployee().getEmpCode());	
			row.createCell(colNo++).setCellValue(approvedLeaves.getEmployee().getEmpName());
			row.createCell(colNo++).setCellValue(approvedLeaves.getApprovedBy().getEmpCode());
			row.createCell(colNo++).setCellValue(approvedLeaves.getApprovedBy().getEmpName());
			row.createCell(colNo++).setCellValue(approvedLeaves.getAppliedOn());
			row.createCell(colNo++).setCellValue(approvedLeaves.getFromDate());
			row.createCell(colNo++).setCellValue(approvedLeaves.getToDate());
			row.createCell(colNo++).setCellValue(approvedLeaves.getLeaveType());
			row.createCell(colNo++).setCellValue(approvedLeaves.getNoOfDays());
			row.createCell(colNo++).setCellValue(approvedLeaves.getApprovedOn());
		}
		

		// Resize all columns to fit the content size
        for(int i = 0; i < columnNames.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
		FileOutputStream fos = new FileOutputStream(ConstantValues.MANAGER_REPORT_DIRECTORY+"/"+fileName);
		workbook.write(fos);
		
		fos.close();
		System.out.println(fileName + " written successfully");
        
		
		sendMailForManager(manager, fileName);
		
	}
	
		
		 

	private void sendMailForHR(AdminEntity adminEntity,String fileName) {
			
		   MimeMessage message = mailSender.createMimeMessage();
		   FileSystemResource file = null;		
		   try{
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			Calendar calendar = Calendar.getInstance();
	        int month =calendar.get(Calendar.MONTH);
			helper.setFrom("username@email.com");
			helper.setTo("user@email.com"); // change it
			helper.setSubject("Monthly Report for "+LeaveUtil.getMonth(month));
	        helper.setText("This mail contaings monthly report for " +LeaveUtil.getMonth(month));
	        
	        file = new FileSystemResource(ConstantValues.HR_REPORT_DIRECTORY+"/"+fileName);
			helper.addAttachment(file.getFilename(), file);
            
		     }catch (MessagingException e) {
			throw new MailParseException(e);
		     }
		     mailSender.send(message);
		     if(file.exists()){
		    	 file.getFile().delete();
		     }
		    
	         }	 
	
	 

	 
	 private void sendMailForManager(Manager manager,String fileName) {
			
		   MimeMessage message = mailSender.createMimeMessage();
		   FileSystemResource file = null;	
		   try{
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("username@email.com");
			helper.setTo("user@email.com"); // change it
			
			String firstDayOfWeek = LeaveUtil.convertDateToStringInParticularFormat(LeaveUtil.getFirstDateOfWeek());
		    String lastDayOfWeek = LeaveUtil.convertDateToStringInParticularFormat(LeaveUtil.getLastDateOfWeek());
		    
		    helper.setSubject("Weekly Report for days from "+firstDayOfWeek+" to "+lastDayOfWeek);
		    helper.setText("This mail contaings weekly report for days from "+firstDayOfWeek+" to "+lastDayOfWeek);
		    file = new FileSystemResource(ConstantValues.MANAGER_REPORT_DIRECTORY+"/"+fileName);
			helper.addAttachment(file.getFilename(), file);
            
		     }catch (MessagingException e) {
			throw new MailParseException(e);
		     }
		     mailSender.send(message);
		     if(file.exists()){
		    	 file.getFile().delete();
		     }
		     
	 }	 
	 
		 
	 

}
