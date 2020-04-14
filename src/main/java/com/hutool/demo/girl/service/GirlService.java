package com.hutool.demo.girl.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author ：bohan.zhou@ucarinc.com
 * @date ：Created in 2020/4/9 17:00
 * @description：
 * @version: $
 */
public interface GirlService {

    Workbook export(String tempFileName);
}
