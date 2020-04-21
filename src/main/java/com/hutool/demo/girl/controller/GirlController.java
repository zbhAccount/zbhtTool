package com.hutool.demo.girl.controller;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Console;
import com.hutool.demo.girl.model.Girl;
import com.hutool.demo.girl.model.Stack;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * @author ：bohan.zhou@ucarinc.com
 * @date ：Created in 2020/3/31 10:26
 * @description：
 * @version: $
 */
@RestController
@RequestMapping("/girl")
@Api(description = "girl控制器")
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

    @RequestMapping(value = "/pop", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "测试泛型", notes = "返回null")
    public String pop() {
        Stack<Girl> stack = new Stack<>();
        Girl girl = Girl.builder().age("12").name("意愿").tel("15046238596").build();
        stack.push(girl);
        Console.log(stack.pop().toString());
        Girl girl1 = Girl.builder().age("12").name("未来").tel("15078945622").build();
        Girl girl2 = Girl.builder().age("13").name("发财").tel("15078594163").build();
        List<Girl> list = new ArrayList<>();
        list.add(girl1);
        list.add(girl2);
        stack.pushAll(list);
        Console.log("popAll前：{}", stack.toString());
        List<Girl> girls = new ArrayList<>();
        stack.popAll(girls);
        Console.log("popAll后：{}", stack.toString());
        return null;
    }

}
