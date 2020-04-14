package com.hutool.demo.girl.vo;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author ：bohan.zhou@ucarinc.com
 * @date ：Created in 2020/4/8 17:53
 * @description：
 * @version: $
 */
public class GirlVO {
    @NotBlank
    private String name;
    @NotEmpty
    private String age;
    @NotNull
    private String tel;
    @NotNull
    private Integer sex;
}
