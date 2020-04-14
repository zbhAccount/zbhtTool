package com.hutool.demo.girl.excel;

import com.hutool.demo.girl.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：bohan.zhou@ucarinc.com
 * @date ：Created in 2020/4/13 13:59
 * @description：
 * @version: $
 */
@Component
public class NewExcel extends BaseNewExcel{
    @Override
    public void fillWorkbookDataByTemplate() {
        sheet = workbook.getSheetAt(0);
        // 列宽
        sheet.setColumnWidth(0, width);

        Row row = sheet.createRow(4);
        // 行高
        row.setHeightInPoints(height);

        Map<String, Object> map = new HashMap<>();
        map.put("borderStyle", BorderStyle.THIN);
        map.put("indexedColors", IndexedColors.BLACK.getIndex());
        map.put("horizontalAlignment", HorizontalAlignment.CENTER);
        map.put("verticalAlignment", VerticalAlignment.BOTTOM);

        Map<String, Object> properties = getProMap();

        CellStyle cellStyle = ExcelUtil.getCellStyle(workbook, map);
        bodyStyle = workbook.createCellStyle();
        bodyStyle.cloneStyleFrom(cellStyle);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("技术部");
        CellUtil.setCellStyleProperties(cell0, properties);

        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.cloneStyleFrom(cellStyle);
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle1.setDataFormat(
                createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
        Cell cell1 = row.createCell(1);
        cell1.setCellValue(new Date());
        cell1.setCellStyle(cellStyle);

        Cell cell2 = row.createCell(2);
        cell2.setCellValue(2121);
        cell2.setCellStyle(bodyStyle);

        Cell cell3 = row.createCell(3);
        cell3.setCellValue("this a string");
        cell3.setCellStyle(bodyStyle);

        Cell cell4 = row.createCell(4);
        cell4.setCellStyle(bodyStyle);
        ExcelUtil.mergeCell(sheet, 4, 4, 3, 4);

        table.put("技术部","黄衫","23");

        Cell cell5 = row.createCell(5);
        cell5.setCellStyle(bodyStyle);
        cell5.setCellValue(table.get("技术部","黄衫"));

        String[] formulaString = new String[]{"维持", "恢复", "调整"};
        Map<String, Integer> cellMap = new HashMap<>();
        cellMap.put(RowCol.FIRSTROW.getValue(), 4);
        cellMap.put(RowCol.LASTROW.getValue(), 200);
        cellMap.put(RowCol.FIRSTCOL.getValue(), 3);
        cellMap.put(RowCol.LASTCOL.getValue(), 4);
        setDropDownAndHidden(formulaString, cellMap);
    }

    @Override
    public Map<String, Object> getProMap() {
        Map<String, Object> properties = new HashMap<>();
        // 边框线条
        properties.put(CellUtil.BORDER_TOP, BorderStyle.THIN);
        properties.put(CellUtil.BORDER_BOTTOM, BorderStyle.THIN);
        properties.put(CellUtil.BORDER_LEFT, BorderStyle.THIN);
        properties.put(CellUtil.BORDER_RIGHT, BorderStyle.THIN);
        // 边框颜色
        properties.put(CellUtil.TOP_BORDER_COLOR, IndexedColors.RED.getIndex());
        properties.put(CellUtil.BOTTOM_BORDER_COLOR, IndexedColors.RED.getIndex());
        properties.put(CellUtil.LEFT_BORDER_COLOR, IndexedColors.RED.getIndex());
        properties.put(CellUtil.RIGHT_BORDER_COLOR, IndexedColors.RED.getIndex());
        // 对齐方式
        properties.put(CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.BOTTOM);
        properties.put(CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
        // 背景色
        properties.put(CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.GREEN.getIndex());
        properties.put(CellUtil.FILL_PATTERN, FillPatternType.SOLID_FOREGROUND);
        return properties;
    }

    @Override
    public Map<String, Object> getProMap(Map<String, Object> map) {
        return null;
    }
}
