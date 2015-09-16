package com.ctt.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * 1. 多虑多种情况， 尽量按照需求取出尽可能少需要处理的数据格式 
 * 2. 写通用类型转换处理， 解决bug问题，写继承， 判断 ，解决版本问题
 * 
 * 通用处理情况： a：获取单个项目  具备行业排除功能
 * 
 * 
 * @author CTT
 *
 */
public class ExcelHelper {
	
	
	/**
	 * Excel 2003
	 */
	private final static String XLS = "xls";
	/**
	 * Excel 2007
	 */
	private final static String XLSX = "xlsx";
	

	private List<String> columnList;
	
	public ExcelHelper(){
		
	}
	
	public ExcelHelper(List<String> columnList){
		this.columnList  = columnList;
	}
	
	public ExcelHelper(String[] columnList){
		if(columnList!=null && columnList.length >0){
			this.columnList = new ArrayList<String>();
			for(String s:columnList){
				this.columnList.add(s);
			}
		}
	}
	
  public static void main( String[] args) throws IOException {
//	  ExcelHelper xlsMain = new ExcelHelper();
//	  File file = new File("C:\\demo.xls");
//	  xlsMain.getSheetData(file,"Sheet1",0,0);
	  
	  
	  ExcelHelper xlsMain = new ExcelHelper(new String[]{"i1","i2","i3","i4","i5"});
	  File file = new File("C:\\demo.xlsx");
	  xlsMain.getSheetDataMap(file,"Sheet1",0,0);
  }
  
  /**
   * @param is 文件流
   * @param sheelName 表名称
   * @param rowNum 数据开始行 0-
   * @param colNum 数据开始列 0-
   */
  public List<List<String>> getSheetData(File  file,String sheetName,int rowNum,int colNum)throws IOException{
	  
	  List<List<String>> data = null;
	  Workbook workbook = null;
	  //判断文件类型及文件版本，创建核心处理类
	 String  extensionName = FilenameUtils.getExtension(file.getName());
	 if (extensionName.toLowerCase().equals(XLS)) {
		workbook = new HSSFWorkbook(new FileInputStream(file));
		
		
	 } else if (extensionName.toLowerCase().equals(XLSX)) {
		workbook = new XSSFWorkbook(new FileInputStream(file));
	 }
	 
	 data =  getExcelData(workbook,sheetName,rowNum,colNum);
//		int i = 1;
//		for(List<String> s:data){
//			for(String s1:s){
//				System.out.print("  "+s1);
//			}
//			System.out.print("  "+i);
//			i++;
//			System.out.println();
//		}
//		
//	 
	 return  data;
  }
  
    /**
     * 
     * @param file
     * @param sheetName
     * @param rowNum
     * @param colNum
     * @return
     * @throws IOException
     */
	public List<Map<String,Object>> getSheetDataMap(File  file,String sheetName,int rowNum,int colNum)throws IOException{
		List<Map<String,Object>>  data = null;
		//判断文件类型及文件版本，创建核心处理类
		Workbook workbook = null;
		 String  extensionName = FilenameUtils.getExtension(file.getName());
		 if (extensionName.toLowerCase().equals(XLS)) {
			 workbook = new HSSFWorkbook(new FileInputStream(file));
			
		 } else if (extensionName.toLowerCase().equals(XLSX)) {
			 workbook = new XSSFWorkbook(new FileInputStream(file));
		 }
		 data =  getExcelDataMap(workbook,sheetName,rowNum,colNum);
			int i = 1;
			for(Map<String,Object> s:data){
				Set<String> keys = s.keySet();
				for(String s1:keys){
					System.out.print("   "+s1+"-"+s.get(s1));
				}
				System.out.print("   "+i);
				i++;
				System.out.println();
			}
		 return data;
	  }
  
  /**
   * 
   * @param workbook
   * @param sheetName
   * @param rNum
   * @param cNum
   * @return
   * @throws IOException
   */
  private List<List<String>> getExcelData(Workbook workbook,String sheetName,int rNum,int cNum )
		  throws IOException{
	  List<List<String>> rowList = null;
	  Sheet sheet = workbook.getSheet(sheetName);
	  if(sheet != null){
		  rowList = new ArrayList<List<String>>();
		// 循环行Row 
	      for(int rowNum = rNum; rowNum <= sheet.getLastRowNum(); rowNum++){
	    	Row row = sheet.getRow( rowNum);
	        if(row == null){
	        	continue;
	        }
	        List<String> cellList = new ArrayList<String>();
	        // 循环列Cell  
	        for(int cellNum = cNum; cellNum <= row.getLastCellNum(); cellNum++){
	          Cell hssfCell = row.getCell( cellNum);
	          if(hssfCell == null){
	        	  cellList.add("");
	            continue;
	          }
	          cellList.add(getValue(hssfCell));
	        }
	        rowList.add(cellList);
	      }
	  } 
	  return rowList;
  }
  
  
  private List<Map<String,Object>> getExcelDataMap(Workbook workbook,String sheetName,int rNum,int cNum )
		  throws IOException{
	  List<Map<String,Object>>  rowList = null;
	  // 解析公式结果
	  FormulaEvaluator evaluator = workbook.getCreationHelper()
				.createFormulaEvaluator();
	  Sheet sheet = workbook.getSheet(sheetName);
	  if(sheet != null){
		  rowList = new ArrayList<Map<String,Object>>();
		// 循环行Row 
	      for(int rowNum = rNum; rowNum <= sheet.getLastRowNum(); rowNum++){
	    	Row row = sheet.getRow( rowNum);
	        if(row == null){
	        	continue;
	        }
	        
	        Map<String,Object> map = new HashMap<String,Object>();
	        // 循环列Cell
	        int  colNum = 0;
	        
	        for(int cellNum = cNum; cellNum <= row.getLastCellNum(); cellNum++){
	          Cell hssfCell = row.getCell( cellNum);
	          if(hssfCell == null){
	            continue;
	          }
	          map.put(columnList.get(colNum),getValue(hssfCell));
	          colNum++;
	        }
	        rowList.add(map);
	      }
	  }
	  return rowList;
  }
  
  
  @SuppressWarnings("static-access")
  private String getValue(Cell hssfCell){
    if(hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN){
      return String.valueOf( hssfCell.getBooleanCellValue());
    }else if(hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC){
    	// 这里的日期类型会被转换为数字类型，需要判别后区分处理
		if (DateUtil.isCellDateFormatted(hssfCell)) {
			return String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			.format(hssfCell.getDateCellValue()));
		} else {
			return String.valueOf( hssfCell.getNumericCellValue());
		}
    }else{
      return String.valueOf( hssfCell.getStringCellValue());
    }
  }
  
}

