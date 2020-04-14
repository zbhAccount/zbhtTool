package com.hutool.demo.girl.service.impl;

import com.hutool.demo.girl.excel.BaseNewExcel;
import com.hutool.demo.girl.excel.NewExcel;
import com.hutool.demo.girl.service.GirlService;
import com.hutool.demo.girl.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：bohan.zhou@ucarinc.com
 * @date ：Created in 2020/4/9 17:01
 * @description：
 * @version: $
 */
@Service
public class GirlServiceImpl implements GirlService {

    @Autowired
    NewExcel newExcel;

    @Override
    public Workbook export(String tempFileName) {
        Workbook workbook = ExcelUtil.buildWorkbook(tempFileName);
        // 数据填充
        newExcel.setWorkbook(workbook);
        newExcel.fillWorkbookDataByTemplate();
        return workbook;
    }
}
