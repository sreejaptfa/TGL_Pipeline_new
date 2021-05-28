
package org.tfa.framework.core;

import static org.tfa.framework.core.Constants.KEY_NAME_PAGE_SHEETS_ROWS;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.tfa.framework.utilities.testdata.TestData;


public class BaseTestMethods {

	private  TestData testData;
	protected  Map <String, String> testDataMap;
	protected Constants  constants; // extending class to instantiate this object
	int iterationCounter;
	private  List<Map<String, String>> totalDataList=null;
	private  List<Integer> testIdRowsList;
    private  String testIdPattern;
    

	public Constants getConstants() {
		return constants; // test classes to override this method
	}

	private static final Logger logger = Logger.getLogger(BaseTestMethods.class);

	@BeforeSuite
	public void beforeSuite() {

		logger.info("Test Suite Execution method has been started");
		try {
			testData=TestData.getObject();		
			testData.getLocatorInfo();
		} catch (IOException e) {
			logger.info( "Locators Repository not initialized Properly", e);
		}
	}

	/**
	 * This method is to provide test data to test cases
	 * feature1: If TestCaseDriver worksheet of MasterSheet workbook has multiple rows with same test case id then it will fetch
	 *           data from duplicate rows and it will execute the test case multiple times with different test data row in each iteration 
	 *           without any loop in code
	 *           e.g. if any testcase testTfact1293 is written n times in TestCaseDriver worksheet then testcase testTfact1293 will run n time 
	 * 
	 * feature2: If testcase id is unique in TestCaseDriver worksheet then it will run one time only
	 * how to use : use dataProvider="data" after @Test and provide one argument in testcase
	 * @Test(dataProvider="data")
	    public void testTfact1293ValidateContinuumAccountCreation(String data){
	 *  }
	 *  
	 * feature3: If you are not using dataProvider="data" after @Test annotation then also it will run 1 time 
	 * Logic:- using  getTestDataRowsList method of TestData class we are fetching count of testdata rows on behalf of testcase id in TestCaseDriver worksheet
	 *   after that keeping every data row in separate hashmaps after that saving these hashmaps in one list
	 *   after that we are passing this data to testcase via testng
	 */
	@DataProvider(name="data")
	public Object[][] testData(Method testMethod) throws IOException{
		testData=TestData.getObject();		
		iterationCounter=0;
		String testID = testData.getTestCaseID(testMethod,testIdPattern);
		testIdRowsList=null;  
		testIdRowsList=testData.getTestDataRowsList(testID);
		totalDataList=new ArrayList<>();
		List<String> pageSheetNameList=new ArrayList<>();
		for(int i=0; i<=testIdRowsList.size()-1; i++){
			testData.arrangeTestCaseData(testIdRowsList.get(i));
			totalDataList.add(testData.getTestCaseDataMap());
			pageSheetNameList.add(testData.getTestCaseInfoMap().get(KEY_NAME_PAGE_SHEETS_ROWS));
		}

		Object[][] iterationArray=new Object[totalDataList.size()][1];
		for(int i=0; i<=iterationArray.length-1; i++){
			iterationArray[i][0]=pageSheetNameList.get(i);
		}
			
		return iterationArray;
	}

	
	
	/**
	 * This method has been changed to execute testcases with or without dataProvider feature of testng
	 *  earlier this method was arranging testdata before executing test case and launching browser
	 *  but now it is arranging data if dataProvider feature is not being used. 
	 *  If dataProvider is being used then it will use data that was fetched by dataProvidrer
	 */	
	@BeforeMethod
	public void beforeMethod(Method testMethod)  {	
		testData=TestData.getObject();
        testIdPattern = getConstants().getTestIDPattern();
		if(testIdRowsList==null){
			logger.info( "@beforeMethod method has been started for "+testMethod.getName());
			String testID = testData.getTestCaseID(testMethod,testIdPattern);
			int rowNumber=testData.getTestDataRowNumber(testID);
			testData.arrangeTestCaseData(rowNumber);
			testDataMap=testData.getTestCaseDataMap();
		}else{
			testDataMap=totalDataList.get(iterationCounter);
			iterationCounter=iterationCounter+1;
			if(iterationCounter==totalDataList.size()){
				testIdRowsList=null;
				iterationCounter=0;
			}
		}
		WebDriverUtil.getObject().launchDriver();
		logger.info( "@beforeMethod method has been completed for "+testMethod.getName());
		logger.info( testMethod.getName()+" execution has been started");
		
	}

	@AfterMethod
	public  void afterMethod(ITestResult result) {
		WebDriverUtil webUtil=WebDriverUtil.getObject();

		if (!result.isSuccess()) {
			String methodName=result.getName().trim();
			webUtil.takeScreenShot(methodName);
		} 
		webUtil.stopDriver();
		logger.info( "@afterMethod method has been completed");
	}

	@AfterSuite
	protected void afterSuite() {
		logger.info( "Test Suite Execution method has been completed");
	}

}

