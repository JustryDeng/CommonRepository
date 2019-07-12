package com.aspire.ssm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户模型
 *
 * @author JustryDeng
 * @date 2019/6/21 16:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	/** 用户id */
	private Integer id;

	/** 名字 */
	private String name;

	/** 年龄 */
	private Integer age;

	/** 性别 */
	private String gender;

	/** 座右铭 */
	private String motto;

}
