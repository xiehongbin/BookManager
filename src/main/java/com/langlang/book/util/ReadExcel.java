package com.langlang.book.util ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author HongShengWei
 * @date 2018/12/04
 */
public class ReadExcel {

	/**
	 * 构造方法
	 */
    public ReadExcel() {
    }

	/**
	 * 错误信息
	 */
    private String errorMsg;
    /**
     * 获取错误信息
     * @return
     */
    public String getErrorInfo() {
        return errorMsg;
    }
    
    /**
     * 读EXCEL文件，获取信息集合
     * @param mFile
     * @return
     */
    public List<Map<String,Object>> getExcelInfo(MultipartFile mFile, String[] keys) {
    	// 获取文件名
        String fileName = mFile.getOriginalFilename();
        try {
        	//验证文件名是否合格
            if (!validateExcel(fileName)) {
                return null;
            }
            //CSV处理
            if(isCSV(fileName)) {
            	return readCSV(mFile.getInputStream(), keys);
            }
            //根据文件名判断文件是2003版本还是2007版本
            boolean isExcel2003 = true;
            if (isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            return createExcel(mFile.getInputStream(), isExcel2003, keys);
        } catch (Exception e) {
        	errorMsg = "模板格式不正确";
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据excel版本返回对应的Workbook
     * @param is 输入流
     * @param isExcel2003 excel是2003还是2007版本
     * @return
     */
    public List<Map<String,Object>> createExcel(InputStream is, boolean isExcel2003, String[] keys) {
        try {
            Workbook wb = null;
            if (isExcel2003) {
            	//当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(is);
            } else {
            	//当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
            //读取Excel里面的信息
            return readExcel(wb, keys);
        } catch (IOException e) {
        	errorMsg = "格式错误";
            e.printStackTrace();
        } finally {
        	if(is != null){
                try {
                	is.close();
                	is = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    /**
     * 读取Excel里面的信息
     * @param wb
     * @param keys
     * @return
     */
	private List<Map<String,Object>> readExcel(Workbook wb, String[] keys) {
        //得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        //得到Excel的行数
        int totalRows = sheet.getPhysicalNumberOfRows();
        //得到Excel的列数(前提是有行数)
        int totalCells = 0;
        if (totalRows > 1 && sheet.getRow(0) != null) {
        	totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        //循环Excel行数
        for (int r = 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            if(isRowEmpty(row)) {
            	continue;
            }
            //循环Excel的列
            Map<String,Object> map = new HashMap<String,Object>(keys.length);
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                if (cell != null) {
                	switch (cell.getCellTypeEnum()) {
	                    case NUMERIC:
	                    	if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
		                    	map.put(keys[c], String.valueOf(cell.getDateCellValue()));
	                        } else {
		                    	map.put(keys[c], String.valueOf(cell.getNumericCellValue()));
	                        }
	                        break;
	                    case STRING:
	                    	map.put(keys[c], cell.getStringCellValue());
	                        break;
	                    default:
	                    	break;
                	}
                }
            }
            //添加到list
            list.add(map);
        }
        return list;
    }
    
    /**
     * 读取CSV里面的信息
     * @param is
     * @param keys
     * @return
     */
    private List<Map<String,Object>> readCSV(InputStream is, String[] keys) {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        InputStreamReader inputStream = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(inputStream);
        int errorRow = 1;
        try { 
            String line = ""; 
            while ((line = br.readLine()) != null) {
            	if(errorRow == 1) {
            		//跳过第一行-标题
                	errorRow ++;
            		continue;
            	}
            	errorRow ++;
            	String[] row = line.split(",");
            	if(keys.length == row.length) {
	            	Map<String,Object> mRow = new HashMap<String,Object>(keys.length);
	            	for(int i=0; i<row.length; i++) {
	                	mRow.put(keys[i], row[i]);
	            	}
	            	list.add(mRow);
            	}else {
                	errorMsg = "数据格式错误:第" + errorRow + "行";
            	}
            }
        }catch (Exception e) {
        	errorMsg = "数据格式错误:第" + errorRow + "行";
        	e.printStackTrace();
        }finally {
            if(br != null){
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        	if(is != null){
                try {
                	is.close();
                	is = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * 验证EXCEL文件
     * 
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath) {
        if (filePath == null) {
            return false;
        }
        if(!(isExcel2003(filePath) || isExcel2007(filePath) || isCSV(filePath))) {
        	return false;
        }
        return true;
    }

    /**
     * 是否是2003的excel，返回true是2003
     * @param filePath
     * @return
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 是否是2007的excel，返回true是2007
     * @param filePath
     * @return
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
    
    /**
     * 是否是CSV，返回true是CSV
     * @param filePath
     * @return
     */
    public static boolean isCSV(String filePath) {
        return filePath.matches("^.+\\.(?i)(csv)$");
    }
    
    /**
     * 校验行数据是否为空
     * @param row
     * @return
     */
	public static boolean isRowEmpty(Row row) {
    	for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
    		Cell cell = row.getCell(c);
	    	if (cell != null && cell.getCellTypeEnum() != CellType.BLANK) {
                return false;
            }
    	}
    	return true;
	}
}
