package org.glamey.scaffold.autocode.service;

import org.junit.Test;

/**
 * @author by zhouyang.zhou.
 */
public class GenerateCodeServiceTest {

    GenerateCodeService service;

    final String schema = "vpms";
    final String driver = "com.mysql.jdbc.Driver";
    final String userName = "root";
    final String password = "root";
    final String url = "jdbc:mysql://127.0.0.1:3306?zeroDateTimeBehavior=convertToNull&amp;autoReconnect=true&allowMultiQueries=true";


    @Test
    public void testGenerateCode() throws Exception {
        service = new GenerateCodeService(driver, url, userName, password);
        service.generateCode(
                "cn.valuelink.vpms",
                schema,
                new String[]{
                        "search_content", "search_top"

                },
                "/Users/zhouyangzhou/work/temp/vpms"
        );
    }
}