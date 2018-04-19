package com.ytzl.itrip.bean;

import java.util.List;

/**
 * Created by Su_crates on 2018/4/19.
 */
public class Table {

    private String tableName;

    private String className;

    private String firstLowerCaseClassName;

    private List<Column> columnList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFirstLowerCaseClassName() {
        return firstLowerCaseClassName;
    }

    public void setFirstLowerCaseClassName(String firstLowerCaseClassName) {
        this.firstLowerCaseClassName = firstLowerCaseClassName;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }
}
