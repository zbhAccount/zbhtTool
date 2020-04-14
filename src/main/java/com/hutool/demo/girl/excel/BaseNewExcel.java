package com.hutool.demo.girl.excel;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.hutool.demo.girl.util.ExcelUtil;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：bohan.zhou@ucarinc.com
 * @date ：Created in 2020/4/10 15:33
 * @description：
 * @version: $
 */
@Data
public abstract class BaseNewExcel {
    /**
     * 表头样式
     */
    CellStyle headStyle;
    /**
     * 内容样式
     */
    CellStyle bodyStyle;
    /**
     * 列宽
     */
    int width = 20 * 256;
    /**
     * 行高
     */
    float height = 20.0f;
    /**
     * 表
     */
    Sheet sheet;
    /**
     * workbook
     */
    Workbook workbook;
    /**
     * 创建table
     */
    Table<String, String, String> table = HashBasedTable.create();

    /**
     * 填充数据
     */
    public abstract void fillWorkbookDataByTemplate();

    void setDropDownAndHidden(String[] formulaString, Map<String, Integer> map) {
        // 创建sheet，写入枚举项
        Sheet hideSheet = workbook.createSheet("hiddenSheet");
        for (int i = 0; i < formulaString.length; i++) {
            hideSheet.createRow(i).createCell(0).setCellValue(formulaString[i]);
        }
        // 创建名称，可被其他单元格引用
        Name category1Name = workbook.createName();
        category1Name.setNameName("hidden");
        // 设置名称引用的公式
        // 使用像'A1：B1'这样的相对值会导致在Microsoft Excel中使用工作簿时名称所指向的单元格的意外移动，
        // 通常使用绝对引用，例如'$A$1:$B$1'可以避免这种情况。
        // 参考： http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Name.html
        category1Name.setRefersToFormula("hiddenSheet!" + "$A$1:$A$" + formulaString.length);
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createFormulaListConstraint("hidden");
        CellRangeAddressList addressList = new CellRangeAddressList(
                map.get(RowCol.FIRSTROW.getValue()),
                map.get(RowCol.LASTROW.getValue()),
                map.get(RowCol.FIRSTCOL.getValue()),
                map.get(RowCol.LASTCOL.getValue())
        );
        DataValidation dataValidation = helper.createValidation(constraint, addressList);
        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            // 数据校验
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        // 作用在目标sheet上
        sheet.addValidationData(dataValidation);
        // 设置hiddenSheet隐藏
        workbook.setSheetHidden(workbook.getNumberOfSheets()-1, true);
    }

    /**
     * 设置获取单元格属性
     *
     * @return
     */
    public abstract Map<String, Object> getProMap();

    /**
     * 设置获取单元格属性
     *
     * @return
     */
    public abstract Map<String, Object> getProMap(Map<String, Object> map);

    enum RowCol {
        FIRSTROW("firstRow"),
        LASTROW("lastRow"),
        FIRSTCOL("firstRow"),
        LASTCOL("LastCol");
        private String value;

        RowCol(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
