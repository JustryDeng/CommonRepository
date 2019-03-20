package com.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 个人信息 模型
 *
 * @author JustryDeng
 * @date 2019/3/19 13:58
 */
@Data
public class PersonInfoVO {

    private String name;

    private Integer age;

    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date borthDate;
}
