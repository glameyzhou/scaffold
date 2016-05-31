package org.glamey.scaffold.component.store.qiniu;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author zhouyang.zhou.
 */
public class FileUtils {

    public static String generateFileName(String fileName) {
        Assert.notNull(fileName);
        return generateUniqeId(fileName);
    }

    private static String generateUniqeId(String fileName) {
        String date = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
        int fileHash = Math.abs(fileName.hashCode());
        String extension = FilenameUtils.getExtension(fileName);

        StringBuilder file = new StringBuilder();
        file.append(date).append("_").append(String.valueOf(fileHash));
        if (StringUtils.isBlank(extension))
            return file.toString();
        return file.append(".").append(extension).toString();
    }
}
