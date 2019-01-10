package com.langlang.book.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSONObject;

/**
 * @author  xiehongbin
 */
public class ExcelUtil {

    /**
     * 实体类导出excel
     * @param title 标题，sheet名称 不能为空
     * @param headers 列名 String[] 根据实体定义的字段顺序
     * @param dataset 实体类集合
     * @param out  输出流
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static HSSFWorkbook exportEntityExcel(String title, String[] headers, Collection<Object> dataset, OutputStream out) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);
        HSSFCellStyle style = workbook.createCellStyle();
        // 自动换行
        style.setWrapText(true);
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("微软雅黑");
        style.setFont(font);
        HSSFCellStyle style2 = workbook.createCellStyle();
        HSSFCellStyle styleNum = workbook.createCellStyle();
        style2.setWrapText(true);
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("微软雅黑");
        font2.setFontHeightInPoints((short) 10);
        style2.setFont(font2);
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints((short) 32);
        HSSFCell cell = null;
        HSSFRichTextString text = null;
        String textValue = null;
        Object value = null;
        // 添加head 标题列
        for (short i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
            text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            sheet.setColumnWidth(i, 24 * 256);
        }
        Iterator<Object> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            ++index;
            row = sheet.createRow(index);
            row.setHeightInPoints((short) 20);
            Object t = (Object) it.next();
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                cell = row.createCell(i);
                if (i == 0) {
                    cell.setCellStyle(styleNum);
                } else {
                    cell.setCellStyle(style2);
                }
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                    value = getMethod.invoke(t, new Object[] {});
                    field.setAccessible(true);
                    textValue = String.valueOf(value);
                    if (textValue != null) {
                        cell.setCellValue(textValue);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }

    /**
     * 导出实体为Map的集合
     * @param title 标题
     * @param headers 标题 与key值顺序对应
     * @param keys map的Key
     * @param dataset 数据集合
     * @param out
     */
    public static HSSFWorkbook exportMapExcel(String title, String[] headers, String[] keys, Collection<Map<String, Object>> dataset,
                                              OutputStream out) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);
        HSSFCellStyle style = workbook.createCellStyle();
        // 自动换行
        style.setWrapText(true);
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("微软雅黑");
        style.setFont(font);
        HSSFCellStyle style2 = workbook.createCellStyle();
        HSSFCellStyle styleNum = workbook.createCellStyle();
        style2.setWrapText(true);
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("微软雅黑");
        font2.setFontHeightInPoints((short) 10);
        style2.setFont(font2);
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints((short) 32);
        HSSFCell cell = null;
        HSSFRichTextString text = null;
        String textValue = null;
        String key = null;
        Object value = null;
        // 添加head 标题列
        for (short i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
            text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            sheet.setColumnWidth(i, 24 * 256);
        }
        Iterator<Map<String, Object>> it = dataset.iterator();
        Map<String, Object> t = null;
        int index = 0;
        while (it.hasNext()) {
            ++index;
            row = sheet.createRow(index);
            row.setHeightInPoints((short) 20);
            t = it.next();
            for (short i = 0; i < keys.length; i++) {
                cell = row.createCell(i);
                if (i == 0) {
                    cell.setCellStyle(styleNum);
                } else {
                    cell.setCellStyle(style2);
                }
                try {
                    key = keys[i];
                    value = t.get(key);
                    if (value == null) {
                        textValue = "";
                    }else {
                        textValue = String.valueOf(value);
                    }
                    if (textValue != null) {
                        cell.setCellValue(textValue);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            if (out != null) {
                workbook.write(out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return workbook;
    }


    /**
     * 导出实体为JSONObject的集合
     * @param title 标题
     * @param headers 标题 与key值顺序对应
     * @param keys map的Key
     * @param dataset 数据集合
     * @param out
     */
    public static HSSFWorkbook exportJSONObjectExcel(String title, String[] headers, String[] keys, Collection<JSONObject> dataset,
                                                     OutputStream out) {
        return exportJSONObjectExcel(title, headers, keys, dataset, out, null);
    }

    /**
     * 导出实体为JSONObject的集合
     * @param title 标题
     * @param headers 标题 与key值顺序对应
     * @param keys  map的Key
     * @param dataset 数据集合
     * @param out 流
     * @param code 字典码 key:需要转换的字段 value: 字典码键值对
     */
    public static HSSFWorkbook exportJSONObjectExcel(String title, String[] headers, String[] keys, Collection<JSONObject> dataset,
                                                     OutputStream out,JSONObject code) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);
        HSSFCellStyle style = workbook.createCellStyle();
        // 自动换行
        style.setWrapText(true);
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("微软雅黑");
        style.setFont(font);
        HSSFCellStyle style2 = workbook.createCellStyle();
        HSSFCellStyle styleNum = workbook.createCellStyle();
        style2.setWrapText(true);
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("微软雅黑");
        font2.setFontHeightInPoints((short) 10);
        style2.setFont(font2);
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints((short) 32);
        HSSFCell cell = null;
        HSSFRichTextString text = null;
        String textValue = null;
        String key = null;
        Object value = null;
        // 添加head 标题列
        for (short i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
            text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            sheet.setColumnWidth(i, 24 * 256);
        }
        Iterator<JSONObject> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            ++index;
            row = sheet.createRow(index);
            row.setHeightInPoints((short) 20);
            JSONObject t = it.next();
            for (short i = 0; i < keys.length; i++) {
                cell = row.createCell(i);
                if (i == 0) {
                    cell.setCellStyle(styleNum);
                } else {
                    cell.setCellStyle(style2);
                }
                try {
                    key = keys[i];
                    value = t.get(key);
                    textValue = null;
                    if (value == null) {
                        textValue = "";
                    }else {
                        textValue = String.valueOf(value);
                    }
                    //字典码转换
                    if (code != null) {
                        if (code.containsKey(key)) {
                            JSONObject codeJson = code.getJSONObject(key);
                            textValue = codeJson.getString(textValue);
                        }
                    }
                    if (textValue != null) {
                        cell.setCellValue(textValue);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            if (out != null) {
                workbook.write(out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return workbook;
    }

    public static void main(String[] args) throws FileNotFoundException{
        Collection<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 1; i++) {
            Map<String, Object> map = new HashMap<String, Object>(16);
            map.put("name", i + "-" + i);
            map.put("age", i * 10);
            maps.add(map);
        }
        exportMapExcel("map测试", new String[] { "名字", "年龄" }, new String[] { "name", "age" }, maps,
                new FileOutputStream("c:/22.xls"));
    }
}