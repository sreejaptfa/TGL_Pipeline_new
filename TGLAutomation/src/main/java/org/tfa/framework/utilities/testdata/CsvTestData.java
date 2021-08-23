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
import org.tfa.framework.core.Constants;

import static org.tfa.framework.core.Constants.*;

public  class CsvTestData extends TestData{
	private  Map<String, String> testCaseInfoMap;
	private  Map<String, String> testCaseDataMap;
	private  Map<String, String> environmentDataMap;
	private  Map<String, Map<String, String>> locatorDataMap;
	private CsvUtility csvUtil=CsvUtility.getObject();
	private  static final Logger logger = Logger.getLogger(CsvTestData.class);

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
	
	@Override
	public String getTestCaseName(Method testMethod){
		return testMethod.getName();
	}	
	
	@Override
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

		Map<String, String> envMap= new HashMap<>();



		List<String[]> envLines=csvUtil.getAllLinesFromCSV(CSV_ENV_SHEET_NAME);
		int rowNumber=csvUtil.getRowNumberByRowID(envLines, ENV_COLUMN_NAME, envID);
		String[] envColumnArr=envLines.get(0);
		String[] envDataArr=envLines.get(rowNumber);

		for(int i=0; i<=envColumnArr.length-1; i++){
			String envKeyName=envColumnArr[i].trim();
			String envKeyValue=null;
			if(envKeyName!=null && !"".equals(envKeyName.trim())){
				envKeyValue=envDataArr[i];
				
				envMap.put(envKeyName, envKeyValue);
			}
		}
		environmentDataMap=envMap;
		return envMap;
	}


	public  void getTestCaseInfo( int rowNumber) throws IOException {
		List<String[]> testCaseDriverLines=csvUtil.getAllLinesFromCSV(CSV_TEST_DRIVER_SHEET_NAME);
		String[] columnLine=testCaseDriverLines.get(0);
		int sheetRowcolumnNumber=csvUtil.getColumnNumberByColumnName(columnLine,DATA_SHEET_ROW_ID);
		int envColumnNumber=csvUtil.getColumnNumberByColumnName(columnLine, ENV_COLUMN_NAME);
		String[] testCaseInfoLine=testCaseDriverLines.get(rowNumber);  
		String pageDataInfo=testCaseInfoLine[sheetRowcolumnNumber];
		testCaseInfoMap=new HashMap<>();
		testCaseInfoMap.put(KEY_NAME_PAGE_SHEETS_ROWS, pageDataInfo);
		String envDetail=testCaseDriverLines.get(rowNumber)[envColumnNumber];
		testCaseInfoMap.put(KEY_NAME_ENV_ID,envDetail);
	}


	public  void getValidationData(int rowNumber) throws IOException {
		List<String[]> testCaseDriverLines=csvUtil.getAllLinesFromCSV(CSV_TEST_DRIVER_SHEET_NAME);
		String[] testCaseRowDataArray=testCaseDriverLines.get(rowNumber);
		if(testCaseDataMap==null){
			testCaseDataMap=new HashMap<>();
		}
		int startColumnNumber=csvUtil.getColumnNumberByColumnName(testCaseDriverLines.get(0), STARTING_VALIDATION_COLUMN_NAME);
		
		for(int i=startColumnNumber; i<=testCaseRowDataArray.length-1; i=i+2){
			String dataKeyName=testCaseRowDataArray[i].trim();
			if(dataKeyName!=null && !"".equals(dataKeyName.trim())){
				String cellData;
				try {
				 cellData = testCaseRowDataArray[i+1].trim();
				}catch(ArrayIndexOutOfBoundsException e) {
				 cellData ="";
				}
				testCaseDataMap.put(dataKeyName, cellData);

			}
		}
	}




	public  void getPageData() throws IOException {

		String pageSheetNamesRows=testCaseInfoMap.get(KEY_NAME_PAGE_SHEETS_ROWS);
		if(StringUtils.isBlank(pageSheetNamesRows)){
			return;
		}
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
			List<String[]> testDataLines=csvUtil.getAllLinesFromCSV(CSV_TEST_DATA_FOLDER_PATH+pageSheetName+".csv");
			int rowNumber=csvUtil.getRowNumberByRowID(testDataLines, ROW_ID_COLUMN_NAME, rowID);
			String[] columnLineArray=testDataLines.get(0);
			String[] dataLineArray=testDataLines.get(rowNumber);

			for(int i=1; i<dataLineArray.length; i++){
				String dataKeyName=columnLineArray[i];
				if(dataKeyName!=null && !"".equals(dataKeyName.trim())){
					String cellValue=dataLineArray[i];
					testCaseDataMap.put(dataKeyName, cellValue);
				}
			}
		}
	}




	public  Map<String, Map<String, String>> getLocatorInfo() throws IOException{
		List<String[]> locatorLines=csvUtil.getAllLinesFromCSV(Constants.CSV_LOCATOR_SHEET_NAME);
		String[] columnLine=locatorLines.get(0);
		int locatorNameColumnNumber=csvUtil.getColumnNumberByColumnName(columnLine,LOCATOR_NAME_COLUMN);
		int locatorValueColumnNumber=csvUtil.getColumnNumberByColumnName(columnLine, LOCATOR_VALUE_COLUMN);
		int locatorTypeColumnNumber=csvUtil.getColumnNumberByColumnName(columnLine, LOCATOR_TYPE_COLUMN);
		Map<String, Map<String, String>> orMap= new HashMap<>();
		for(int rowNumber = 1; rowNumber<= locatorLines.size()-1; rowNumber++){
			String[] locatorDataArray=locatorLines.get(rowNumber);
			String dataKeyName=null;
			if(locatorDataArray.length>2) {
				dataKeyName=locatorDataArray[locatorNameColumnNumber].trim();	
			}
				if(dataKeyName!=null && !"".equals(dataKeyName.trim())){
					String locatorValue;
					String locatorType;
					try {
					locatorValue=locatorDataArray[locatorValueColumnNumber].trim();
					locatorType=locatorDataArray[locatorTypeColumnNumber];
					}catch(ArrayIndexOutOfBoundsException e) {
						locatorValue="";	
						locatorType="";	
					}
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
		List<Integer> listData=null;
		try {
			List<String[]> testDataList=csvUtil.getAllLinesFromCSV(CSV_TEST_DRIVER_SHEET_NAME);
			listData= csvUtil.getRowNumbersListByRowID(testDataList, Constants.TESTCASE_ID_COLUMN_NAME, testCaseId);
		} catch (Exception e) {
			logger.error( CSV_TEST_DRIVER_SHEET_NAME+" file not found- ", e);
		}
		return listData;
	}

	/**
	 * This method is returning single row number of testcase on the basis of testcase id
	 */
	public int getTestDataRowNumber(String testCaseId){
		int rowNumber=-1;
		try { 
			List<String[]> testDataList=csvUtil.getAllLinesFromCSV(CSV_TEST_DRIVER_SHEET_NAME);
			rowNumber= csvUtil.getRowNumberByRowID(testDataList, Constants.TESTCASE_ID_COLUMN_NAME, testCaseId);
			logger.debug( "Row number = "+ rowNumber);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error( "Exception occured while fetching row number = "+ rowNumber, e);

		}
		return rowNumber;
	}

}
