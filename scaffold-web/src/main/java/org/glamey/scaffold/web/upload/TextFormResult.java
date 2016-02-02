package org.glamey.scaffold.web.upload;

/**
 * <p>form表单文本域对象
 *
 * @author zhouyang.zhou
 */
public class TextFormResult {
    private String fieldName;
    private String text;

    public TextFormResult() {
    }

    public TextFormResult(String fieldName, String text) {
        this.fieldName = fieldName;
        this.text = text;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TextFormResult{" +
                "fieldName='" + fieldName + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
