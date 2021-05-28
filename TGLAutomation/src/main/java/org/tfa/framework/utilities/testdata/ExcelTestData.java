package org.tfa.framework.utilities.testdata;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.tfa.framework.core.Constants;

import static org.tfa.framework.core.Constants.*;

public  class ExcelTestData extends TestData{
	private  Map<String, String> testCaseInfoMap;
	private  Map<String, String> testCaseDataMap;
	private  Map<String, String> environmentDataMap;
	private  Map<String, Map<String, String>> locatorDataMap;
	private ExcelPoiUtility excelUtil=ExcelPoiUtility.getObject();
	private  static final Logger logger = Logger.getLogger(ExcelTestData.class);

	public Map<String, String> getTestCaseInfoMap(){
		return testCaseInfoMap;
	}
	public Map<String, String> getTestCaseDataMap(){
		return testCaseDataMap;
	}

	public Map<String, String> getEnvironmentDataMap(){
		return environmentDataMap;
	}


	public Map<String, Map<String, String>> getLocatorDataMap(){ 
		return locatorDataMap;
	}

	public void arrangeTestCaseData(int dataRowNumber){
		try {
			getTestCaseInfo(dataRowNumber);
			getPageData();
			getValidationData(dataRowNumber);
			getEnvironmentInfo(testCaseInfoMap.get(KEY_NAME_ENV_ID));
		} catch (IOException e) {
			logger.error( "Exception occured while reading data", e);
		}

	}
	public String getTestCaseName(Method testMethod){
		return testMethod.getName();

	}

	public String getTestCaseID(Method testMethod, String testIDPattern){
		String testCaseID = "";
		if(StringUtils.isNotBlank(testIDPattern)) {
			String testMethodName = getTestCaseName(testMethod);
			Pattern p = Pattern.compile(testIDPattern);
			Matcher m = p.matcher(testMethodName);
			if (m.find()) {
				testCaseID = m.group(0);
			}
		}
		return testCaseID;
	}





	public  Map<String, String> getEnvironmentInfo(String envID) throws IOException{
		logger.debug("envID: "+envID);
		Workbook wbook=excelUtil.getDataBook(MASTER_SHEET_PATH);
		Sheet sheet=excelUtil.getDataSheet(wbook, ENV_SHEET_NAME);
		Map<String, String> envMap= new HashMap<>();
		Row firstRow= sheet.getRow(0);
		int columnCount=firstRow.getLastCellNum();
		int rowNumber=excelUtil.getRowNumberByRowID(sheet, ENV_COLUMN_NAME, envID);
		logger.debug("rowNumber: "+rowNumber);
		Row envRow=sheet.getRow(rowNumber);
		logger.debug("envRow: "+envRow);
		for(int i=0; i<=columnCount-1; i++){
			String envKeyName=firstRow.getCell(i).getStringCellValue();
			String envKeyValue=null;
			if(envKeyName!=null && !"".equals(envKeyName.trim())){
				envKeyValue=envRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				envMap.put(envKeyName, envKeyValue);
			}
		}
		environmentDataMap=envMap;
		return envMap;
	}


	public  void getTestCaseInfo( int rowNumber) throws IOException {
		Workbook wbook=excelUtil.getDataBook(MASTER_SHEET_PATH);
		Sheet sheet=excelUtil.getDataSheet(wbook, TEST_DRIVER_SHEET_NAME);
		int sheetRowcolumnNumber=excelUtil.getColumnNumberByColumnName(sheet, DATA_SHEET_ROW_ID);
		int envColumnNumber=excelUtil.getColumnNumberByColumnName(sheet, ENV_COLUMN_NAME);
		String pageDataInfo=sheet.getRow(rowNumber).getCell(sheetRowcolumnNumber, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();    
		testCaseInfoMap=new HashMap<>();
		testCaseInfoMap.put(KEY_NAME_PAGE_SHEETS_ROWS, pageDataInfo);
		testCaseInfoMap.put(KEY_NAME_ENV_ID,sheet.getRow(rowNumber).getCell(envColumnNumber, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
	}


	public  void getValidationData( int rowNumber) throws IOException {
		Workbook wbook=excelUtil.getDataBook(MASTER_SHEET_PATH);
		Sheet sheet=excelUtil.getDataSheet(wbook, TEST_DRIVER_SHEET_NAME);
		FormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) wbook);
		Row dataRow=null;
		logger.debug("Row Number "+rowNumber);
		dataRow=sheet.getRow(rowNumber);
		if(testCaseDataMap==null){
			testCaseDataMap=new HashMap<>();
		}
		int startColumnNumber=excelUtil.getColumnNumberByColumnName(sheet, STARTING_VALIDATION_COLUMN_NAME);
		int columnCount=dataRow.getLastCellNum();
		for(int i=startColumnNumber; i<=columnCount-1; i=i+2){
			String dataKeyName=dataRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
			if(dataKeyName!=null && !"".equals(dataKeyName.trim())){
				DataFormatter df = new DataFormatter();
				Cell curCell = dataRow.getCell(i+1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				switch (curCell.getCellTypeEnum()){
				case NUMERIC:
					testCaseDataMap.put(dataKeyName, df.formatCellValue(curCell));
					break;
				case FORMULA:
					String keyValue = df.formatCellValue(curCell, formulaEvaluator);
					testCaseDataMap.put(dataKeyName, keyValue);
					break;
				case STRING:
				default:
					testCaseDataMap.put(dataKeyName, curCell.getStringCellValue());
					break;
				}
			}
		}

	}


	public  void getPageData() throws IOException {

		String pageSheetNamesRows=testCaseInfoMap.get(KEY_NAME_PAGE_SHEETS_ROWS);
		if(StringUtils.isBlank(pageSheetNamesRows)){
			return;
		}
		Workbook wbook=excelUtil.getDataBook(TEST_DATA_FOLDER_PATH +
				TEST_DATA_WORKBOOK_NAME+
				MS_EXCEL_EXTENSION);
		if(testCaseDataMap==null){
			testCaseDataMap=new HashMap<>();
		}

		String pageSheetName=null;
		String rowID=null;
		String[] dataSheetsRowsArray=pageSheetNamesRows.trim().split(",");
		for(String sheetNameRowNum:dataSheetsRowsArray){
			if(!"".equals(sheetNameRowNum.trim())){
				String[] arrDataInfo=sheetNameRowNum.split(SHEET_ROW_DELIMETER);
				pageSheetName=arrDataInfo[0].trim();
				rowID=arrDataInfo[1].trim();
			}
			Sheet sheet=excelUtil.getDataSheet(wbook, pageSheetName);
			FormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) wbook);
			int rowNumber=excelUtil.getRowNumberByRowID(sheet, ROW_ID_COLUMN_NAME, rowID);
			Row dataRow=sheet.getRow(rowNumber);
			Row columnRow=sheet.getRow(0);
			for(int i=1; i<columnRow.getLastCellNum(); i++){
				String dataKeyName=columnRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				if(dataKeyName!=null && !"".equals(dataKeyName.trim())){
					String cellValue=excelUtil.getCellData(dataRow, i, formulaEvaluator);
					testCaseDataMap.put(dataKeyName, cellValue);
				}
			}
		}
	}




	public  Map<String, Map<String, String>> getLocatorInfo() throws IOException{
		Workbook wbook=excelUtil.getDataBook(MASTER_SHEET_PATH);
		Sheet sheet=excelUtil.getDataSheet(wbook, Constants.LOCATOR_SHEET_NAME);
		int locatorNameColumnNumber=excelUtil.getColumnNumberByColumnName(sheet,LOCATOR_NAME_COLUMN);
		int locatorValueColumnNumber=excelUtil.getColumnNumberByColumnName(sheet, LOCATOR_VALUE_COLUMN);
		int locatorTypeColumnNumber=excelUtil.getColumnNumberByColumnName(sheet, LOCATOR_TYPE_COLUMN);
		Map<String, Map<String, String>> orMap= new HashMap<>();
		for(int rowNumber = 1; rowNumber<= sheet.getLastRowNum(); rowNumber++){
			Row dataRow=sheet.getRow(rowNumber);
			String dataKeyName=dataRow.getCell(locatorNameColumnNumber, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
			if(dataKeyName!=null && !"".equals(dataKeyName.trim())){
				String locatorValue=dataRow.getCell(locatorValueColumnNumber, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				String locatorType=dataRow.getCell(locatorTypeColumnNumber, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				Map<String, String> locatorDetailsMap=new HashMap<>();
				locatorDetailsMap.put(LOCATOR_VALUE_COLUMN, locatorValue);
				locatorDetailsMap.put(LOCATOR_TYPE_COLUMN, locatorType);
				orMap.put(dataKeyName, locatorDetailsMap);
			}
		}
		locatorDataMap=orMap;
		return orMap;
	}


	/**
	 * This method is returning multiple row numbers of testcase with same testcase id in a List
	 */
	public List<Integer> getTestDataRowsList(String testCaseId){
		Workbook wbook;
		List<Integer> listData=null;
		try {
			wbook = excelUtil.getDataBook(MASTER_SHEET_PATH);
			Sheet sheet=excelUtil.getDataSheet(wbook, TEST_DRIVER_SHEET_NAME);
			listData= excelUtil.getRowNumbersListByRowID(sheet, Constants.TESTCASE_ID_COLUMN_NAME, testCaseId);
		} catch (Exception e) {
			logger.error( MASTER_SHEET_PATH+" file not found- ", e);
		}
		return listData;
	}

	/**
	 * This method is returning single row number of testcase on the basis of testcase id
	 */
	public int getTestDataRowNumber(String testCaseId){
		Workbook wbook;
		int rowNumber=-1;
		try {
			wbook = excelUtil.getDataBook(MASTER_SHEET_PATH);

			Sheet sheet=excelUtil.getDataSheet(wbook, TEST_DRIVER_SHEET_NAME);
			rowNumber= excelUtil.getRowNumberByRowID(sheet, Constants.TESTCASE_ID_COLUMN_NAME, testCaseId);
			logger.debug( "Row number = "+ rowNumber);
		} catch (Exception e) {
			logger.error( "Exception occured while fetching row number = "+ rowNumber, e);

		}
		return rowNumber;
	}

}