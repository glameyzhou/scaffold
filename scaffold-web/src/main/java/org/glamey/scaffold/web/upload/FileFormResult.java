package org.glamey.scaffold.web.upload;

/**
 * <p>form表单文件域
 *
 * @author zhouyang.zhou
 */
public class FileFormResult {
    /**
     * form域名称
     */
    private String fieldName;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件类型
     */
    private String contentType;
    /**
     * 文件大小，单位为字节
     */
    private long sizeInBytes;
    /**
     * 文件保存的相对路径，针对根目录而言
     */
    private String saveRelativePath;

    public FileFormResult() {
    }

    public FileFormResult(String fieldName, String fileName, String contentType, long sizeInBytes, String saveRelativePath) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.contentType = contentType;
        this.sizeInBytes = sizeInBytes;
        this.saveRelativePath = saveRelativePath;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSizeInBytes() {
        return sizeInBytes;
    }

    public void setSizeInBytes(long sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }

    public String getSaveRelativePath() {
        return saveRelativePath;
    }

    public void setSaveRelativePath(String saveRelativePath) {
        this.saveRelativePath = saveRelativePath;
    }

    @Override
    public String toString() {
        return "FileFormResult{" +
                "fieldName='" + fieldName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", sizeInBytes=" + sizeInBytes +
                ", saveRelativePath='" + saveRelativePath + '\'' +
                '}';
    }
}
