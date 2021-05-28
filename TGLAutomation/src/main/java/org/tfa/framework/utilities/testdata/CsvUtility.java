package org.tfa.framework.utilities.testdata;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180ParserBuilder;

public class CsvUtility {

	private static CsvUtility csvUtil;
	private static final Logger logger = Logger.getLogger(CsvUtility.class);

	private CsvUtility(){

	}


	public static  CsvUtility getObject(){
		if(csvUtil==null){
			csvUtil = new CsvUtility();
		}
		return csvUtil;
	}

	public  List<String[]> getAllLinesFromCSV(String csvFilePath){
		List<String[]> allLines=new ArrayList<>();
		
		
		try {
			Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), "UTF-8"));
			@SuppressWarnings("resource")
			
			List<String[]> csvDataList = new CSVReaderBuilder(reader).withCSVParser(new RFC4180ParserBuilder().build()).build().readAll();
			for(int i=0; i<=csvDataList.size()-1;i++) {
				String[] csvDataArray= csvDataList.get(i);
				String[] optimizedCsvDataArray =  new String[csvDataArray.length];
				for(int j=0;j<=optimizedCsvDataArray.length-1;j++) {

					String csvData=csvDataArray[j].replace("\"", "");
					byte[] byteArr = csvData.getBytes("UTF-8"); 
					csvData  = new String(byteArr, Charset.forName("UTF-8"));
					optimizedCsvDataArray[j]=csvData;    
				}
				allLines.add(optimizedCsvDataArray);
			}
			csvDataList.clear();
		} catch (Exception e1) {
			logger.error(e1);
		}
		
		return allLines;
	}
	public  int getColumnNumberByColumnName(String[] arrColumns, String columnName) {

		int columnNumber=-1;
		for(int i=0; i<=arrColumns.length-1; i++){
			if(arrColumns[i].trim().toLowerCase().contains(columnName.toLowerCase())){
				columnNumber=i;
				break;
			}
		}
		return columnNumber;
	}



	public  int getRowNumberByRowID(List<String[]> allLines, String columnName,String rowID){
		logger.debug("columnName:" + columnName);
		logger.debug("rowID:" + rowID);
		int rowCount=allLines.size();
		int columnNumber=getColumnNumberByColumnName(allLines.get(0), columnName);
		int rowNumber=-1;
		for(int i=1; i<=rowCount-1; i++){	
			
			String rowIdExcel=allLines.get(i)[columnNumber].trim();	

			if(rowIdExcel.equalsIgnoreCase(rowID.trim())){
				rowNumber=i;
				break;
			}
		}
		return rowNumber;
	}

	/*  below method is actually returning multiple row numbers of testcase with same testcase id in a List
	 * this method is being called in getTestDataRowsList method of TestData.java class
	 */
	public  List<Integer> getRowNumbersListByRowID(List<String[]> listLines, String columnName,String rowID){
		List<Integer> listData=new ArrayList<>();
		int rowCount=listLines.size();
		int columnNumber=getColumnNumberByColumnName(listLines.get(0), columnName);
		for(int i=1; i<=rowCount; i++){
			String cellData=listLines.get(i)[columnNumber].trim();
			if(cellData !=null && cellData.equalsIgnoreCase(rowID)){
				listData.add(i);

			}
		}
		return listData;
	}

}
