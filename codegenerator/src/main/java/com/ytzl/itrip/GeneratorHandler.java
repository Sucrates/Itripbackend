package com.ytzl.itrip;

import com.ytzl.itrip.bean.Table;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Su_crates on 2018/4/19.
 */
public class GeneratorHandler {

    private static String templatePath = "codegenerator\\src\\main\\resources";

    private static String outputPath = "codegenerator\\src\\main\\resources\\output";

    private static String packageName = "com.ytzl.itrip";


    private static void execute(Table table, String templateName, String outputName) throws TemplateException {
        Configuration configuration = new Configuration();

        try {
            configuration.setDirectoryForTemplateLoading(new File(templatePath));

            Map<String, Object> param = new HashMap<>();

            param.put("table", table);

            param.put("packageName", packageName);

            Template template = configuration.getTemplate(templateName);
            template.process(param, new OutputStreamWriter(new FileOutputStream(outputPath + "\\" + outputName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  static void executeModel(Table table) throws TemplateException {
        execute(table,"model.ftl","model\\"+table.getClassName()+".java");
    }

    public  static void executeMapper(Table table) throws TemplateException {
        execute(table,"mapper-interface.ftl","mapper-interface\\"+table.getClassName()+"Mapper.java");
    }
    public  static void executeMapperXml(Table table) throws TemplateException {
        execute(table,"mapper-xml.ftl","mapper-xml\\"+table.getClassName()+"Mapper.xml");
    }

    public  static void executeService(Table table) throws TemplateException {
        execute(table,"service-interface.ftl","service-interface\\"+table.getClassName()+"Service.java");
    }
    public  static void executeServiceImpl(Table table) throws TemplateException {
        execute(table, "service-impl.ftl","service-impl\\"+table.getClassName()+"ServiceImpl.java");
    }

}
