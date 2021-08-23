package org.tfa.framework.utilities.testdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This is the Generic Utility class for reading the data from the Excel Data source. It has multiple generic methods which can be used for reading the data by row, column or Full data and get it stored in the MAP object.
 * @author gaurav.garg
 *
 */
public class ExcelPoiUtility {

	private static ExcelPoiUtility excelUtil;
	private static final Logger logger = Logger.getLogger(ExcelPoiUtility.class);

	private ExcelPoiUtility(){

	}

	public static  ExcelPoiUtility getObject(){
		if(excelUtil==null){
			excelUtil = new ExcelPoiUtility();
		}
		return excelUtil;
	}
	
	/**
	 * This method is to create the object of the Excel sheet from which we want to read the TestData.getObject().
	 * @param dataSheetPath This is the location where we have kept the Excel sheet file.
	 * @return It will return a Workbook object which has reference to the excel sheet.
	 * @throws IOException If file is not found then we will get this exception.
	 */
	public  Workbook getDataBook(String dataSheetPath) throws IOException{
		Workbook workbook = null;
		if(StringUtils.isNotBlank(dataSheetPath)) {
			URL fileURL = getClass().getClassLoader().getResource(dataSheetPath);
			String filePath = fileURL.getPath();

			File xlFile = new File(filePath);
			try (FileInputStream fis = new FileInputStream(xlFile)) {
				String[] arrPath = dataSheetPath.split("\\.");
				String fileExt = arrPath[1];
				if ("xlsx".equalsIgnoreCase(fileExt)) {
					workbook = new XSSFWorkbook(fis);
					logger.debug(dataSheetPath + " file found and workbook object successfully created");
				} else {
					workbook = new HSSFWorkbook(fis);
					logger.debug(dataSheetPath + " file found and workbook object successfully created" + dataSheetPath);
				}
			} catch (IOException e) {
				logger.error("No Data file found of name - " + dataSheetPath, e);
				throw e;
			} catch (Exception e) {
				logger.error("Exception occured while reading file - " + dataSheetPath, e);
				throw e;
			}
		}
		return workbook;
	}

	/**
	 * This method is to get the individual work sheet from the workbook.
	 * @param workBook Need to provide the workbook object which we have generated from the getDataBook function.
	 * @param sheetName Name of the sheet which you want to read from the workbook.
	 * @return it will return the sheet object with all rows and columns.
	 */
	public  Sheet getDataSheet(Workbook workBook, String sheetName){
		Sheet sheet=null;
		try{
			sheet=workBook.getSheet(sheetName);
		}catch(Exception e){
			logger.error( sheetName+" worksheet not found in workbook ", e);
		}

		return sheet;
	}

	public String getCellData(Row dataRow, int columnNumber, FormulaEvaluator formulaEvaluator){
		DataFormatter df = new DataFormatter();
		Cell curCell = dataRow.getCell(columnNumber, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		if(CellType.FORMULA==curCell.getCellTypeEnum()){
			return df.formatCellValue(curCell, formulaEvaluator);
		}else{
			return df.formatCellValue(curCell);
		}
	}

	/**
	 * This method is used to get the column number by providing the Column Name, because POI utility always looks for number, so we need the column number to get the TestData.getObject().
	 * @param sheet Sheet object which has the TestData.getObject().
	 * @param columnName Column name for which we need the column number.
	 * @return It will return integer value.
	 */
	public  int getColumnNumberByColumnName(Sheet sheet, String columnName) {
		Row firstRowColumns=sheet.getRow(0);
		int columnNumber=-1;
		int columnCount=firstRowColumns.getLastCellNum();
		for(int i=0; i<=columnCount-1; i++){
			if(firstRowColumns.getCell(i).getStringCellValue().toLowerCase().contains(columnName.toLowerCase())){
				columnNumber=i;
				break;
			}
		}
		return columnNumber;
	}

	/**
	 * This method is to get the Row number for the provided Row ID.
	 * @param sheet Sheet object from which we want to have the row number.
	 * @param columnName Name of the column to determine the Cell value.
	 * @param rowID Actual Row value for which we need the Row number.
	 * @return It will return the integer value.
	 */
	public  int getRowNumberByRowID(Sheet sheet, String columnName,String rowID){
		logger.debug("columnName:" + columnName);
		logger.debug("rowID:" + rowID);
		int rowCount=sheet.getLastRowNum();
		int columnNumber=getColumnNumberByColumnName(sheet, columnName);
		int rowNumber=-1;
		for(int i=1; i<=rowCount; i++){
			Cell cell=sheet.getRow(i).getCell(columnNumber);
			if(cell !=null && cell.getStringCellValue().equalsIgnoreCase(rowID)){
				rowNumber=i;
				break;
			}
		}
		return rowNumber;
	}

	/*  below method is actually returning multiple row numbers of testcase with same testcase id in a List
	 * this method is being called in getTestDataRowsList method of TestData.java class
	 */
	public  List<Integer> getRowNumbersListByRowID(Sheet sheet, String columnName,String rowID){
		List<Integer> listData=new ArrayList<>();
		int rowCount=sheet.getLastRowNum();
		int columnNumber=getColumnNumberByColumnName(sheet, columnName);
		for(int i=1; i<=rowCount; i++){
			Cell cell=sheet.getRow(i).getCell(columnNumber);
			if(cell !=null && cell.getStringCellValue().equalsIgnoreCase(rowID)){
				listData.add(i);
				
			}
		}
		return listData;
	}
	
	
}
