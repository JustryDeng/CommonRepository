package cn.afterturn.easypoi.test.entity.groupname;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.test.entity.MsgClientGroup;

/**
 * Created by JueYue on 2017/10/2.
 */
public class GroupNameEntity implements java.io.Serializable {
    /**
     * id
     */
    private java.lang.String id;
    // 电话号码(主键)
    @Excel(name = "电话号码", groupName = "联系方式", orderNum = "1")
    private String clientPhone = null;
    // 客户姓名
    @Excel(name = "姓名")
    private String clientName = null;
    // 备注
    @Excel(name = "备注")
    private String remark = null;
    // 生日
    @Excel(name = "出生日期", format = "yyyy-MM-dd", width = 20, groupName = "时间", orderNum = "2")
    private Date birthday = null;
    // 创建人
    @Excel(name = "创建时间", groupName = "时间", orderNum = "3")
    private String createBy = null;

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 生日
     */
    public java.util.Date getBirthday() {
        return this.birthday;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 客户姓名
     */
    public java.lang.String getClientName() {
        return this.clientName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 电话号码
     */
    public java.lang.String getClientPhone() {
        return this.clientPhone;
    }

    public String getCreateBy() {
        return createBy;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String id
     */

    public java.lang.String getId() {
        return this.id;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 备注
     */
    public java.lang.String getRemark() {
        return this.remark;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 生日
     */
    public void setBirthday(java.util.Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 客户姓名
     */
    public void setClientName(java.lang.String clientName) {
        this.clientName = clientName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 电话号码
     */
    public void setClientPhone(java.lang.String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 备注
     */
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }
}