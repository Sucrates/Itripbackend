package com.ytzl.itrip.bean;

/**
 * Created by Su_crates on 2018/4/19.
 */
public class Column {

    private String columnName;

    private String columnType;

    private String firstUpperCaseColumnName;

    private String columnRemark;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getFirstUpperCaseColumnName() {
        return firstUpperCaseColumnName;
    }

    public void setFirstUpperCaseColumnName(String firstUpperCaseColumnName) {
        this.firstUpperCaseColumnName = firstUpperCaseColumnName;
    }

    public String getColumnRemark() {
        return columnRemark;
    }

    public void setColumnRemark(String columnRemark) {
        this.columnRemark = columnRemark;
    }
}
