package com.hai.test.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.nacos.common.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Excel处理
 * @author ZXH
 */
@Slf4j
public class PoiUtil {

    /**
     * 从inputstream中读取Excel文件
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static List<String[]> readExcel(InputStream inputStream,int ignoreName) throws IOException {
        Workbook workbook = null;
        try {
            // 扩展后缀名.xlsx
            workbook = new XSSFWorkbook(inputStream);
        } catch (Exception ex) {
            // 扩展后缀名.xlx
            workbook = new HSSFWorkbook(inputStream);
        }
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if (workbook != null) {
            //获得当前sheet工作表
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                return null;
            }
            //获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            //循环除了第一行和第二行的所有行
            for (int rowNum = firstRowNum + ignoreName; rowNum <= lastRowNum; rowNum++) {
                //获得当前行
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                //获得当前行的开始列
                int firstCellNum = row.getFirstCellNum();
                //获得当前行的列数
                int lastCellNum = row.getLastCellNum();
                if (firstCellNum < 0 || lastCellNum < 0) {
                    continue;
                }
                String[] cells = new String[lastCellNum];
                //循环当前行
                boolean nullFlag = false;
                for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                    Cell cell = row.getCell(cellNum);
                    cells[cellNum] = getCellValue(cell);
                    if (StringUtils.isNotBlank(cells[cellNum])){
                        nullFlag = true;
                    }
                }
                if (nullFlag){
                    list.add(cells);
                }
            }
            workbook.close();
        }
        return list;
    }


    /**
     * 将内容转换为String
     *
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellType() == CellType.NUMERIC) {
            String value = new BigDecimal(String.valueOf(cell)).stripTrailingZeros().toPlainString();
            cell.setCellValue(value);
        }
        //判断数据的类型
        switch (cell.getCellType()) {
            //数字
            case NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            //字符串
            case STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            //Boolean
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            //公式
            case FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            //空值
            case BLANK:
                cellValue = "";
                break;
            //故障
            case ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

}
