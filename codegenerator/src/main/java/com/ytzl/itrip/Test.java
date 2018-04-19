package com.ytzl.itrip;

import com.ytzl.itrip.bean.Table;
import freemarker.template.TemplateException;

import java.util.List;

/**
 * Created by Su_crates on 2018/4/19.
 */
public class Test {
    public static void main(String[] args) throws TemplateException {
        List<Table> tableList = TableHandler.getTables();
        for (Table table : tableList) {
            System.out.println("--------11111-------");
            GeneratorHandler.executeModel(table);
            GeneratorHandler.executeMapper(table);
            GeneratorHandler.executeMapperXml(table);
            GeneratorHandler.executeService(table);
            GeneratorHandler.executeServiceImpl(table);
        }
    }
}
