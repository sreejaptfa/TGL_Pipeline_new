package org.tfa.tgl.pages.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebElement;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class SelectorPortalPage {

	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	Logger log=Logger.getLogger("rootLogger");
	/**
	 * This function will login to the IMPS application
	 */
	public void validLogin(String userName, String password){
        webUtil.setTextBoxValue("SelectorPortal_UserName_ED", userName);
        webUtil.setTextBoxValue("SelectorPortal_Password_ED", password);
        webUtil.click("SelectorPortal_Submit_Btn");  
	}
	
	/**
	 * This function will click on Regional Reassignment Upload Link
	 */
	public void selectRegionalReassignmentUploadLink(){
		webUtil.click("SelectorPortal_Admissions_LK");
		webUtil.holdOn(5);
		webUtil.click("SelectorPortal_RegionalReassignmentUpload_LK");
		webUtil.holdOn(5);
	}
	
	/**
	 * This function will choose the file and click on the upload button
	 */
	public void chooseFileAndUploadTemplate(){
		WebElement el=webUtil.getElement("SelectorPortal_FileDate_file");
		el.clear();
		el.sendKeys(new File("Reg_Reassign_Upload_Template.xls").getAbsolutePath());
        webUtil.holdOn(5);
        webUtil.click("SelectorPortal_Uploaded_Btn");webUtil.holdOn(5);
	}
	
	/**
	 * This function will update the Person id on row 1 for Reg Reassign Upload Template
	 */
	public void updateExcelWithPersonIDForRegReassignUploadTemplate(String fileName, String applicantID, String assignmentId) throws IOException{
		try (FileInputStream fis = new FileInputStream(new File(fileName).getAbsolutePath())) { 
			HSSFWorkbook workbook = new HSSFWorkbook (fis);
			HSSFSheet sheet = workbook.getSheetAt(0); 
			HSSFRow row = sheet.getRow(1);
			HSSFCell cell = row.getCell(0);
			cell.setCellValue(applicantID);
			HSSFCell cell1 = row.getCell(3);
			cell1.setCellValue(assignmentId);
			FileOutputStream fos =new FileOutputStream(new File(fileName).getAbsolutePath());
			workbook.write(fos);
			workbook.close();
			fos.close();
		} catch (IOException e) {
			log.error("No Data file found of name ", e);
			throw e;
		} catch (Exception e) {
			log.error("Exception occured", e);
			throw e;
		}
	}
}
