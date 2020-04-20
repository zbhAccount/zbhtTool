package com.hutool.demo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.*;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.extra.emoji.EmojiUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import cn.hutool.poi.excel.style.StyleUtil;
import cn.hutool.script.ScriptUtil;
import com.hutool.demo.girl.model.Girl;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        int a = 1;
        String aStr = Convert.toStr(a);
        System.out.println(aStr);

        long[] b = {1, 2, 3, 4, 5};
        String bStr = Convert.toStr(b);
        System.out.println(bStr);
    }

    @Test
    void test1() {
        String[] b = {"1", "2", "3", "4"};
        //结果为Integer数组
        Integer[] intArray = Convert.toIntArray(b);

        long[] c = {1, 2, 3, 4, 5};
        //结果为Integer数组
        Integer[] intArray2 = Convert.toIntArray(c);

        List<String> d = new ArrayList<>();
        d.add("1");
        d.add("2");
        Integer[] intArray3 = Convert.toIntArray(d);
    }

    @Test
    void test3() {
        String a = "2017-05-06 11:11:62";
        Date value = Convert.toDate(a);
        System.out.println(value);
    }

    @Test
    void test4() {
        Object[] a = {"a", "你", "好", "", 1};
        Object b = "2";
        List<String> list = Convert.convert(new TypeReference<List<String>>() {
        }, a);
        List<String> list2 = Convert.convert(new TypeReference<List<String>>() {
        }, b);
        Set<String> list3 = Convert.convert(new TypeReference<HashSet<String>>() {
        }, a);
        System.out.println();
    }

    @Test
    void test5() {
        //去包装
        Class<?> wrapClass = Integer.class;
        //结果为：int.class
        Class<?> unWraped = Convert.unWrap(wrapClass);
        //包装
        Class<?> primitiveClass = long.class;
        //结果为：Long.class
        Class<?> wraped = Convert.wrap(primitiveClass);

    }

    @Test
    void test6() {
        // 上周
        Date date1 = DateUtil.lastWeek();
        // 周一
        Date date2 = DateUtil.beginOfWeek(DateUtil.date());
    }

    @Test
    void test7() {
        FileUtil.mkdir("D:\\testFolder");
        File file = FileUtil.touch("/testFolder/a.txt");
        System.out.println(file.getPath());
    }

    @Test
    void test8() throws IOException {
        ClassPathResource resource = new ClassPathResource("config.properties");
        Properties properties = new Properties();
        properties.load(resource.getStream());

        Console.log("Properties: {}", properties);
    }

    @Test
    void test9() {
        String s = StrUtil.str("eqw");
        byte[] b = StrUtil.bytes("中文");
        String template = "{}爱{}，就像老鼠爱大米，{}";
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        String[] strings = {"1", "2"};
        String str = StrUtil.format(template, strings, list, b);
    }

    @Test
    void test10() {
        String s = EscapeUtil.escape("中文");
        String ss = EscapeUtil.unescape(s);
    }

    @Test
    void test11() {
        //参数1为终端ID
        //参数2为数据中心ID
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long id = snowflake.nextId();
    }

    @Test
    void test12() {
        String ID_18 = "360430199802080931";
        String ID_15 = "150102880730303";
        String province = IdcardUtil.getProvinceByIdCard(ID_18);
    }

    @Test
    void test13() {
        HashMap<String, Object> map = CollUtil.newHashMap();
        map.put("name", "Joe");
        map.put("age", "1");
        map.put("tel", "2");
        Girl person = BeanUtil.mapToBeanIgnoreCase(map, Girl.class, false);
    }

    @Test
    void testDB() {
        try {
            Db.use().insertForGeneratedKey(
                    Entity.create("girl")
                            .set("name", "testUser")
                            .set("age", "21")
                            .set("tel", "111220")
            );
        } catch (SQLException e) {
            Console.log("出错啦");
        }
    }

    @Test
    void testEmoji() {
        String emoji = EmojiUtil.toUnicode(":smile:");//😄
        System.out.println(emoji);
    }

    @Test
    void testScript() {
        ScriptUtil.eval("print('Script test!');");
    }

    @Test
    void testExcel() {
        List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
        List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");
        List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);
        ExcelWriter writer = ExcelUtil.getWriter("d:/writeTest.xlsx");
        writer.merge(0, 0, 0, 3, "测试1", true);
//        writer.merge(0,0,3,3,"测试2",true);
//        writer.merge(row1.size() - 1, "测试标题");
//        StyleSet style = writer.getStyleSet();
//        style.setBackgroundColor(IndexedColors.RED, false);
//        writer.setStyleSet(style);
        writer.write(rows, true);
        CellStyle orCreateRowStyle = writer.getOrCreateCellStyle(2, 2);
        StyleUtil.setColor(orCreateRowStyle, IndexedColors.RED.getIndex(), FillPatternType.SOLID_FOREGROUND);
        writer.close();
    }

    @Test
    public void writeBeanTest2() {
        Girl order1 = new Girl();
        order1.setAge("1");
        order1.setName("123");
        order1.setTel("body1");

        Girl order2 = new Girl();
        order2.setAge("2");
        order2.setName("456");
        order2.setTel("body2");

        List<Girl> rows = CollUtil.newArrayList(order1, order2);
        // 通过工具类创建writer
        String file = "d:/writeBeanTest2.xlsx";
        FileUtil.del(file);
        ExcelWriter writer = ExcelUtil.getWriter(file);
        // 自定义标题
        writer.addHeaderAlias("id", "编号");
        writer.addHeaderAlias("num", "序号");
        writer.addHeaderAlias("body", "内容");
        // 一次性写出内容，使用默认样式
        writer.write(rows, true);
        CellStyle orCreateRowStyle = writer.getOrCreateCellStyle(0, 1);
        StyleUtil.setColor(orCreateRowStyle, IndexedColors.RED.getIndex(), FillPatternType.SOLID_FOREGROUND);
        // 关闭writer，释放内存
        writer.close();
    }

    // poi导出文件
    @Test
    public void test14() throws IOException {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");
        // 列宽
        sheet.setColumnWidth(0, 20 * 256);

        Row row = sheet.createRow(0);
        // 行高
        row.setHeightInPoints(50);

        row.createCell(0).setCellValue(1.1);

        CreationHelper createHelper = wb.getCreationHelper();
        CellStyle cellStyle = getCellStyle(wb);
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
        fillColor(cellStyle, IndexedColors.RED.getIndex(), FillPatternType.SOLID_FOREGROUND);
        Cell cell = row.createCell(1);
        cell.setCellValue(new Date());
        cell.setCellStyle(cellStyle);

        Cell cell2 = row.createCell(2);
        CellStyle cellStyle2 = getCellStyle(wb);
        cell2.setCellValue(Calendar.getInstance());
        cell2.setCellStyle(cellStyle2);

        Cell cell3 = row.createCell(3);
        cell3.setCellValue("a string");
        CellStyle cellStyle3 = getCellStyle(wb);

        row.createCell(4).setCellValue(true);
        mergeCell(sheet, 0, 0, 3, 4);
        cell3.setCellStyle(cellStyle3);

        try (OutputStream fileOut = new FileOutputStream("workbook.xls")) {
            wb.write(fileOut);
        }
    }

    // 设置边框
    private void setBorder(CellStyle cellStyle, BorderStyle borderStyle, short color) {
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

    // 填充颜色
    private void fillColor(CellStyle cellStyle, short color, FillPatternType fillPatternType) {
        // 前景色
        cellStyle.setFillForegroundColor(color);
        cellStyle.setFillPattern(fillPatternType);
    }

    // 合并细胞
    public void mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(
                firstRow, //first row (0-based)
                lastRow, //last row  (0-based)
                firstCol, //first column (0-based)
                lastCol  //last column  (0-based)
        ));
    }

    // 设置对齐方式
    public void setAlignment(CellStyle cellStyle, HorizontalAlignment halign, VerticalAlignment valign) {
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
    }

    @Test
    public void import1() throws IOException {
        ExcelReader reader = ExcelUtil.getReader("C:\\Users\\Administrator\\Documents\\Tencent Files\\1562998176\\FileRecv\\DBSearch_2020-4-10_16-59-43.xlsx");
        List<Map<String, Object>> readAll = reader.readAll();
        String str1 = "0(New-Object Net.WebClient).DownloadFile('http://ucarresource.10101111.com/carresources/resource/";
        String str2 = "','D:/contract/房产证";
        String str3 = "')";

        FileWriter writer = new FileWriter("test3.properties");

        for (Map<String, Object> stringObjectMap : readAll) {
            String property_certificate = (String) stringObjectMap.get("property_certificate");

            if (!"NULL".equalsIgnoreCase(property_certificate)) {
                String suffixStr = StrUtil.sub(property_certificate, property_certificate.lastIndexOf("."), property_certificate.length() + 1);
                String str = str1 + property_certificate + str2 +
                        stringObjectMap.get("code") +  suffixStr + str3 + "\n";
                writer.append(str);
            }
        }
    }

    // 初始化cell
    private CellStyle getCellStyle(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        setBorder(cellStyle, BorderStyle.THIN, IndexedColors.BLACK.getIndex());
        setAlignment(cellStyle, HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM);
        return cellStyle;
    }

    /**
     * <p>初始化构建excel</p>
     *
     * @param tempFileName
     * @return
     */
    private XSSFWorkbook builderWorkbook(String tempFileName) {
        XSSFWorkbook hourWorkbook = null;
        try {
            XSSFWorkbook hourTempWorkbook = new XSSFWorkbook(new FileInputStream(new File(tempFileName)));
            hourWorkbook = hourTempWorkbook;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hourWorkbook;
    }

    /**
     * 设置请求头， 文件名， 写出excel
     *
     * @param response
     * @param fileName
     * @throws IOException
     */
    private void createExcel(HttpServletResponse response, String fileName, XSSFWorkbook workbook) throws IOException {
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        OutputStream output = response.getOutputStream();
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
        bufferedOutPut.flush();
        workbook.write(bufferedOutPut);
        output.close();
        bufferedOutPut.close();
    }

    @Test
    public void test15(){
        List<String> strings = new ArrayList<>();
        strings.add("1");
        unsafeadd(strings, new Integer(0));
        // 编译报错
        // unsafeadd1(strings, new Integer(0));
        unsafeadd2(strings, new Integer(0));
        String s = strings.get(0);
        // 运行报错
        String s2 = strings.get(2);
    }

    /**
     * ?无限制通配符可以接受任意类型的对象 但是 不能不能添加任何元素（除null外）
     * @param list
     * @param o
     */
    private void unsafeadd(List<?> list, Object o){
        // list.add(o);
        list.add(null);
        System.out.println(list.get(0));
    }

    /**
     * list<String> 是原生态类型List的子类  不是List<Object>的子类 故编译报错
     * @param list
     * @param o
     */
    private void unsafeadd1(List<Object> list, Object o){
        list.add(o);
    }

    /**
     * list是原生态类型  脱离泛型系统  运行时报错
     * @param list
     * @param o
     */
    private void unsafeadd2(List list, Object o){
        list.add(o);
    }
}
