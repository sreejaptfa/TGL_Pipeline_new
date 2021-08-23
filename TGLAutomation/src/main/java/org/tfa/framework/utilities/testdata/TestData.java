package org.tfa.framework.utilities.testdata;



import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.tfa.framework.core.Constants;


public abstract class TestData {

	private  static TestData testData;

	public abstract Map<String, String> getTestCaseInfoMap();

	public abstract Map<String, String> getTestCaseDataMap();

	public abstract Map<String, String> getEnvironmentDataMap();

	public abstract Map<String, Map<String, String>> getLocatorDataMap();

	public abstract Map<String, String> getEnvironmentInfo(String envID)  throws IOException;

	public  abstract  void getTestCaseInfo( int rowNumber) throws IOException ;

	public  abstract  void getValidationData( int rowNumber) throws IOException ;

	public  abstract  void getPageData() throws IOException ;

	public  abstract  Map<String, Map<String, String>> getLocatorInfo() throws IOException;

	public abstract  List<Integer> getTestDataRowsList(String testCaseId);

	public abstract  int getTestDataRowNumber(String testCaseId);

	public abstract void arrangeTestCaseData(int dataRowNumber);

	private static Properties prop;	

	public static void getConfig() {

		try {	
			if(prop==null) {
				FileReader reader=new FileReader(Constants.CONFIG_PATH);        
				prop=new Properties();  
				prop.load(reader);	
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}

	public static TestData getObject(){
		getConfig();
		if(testData==null && "excel".equalsIgnoreCase(prop.getProperty(Constants.CONFIG_TESTDATA_TYPE).trim())){
			testData=new ExcelTestData();
		}else if(testData==null && "csv".equalsIgnoreCase(prop.getProperty(Constants.CONFIG_TESTDATA_TYPE).trim())) {
			// testData=new CsvTestData();
		}	
		return testData;
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




}
