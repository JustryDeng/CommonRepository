package com.aspire.model;

import java.util.List;

/**
 * 采访结果 模型
 *
 * @author JustryDeng
 * @date 2018/12/5 17:01
 */
public class ResultPOJO {

    /** 标题 */
    private String title;

    /** 日期 */
    private String date;

    /** 采访人 */
    private String interviewer;

    /** 信息集合 */
    private List<HandsomeBoyPOJO> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(String interviewer) {
        this.interviewer = interviewer;
    }

    public List<HandsomeBoyPOJO> getList() {
        return list;
    }

    public void setList(List<HandsomeBoyPOJO> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ResultPOJO{title='" + title + "', date='" + date + "', interviewer='" + interviewer
                + "', list=" + list + '}';
    }
}