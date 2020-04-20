package com.hutool.demo.girl.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：bohan.zhou@ucarinc.com
 * @date ：Created in 2020/3/31 10:28
 * @description：
 * @version: $
 */
@Configuration
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Girl {
    @Value("${girl.name}")
    private String name;
    @Value("${girl.age}")
    private String age;
    @Value("${girl.tel}")
    private String tel;
}
