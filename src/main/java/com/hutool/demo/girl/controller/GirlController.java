package com.hutool.demo.girl.controller;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Console;
import com.hutool.demo.girl.model.Girl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Properties;


/**
 * @author ：bohan.zhou@ucarinc.com
 * @date ：Created in 2020/3/31 10:26
 * @description：
 * @version: $
 */
@RestController
@RequestMapping("/girl")
@Api(value = "girl控制器")
@PropertySource(value = "classpath:config.properties", encoding = "utf-8")
@Slf4j
public class GirlController {
    @Value("${name}")
    private String name;
    @Value("${age}")
    private String age;
    @Value("${tel}")
    private String tel;

    @Value("${girl.name}")
    private String name1;
    @Value("${girl.age}")
    private String age1;
    @Value("${girl.tel}")
    private String tel1;

    @Autowired
    private Girl girl;

    @RequestMapping(value = "/say", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "测试say方法", notes = "返回null")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "旧密码", required = true, dataType = "String"),
    })
    public String say(@RequestParam String userId, @RequestParam String password) throws IOException {
        System.out.println(userId);
        System.out.println(password);

        System.out.println(name);
        System.out.println(age);
        System.out.println(tel);

        System.out.println(name1);
        System.out.println(age1);
        System.out.println(tel1);

        System.out.println(girl.getName());
        System.out.println(girl.getAge());
        System.out.println(girl.getTel());

        ClassPathResource resource = new ClassPathResource("config.properties");
        Properties properties = new Properties();
        properties.load(resource.getStream());

        Console.log("Properties: {}", properties);

        return "test";
    }


}
