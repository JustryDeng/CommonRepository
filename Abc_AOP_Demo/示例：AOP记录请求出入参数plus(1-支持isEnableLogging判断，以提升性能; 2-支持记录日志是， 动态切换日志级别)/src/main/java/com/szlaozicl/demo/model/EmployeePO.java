package com.szlaozicl.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Employee员工实体类模型
 * 
 * @author JustryDeng
 * @date 2019/11/26 10:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePO {

	/** id */
	private Integer id;

	/** 名字 */
	private String name;

	/** 年龄 */
	private Integer age;

	/** 性别 */
	private String gender;

	/** 座右铭 */
	private String motto;

	/** 出生日期 */
	private String birthday;

	/** 爱好 */
	private String hobby;

}
