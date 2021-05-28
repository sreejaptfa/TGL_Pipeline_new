package org.tfa.tgl.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVWriter;

public class CsvConverter {

	public static void main(String[] args) throws IOException {
        
		convertExcelToCsv( "src/test/resources/TestData/MasterSheet.xlsx",  "src/test/resources/TestData");
		convertExcelToCsv( "src/test/resources/TestData/TestData.xlsx",  "src/test/resources/TestData/PageData");
	}
    
    public static void convertExcelToCsv(String xlFilePath, String csvFileFolderPath) {


		Workbook wbook=getDataBook(xlFilePath);
		int sheetCount=wbook.getNumberOfSheets();

		for(int i=0; i<=sheetCount-1;i++) {

			Sheet sheetObj=wbook.getSheetAt(i);
			List<String[]> listData=new ArrayList<String[]>();
			String sheetName=sheetObj.getSheetName();
			Row columnRowObj=sheetObj.getRow(0);
			int columnCount=columnRowObj.getLastCellNum();
			String[] arrColumn=new String[columnCount];
			for(int c=0; c<=columnCount-1;c++) {			
				Cell columnCell=columnRowObj.getCell(c, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				String columnName=columnCell.getStringCellValue();			
				arrColumn[c]=columnName;
			}    			
			listData.add(arrColumn);
			for(int j=1; j<=sheetObj.getLastRowNum();j++) {
				Row dataRowObj=sheetObj.getRow(j); 			
				if(dataRowObj==null) {break;}
				String[] arrValues=new String[columnCount];
				for(int k=0; k<=columnCount-1;k++) {

					try {
						Cell cellObj=dataRowObj.getCell(k,  MissingCellPolicy.CREATE_NULL_AS_BLANK);
						String dataValue=getCellData(cellObj);						
						arrValues[k]=escapeSpecialCharacters(dataValue);
					}catch(Exception e) {
						System.out.println( "Problem At "+sheetName+" Sheet and Row-"+j+" and Column-"+k);
					}	

				}
				listData.add(arrValues);
			}	

			createCsvFile(csvFileFolderPath, sheetName, listData);

		}
    	


    }
    public static String escapeSpecialCharacters(String data) {
    	if(data.contains("OpportunityDeletionMessage")) {
           System.out.println("");
    	}
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'") || data.contains("’")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
	
	public static Workbook getDataBook(String wbookPath) {

		FileInputStream fis;
		try {
			fis = new FileInputStream(wbookPath);
			return new XSSFWorkbook(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	

	}

	public static void createCsvFile(String csvFileFolderPath, String csvFileName, List<String[]> listData) {
        
		File file=new File(csvFileFolderPath);
		if( !file.exists()) {
			file.mkdir();
		}		
		file = new File(csvFileFolderPath+"/"+csvFileName+".csv");
		CSVWriter writer =null; 

		try {
			
			Writer outputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			writer = new CSVWriter(outputWriter);
		} catch (IOException e) {

			e.printStackTrace();
		} 
		for(int i=0; i<=listData.size()-1;i++) {

			String[] rowDataArray=listData.get(i);

			writer.writeNext(rowDataArray);

		}
		try {
			writer.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}




	public static String getCellData(Cell cellObj) {

		if(cellObj.getCellTypeEnum()==CellType.NUMERIC) {
			Double d=cellObj.getNumericCellValue();
			Integer iValue=d.intValue();	
			return iValue.toString();
		}else {
			return cellObj.getStringCellValue();
		}
	}


}
