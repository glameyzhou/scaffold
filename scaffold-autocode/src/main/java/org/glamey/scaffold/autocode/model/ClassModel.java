package org.glamey.scaffold.autocode.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * JAVA对象模型
 *
 * @author zhouyang.zhou
 */
public class ClassModel implements java.io.Serializable {
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 库名称
     */
    private String schema;
    /**
     * 首字母小写类名称，
     */
    private String lname;
    /**
     * 首字母大写类名称，
     */
    private String uname;
    /**
     * 类描述
     */
    private String desc;

    /**
     * package的根地址，例如 org.glamey,后续会自动补充为org.glamey.model,org.glamey.dao等
     */
    private String basePackage;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}