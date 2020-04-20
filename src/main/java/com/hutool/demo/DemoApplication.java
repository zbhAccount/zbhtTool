package com.hutool.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 功能描述: 入口<br>
 * 〈默认访问地址：http://localhost:8082/huTool/swagger-ui.html〉
 *
 * @author: bohan.zhou@ucarinc.com
 * @date: 2020/4/20 17:45
 */
@SpringBootApplication
@EnableSwagger2
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
