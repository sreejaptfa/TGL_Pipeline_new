package org.tfa.framework.core;

@FunctionalInterface
public interface Constants { 	 

	static final String MASTER_SHEET_PATH="TestData/MasterSheet.xlsx";
	static final String TEST_DRIVER_SHEET_NAME="TestCaseDriver";
	static final String LOCATOR_SHEET_NAME="Locators";
	static final String ENV_SHEET_NAME="ENV";
	static final String TEST_DATA_FOLDER_PATH="TestData/";


	static final String CSV_MASTER_SHEET_PATH="src/test/resources/TestData/";
	static final String CSV_TEST_DRIVER_SHEET_NAME=CSV_MASTER_SHEET_PATH+"TestCaseDriver.csv";
	static final String CSV_LOCATOR_SHEET_NAME=CSV_MASTER_SHEET_PATH+"Locators.csv";
	static final String CSV_ENV_SHEET_NAME=CSV_MASTER_SHEET_PATH+"ENV.csv"; 	
	static final String CSV_TEST_DATA_FOLDER_PATH=CSV_MASTER_SHEET_PATH+"PageData/";
	static final String CONFIG_PATH="config.properties";
	static final String CONFIG_TESTDATA_TYPE="TestDataType";
	static final String TEST_DATA_WORKBOOK_NAME="TestData";
	static final String DATA_WORKBOOK_NAME="DataBook";

	static final String MS_EXCEL_EXTENSION=".xlsx";	 
	static final String ROW_ID_COLUMN_NAME="RowID";
	static final String STARTING_VALIDATION_COLUMN_NAME="FieldName";

	static final String TESTCASE_ID_COLUMN_NAME="TestCaseID";
	static final String ENV_COLUMN_NAME="EnvID";
	static final String DATA_SHEET_ROW_ID="DataSheet##RowID";
	static final String SHEET_ROW_DELIMETER="##";


	static final String KEY_NAME_PAGE_SHEETS_ROWS="pageSheetName";
	static final String KEY_NAME_ROW_ID="rowID";
	static final String KEY_NAME_ENV_ID="envID";


	static final String LOCATOR_NAME_COLUMN="LocatorName";
	static final String LOCATOR_VALUE_COLUMN="LocatorValue";
	static final String LOCATOR_TYPE_COLUMN="LocatorType";

	static final String LOCATOR_XPATH="xpath";
	static final String ELEMENT_SEARCH_ERROR_MESSAGE="error occured during finding element on page";
	static final String DATE_FORMAT_PATTERN = "mm_dd_yyyy hh_mm_ss";

	String getTestIDPattern();
}
