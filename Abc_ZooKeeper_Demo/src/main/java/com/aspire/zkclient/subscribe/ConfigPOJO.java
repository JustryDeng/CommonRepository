package com.aspire.zkclient.subscribe;

import java.io.Serializable;

/**
 * config节点数据 模型
 *
 * 注:由于涉及到这个模型的ZkClient不需要使用SerializableSerializer，而是使用BytesPushThroughSerializer，
 *    所以此模型也可以不实现Serializable接口
 *
 * @author JustryDeng
 * @date 2018/11/28 17:52
 */
public class ConfigPOJO implements Serializable {

    /** 配置姓名 */
    private String nameConfig;

    /** 配置年龄 */
    private int ageConfig;

    /** 配置性别 */
    private String genderConfig;

    /** 配置座右铭 */
    private String mottoConfig;

    public String getNameConfig() {
        return nameConfig;
    }

    public void setNameConfig(String nameConfig) {
        this.nameConfig = nameConfig;
    }

    public int getAgeConfig() {
        return ageConfig;
    }

    public void setAgeConfig(int ageConfig) {
        this.ageConfig = ageConfig;
    }

    public String getGenderConfig() {
        return genderConfig;
    }

    public void setGenderConfig(String genderConfig) {
        this.genderConfig = genderConfig;
    }

    public String getMottoConfig() {
        return mottoConfig;
    }

    public void setMottoConfig(String mottoConfig) {
        this.mottoConfig = mottoConfig;
    }

    @Override
    public String toString() {
        return "ConfigPOJO{nameConfig='" + nameConfig + "', ageConfig=" + ageConfig
                + "', genderConfig='" + genderConfig + ", mottoConfig='" + mottoConfig + "'}";
    }
}
