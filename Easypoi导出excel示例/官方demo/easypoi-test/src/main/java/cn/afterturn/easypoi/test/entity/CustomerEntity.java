package cn.afterturn.easypoi.test.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 客户信息
 * @author JueYue
 *   2015-04-03 22:16:53
 * @version V1.0
 * 
 */
public class CustomerEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    /** id */
    private java.lang.String  id;
    /** 姓名 */
    @Excel(name = "姓名")
    private java.lang.String  name;
    /** 性别 */
    @Excel(name = "性别")
    private java.lang.String  sex;
    /** 资金 */
    @Excel(name = "资金")
    private java.lang.String  money;
    /** 身份证号 */
    @Excel(name = "身份证号")
    private java.lang.String  card;
    /** 住址 */
    @Excel(name = "住址")
    private java.lang.String  address;
    /** 手机 */
    @Excel(name = "手机")
    private java.lang.String  phone;
    /** QQ号 */
    @Excel(name = "QQ号")
    private java.lang.String  qq;
    /** 开户时间 */
    @Excel(name = "开户时间")
    private java.util.Date    openTime;
    /** 交易账户 */
    @Excel(name = "交易账户")
    private java.lang.String  tradeNum;
    /** 登录密码 */
    @Excel(name = "登录密码")
    private java.lang.String  loginPassword;
    /** 交易密码 */
    @Excel(name = "交易密码")
    private java.lang.String  tradePassword;
    /** 备注 */
    private java.lang.String  memo;
    /** 客户状态 */
    @Excel(name = "客户状态")
    private java.lang.String  status;
    /** 客户类型 */
    @Excel(name = "客户类型")
    private java.lang.String  type;
    /** createTime */
    private java.util.Date    createTime;
    /** createUser */
    private java.lang.String  createUser;
    /** updateTime */
    private java.util.Date    updateTime;
    /** updateUser */
    private java.lang.String  updateUser;

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String id
     */

    public java.lang.String getId() {
        return this.id;
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
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 姓名
     */
    public java.lang.String getName() {
        return this.name;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 姓名
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
     * 方法: 取得java.lang.Object
     * 
     * @return: java.lang.Object 性别
     */
    public java.lang.String getSex() {
        return this.sex;
    }

    /**
     * 方法: 设置java.lang.Object
     * 
     * @param: java.lang.Object 性别
     */
    public void setSex(java.lang.String sex) {
        this.sex = sex;
    }

    /**
     * 方法: 取得java.lang.Double
     * 
     * @return: java.lang.Double 资金
     */
    public java.lang.String getMoney() {
        return this.money;
    }

    /**
     * 方法: 设置java.lang.Double
     * 
     * @param: java.lang.Double 资金
     */
    public void setMoney(java.lang.String money) {
        this.money = money;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 身份证号
     */
    public java.lang.String getCard() {
        return this.card;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 身份证号
     */
    public void setCard(java.lang.String card) {
        this.card = card;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 住址
     */
    public java.lang.String getAddress() {
        return this.address;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 住址
     */
    public void setAddress(java.lang.String address) {
        this.address = address;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 手机
     */
    public java.lang.String getPhone() {
        return this.phone;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 手机
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String QQ号
     */
    public java.lang.String getQq() {
        return this.qq;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String QQ号
     */
    public void setQq(java.lang.String qq) {
        this.qq = qq;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 开户时间
     */
    public java.util.Date getOpenTime() {
        return this.openTime;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 开户时间
     */
    public void setOpenTime(java.util.Date openTime) {
        this.openTime = openTime;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 交易账户
     */
    public java.lang.String getTradeNum() {
        return this.tradeNum;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 交易账户
     */
    public void setTradeNum(java.lang.String tradeNum) {
        this.tradeNum = tradeNum;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 登录密码
     */
    public java.lang.String getLoginPassword() {
        return this.loginPassword;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 登录密码
     */
    public void setLoginPassword(java.lang.String loginPassword) {
        this.loginPassword = loginPassword;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 交易密码
     */
    public java.lang.String getTradePassword() {
        return this.tradePassword;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 交易密码
     */
    public void setTradePassword(java.lang.String tradePassword) {
        this.tradePassword = tradePassword;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 备注
     */
    public java.lang.String getMemo() {
        return this.memo;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 备注
     */
    public void setMemo(java.lang.String memo) {
        this.memo = memo;
    }

    /**
     * 方法: 取得java.lang.Integer
     * 
     * @return: java.lang.Integer 客户状态
     */
    public java.lang.String getStatus() {
        return this.status;
    }

    /**
     * 方法: 设置java.lang.Integer
     * 
     * @param: java.lang.Integer 客户状态
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    /**
     * 方法: 取得java.lang.Integer
     * 
     * @return: java.lang.Integer 客户类型
     */
    public java.lang.String getType() {
        return this.type;
    }

    /**
     * 方法: 设置java.lang.Integer
     * 
     * @param: java.lang.Integer 客户类型
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date createTime
     */
    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date createTime
     */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String createUser
     */
    public java.lang.String getCreateUser() {
        return this.createUser;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String createUser
     */
    public void setCreateUser(java.lang.String createUser) {
        this.createUser = createUser;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date updateTime
     */
    public java.util.Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date updateTime
     */
    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String updateUser
     */
    public java.lang.String getUpdateUser() {
        return this.updateUser;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String updateUser
     */
    public void setUpdateUser(java.lang.String updateUser) {
        this.updateUser = updateUser;
    }

}
