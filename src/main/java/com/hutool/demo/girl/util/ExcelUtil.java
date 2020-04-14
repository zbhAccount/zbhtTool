package com.hutool.demo.girl.util;

import cn.hutool.core.util.StrUtil;
import com.hutool.demo.exception.FileTypeException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author ：bohan.zhou@ucarinc.com
 * @date ：Created in 2020/4/9 16:10
 * @description：
 * @version: $
 */
public class ExcelUtil {
    /**
     * 初始化构建XSSFWorkbook
     *
     * @param tempFileName
     * @return
     */
    public static Workbook buildWorkbook(String tempFileName) {
        Workbook workbook = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource(tempFileName);
            InputStream inputStream = classPathResource.getInputStream();
            String suffixStr = StrUtil.sub(tempFileName, tempFileName.lastIndexOf("."), tempFileName.length() + 1);
            if (".xls".equalsIgnoreCase(suffixStr)) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (".xlsx".equalsIgnoreCase(suffixStr)) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                throw new FileTypeException(HttpStatus.valueOf(509), "模板文件不是excel文件！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * 设置边框
     *
     * @param cellStyle
     * @param borderStyle
     * @param color
     */
    public static void setBorder(CellStyle cellStyle, BorderStyle borderStyle, short color) {
        cellStyle.setBorderTop(borderStyle);
        cellStyle.setTopBorderColor(color);
        cellStyle.setBorderBottom(borderStyle);
        cellStyle.setBottomBorderColor(color);
        cellStyle.setBorderLeft(borderStyle);
        cellStyle.setLeftBorderColor(color);
        cellStyle.setBorderRight(borderStyle);
        cellStyle.setRightBorderColor(color);
        cellStyle.setBorderTop(borderStyle);
        cellStyle.setTopBorderColor(color);
    }

    /**
     * 填充颜色
     *
     * @param cellStyle
     * @param color
     * @param fillPatternType
     */
    public static void fillColor(CellStyle cellStyle, short color, FillPatternType fillPatternType) {
        // 前景色
        cellStyle.setFillForegroundColor(color);
        cellStyle.setFillPattern(fillPatternType);
    }

    /**
     * 设置对齐方式
     *
     * @param cellStyle
     * @param halign
     * @param valign
     */
    public static void setAlignment(CellStyle cellStyle, HorizontalAlignment halign, VerticalAlignment valign) {
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
    }

    /**
     * 初始化cell格式
     *
     * @param wb
     * @param map
     * @return
     */
    public static CellStyle getCellStyle(Workbook wb, Map<String, Object> map) {
        CellStyle cellStyle = wb.createCellStyle();
        setBorder(cellStyle, (BorderStyle) map.get("borderStyle"), (Short) map.get("indexedColors"));
        setAlignment(cellStyle, (HorizontalAlignment) map.get("horizontalAlignment"), (VerticalAlignment) map.get("verticalAlignment"));
        return cellStyle;
    }

    /**
     * 合并细胞
     *
     * @param sheet
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    public static void mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    /**
     * 设置请求头， 文件名， 写出excel
     *
     * @param response
     * @param fileName
     * @throws IOException
     */
    public static void createExcel(HttpServletRequest request, HttpServletResponse response, String fileName, Workbook workbook) throws IOException {
        //获得浏览器信息并转换为大写
        response.setContentType("application/vnd.ms-excel");
        String agent = request.getHeader("User-Agent").toUpperCase();
        //IE浏览器和Edge浏览器
        if (agent.indexOf("MSIE") > 0 || agent.indexOf("EDGE") > 0 || agent.indexOf("RV:11") > 0) {
            //处理空格转为加号的问题
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20"));
        } else if (agent.contains("MOZILLA")) {
            // 火狐、谷歌浏览器
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        } else {
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        }

        OutputStream output = response.getOutputStream();
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
        workbook.write(bufferedOutPut);
        bufferedOutPut.flush();
        bufferedOutPut.close();
        output.close();
        workbook.close();
    }

}
