package com.hutool.demo.girl.controller;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Console;
import com.hutool.demo.girl.service.GirlService;
import com.hutool.demo.girl.util.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author ：bohan.zhou@ucarinc.com
 * @date ：Created in 2020/4/9 14:56
 * @description：
 * @version: $
 */
@RestController
@RequestMapping("/girl")
@Api(description = "girl导出控制器")
public class GirlExportController {
    @Autowired
    GirlService girlService;

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "测试export方法", notes = "返回null")
    public String say(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tempFileName = "templates" + File.separator +"hour_template.xlsx";
        // 逻辑处理
        Workbook workbook = girlService.export(tempFileName);
        String fileName = "2020-01-01 2020-01-02.xlsx";
        ExcelUtil.createExcel(request, response, fileName, workbook);
        return "export success";
    }

}
