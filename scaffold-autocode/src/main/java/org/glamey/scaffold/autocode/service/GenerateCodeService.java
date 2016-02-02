package org.glamey.scaffold.autocode.service;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.glamey.scaffold.autocode.model.ClassModel;
import org.glamey.scaffold.autocode.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author zhouyang.zhou
 */
public class GenerateCodeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateCodeService.class);
    private static Map<String, String> columnMap = Maps.newHashMap();
    private static Map<String, String> templateNameMap = Maps.newHashMap();

    static {
        columnMap.put("bigint", "Long");
        columnMap.put("boolean", "Boolean");
        columnMap.put("char", "String");
        columnMap.put("varchar", "String");
        columnMap.put("text", "String");
        columnMap.put("datetime", "Date");
        columnMap.put("timestamp", "Date");
        columnMap.put("date", "Date");
        columnMap.put("bigint", "Long");
        columnMap.put("double", "Double");
        columnMap.put("int", "Integer");
        columnMap.put("smallint", "Integer");
        columnMap.put("tinyint", "Integer");
        columnMap.put("decimal", "Integer");

        templateNameMap.put("Model.java", "Model.ftl");
        templateNameMap.put("Mapper.xml", "Mapper.ftl");
        templateNameMap.put("Dao.java", "Dao.ftl");
        templateNameMap.put("Service.java", "Service.ftl");
//        templateNameMap.put("ServiceImpl.java", "ServiceImpl.ftl");
    }

    private Connection connection;

    public GenerateCodeService(String driver, String url, String userName, String password) {
        try {
            if (Strings.isNullOrEmpty(driver))
                driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            LOGGER.error("Connection the db error,driver={},url={},userName={},password={}",
                    driver, url, userName, password, e);
        }
    }

    private List<PropertyModel> getPropertyModel(String schema, String tableName) throws Exception {
        List<PropertyModel> list = Lists.newArrayList();
        ResultSet rs = connection.createStatement().executeQuery("SELECT COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = '" + schema + "' AND table_name = '" + tableName + "'");
        while (rs.next()) {
            PropertyModel model = new PropertyModel();
            String columnName = rs.getString(1);
            model.setColumnName(columnName);
            model.setLname(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName));
            model.setUname(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, columnName));
            model.setType(columnMap.get(rs.getString(2)));
            model.setDesc(rs.getString(3));

            list.add(model);
        }
        return list;
    }

    private ClassModel getClassModel(String basePackage, String schema, String tableName) throws Exception {
        ResultSet rs = connection.createStatement().executeQuery("SELECT TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES  WHERE table_schema = '" + schema + "' AND table_name = '" + tableName + "'");
        if (!rs.next()) {
            return new ClassModel();
        }
        ClassModel model = new ClassModel();
        model.setTableName(tableName);
        model.setSchema(schema);
        model.setLname(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName));
        model.setUname(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName));
        model.setDesc(rs.getString(1));
        model.setBasePackage(basePackage);
        return model;
    }

    public void generateTemplateContent(String templateName, Map<String, Object> templateObject, String fileName) throws IOException, TemplateException {
        if (templateObject == null || templateObject.size() == 0)
            return;
        // 读取模板并设置模板内容
        Configuration configuration = new Configuration();
        configuration.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/template/autocode"));
        configuration.setEncoding(Locale.CHINA, "UTF-8");
        Template template = configuration.getTemplate(templateName);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
        template.process(templateObject, writer);
    }

    private void generateCode(String basePackage, String filePath, String schema, String tableName) throws Exception {
        ClassModel classModel = getClassModel(basePackage, schema, tableName);
        List<PropertyModel> propertyModelList = getPropertyModel(schema, tableName);

        Map<String, Object> templateObject = Maps.newConcurrentMap();
        templateObject.put("classModel", classModel);
        templateObject.put("propertyModelList", propertyModelList);


        for (Map.Entry<String, String> entry : templateNameMap.entrySet()) {
            String fileName = getFilePath(filePath, entry.getKey()) + classModel.getUname() + entry.getKey();
            generateTemplateContent(entry.getValue(), templateObject, fileName);
        }
    }

    private String getFilePath(String filePath, String codeCategory) {
        String realPath = StringUtils.endsWith(filePath, "/") ? filePath : filePath + "/";
        //需要根据代码类型添加目录
        if (StringUtils.equals(codeCategory, "Model.java")) {
            realPath += "model/";
        } else if (StringUtils.equals(codeCategory, "Mapper.xml")) {
            realPath += "mapper/";
        } else if (StringUtils.equals(codeCategory, "Dao.java")) {
            realPath += "dao/";
        } else if (StringUtils.equals(codeCategory, "Service.java")) {
            realPath += "service/";
        } /*else if (StringUtils.equals(codeCategory, "ServiceImpl.java")) {
            realPath += "service/impl/";
        }*/
        File dir = new File(realPath);
        if (!dir.exists())
            dir.mkdirs();
        return realPath;
    }


    /**
     * 代码生成入口
     *
     * @param basePackage 代码包的根目录
     * @param schema      数据库的schema
     * @param tableNames  要生成的表名称
     * @param codeSaveDir 生成的代码存放位置
     */

    public void generateCode(String basePackage, String schema, String[] tableNames, String codeSaveDir) {
        try {
            for (String tableName : tableNames) {
                generateCode(basePackage, codeSaveDir, schema, tableName);
                LOGGER.info("{}.{} is OK.", schema, tableName);
            }
        } catch (Exception e) {
            LOGGER.error("generateCode error", e);
        }
    }
}
