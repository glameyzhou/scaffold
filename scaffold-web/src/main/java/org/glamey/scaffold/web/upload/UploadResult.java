package org.glamey.scaffold.web.upload;

import java.util.List;

/**
 * form表单文件上传结果
 *
 * @author zhouyang.zhou
 */

public class UploadResult {
    private List<TextFormResult> textFormResultList;
    private List<FileFormResult> fileFormResultList;

    public UploadResult() {
    }

    public UploadResult(List<TextFormResult> textFormResultList, List<FileFormResult> fileFormResultList) {
        this.textFormResultList = textFormResultList;
        this.fileFormResultList = fileFormResultList;
    }

    public List<TextFormResult> getTextFormResultList() {
        return textFormResultList;
    }

    public void setTextFormResultList(List<TextFormResult> textFormResultList) {
        this.textFormResultList = textFormResultList;
    }

    public List<FileFormResult> getFileFormResultList() {
        return fileFormResultList;
    }

    public void setFileFormResultList(List<FileFormResult> fileFormResultList) {
        this.fileFormResultList = fileFormResultList;
    }

    @Override
    public String toString() {
        return "UploadResult{" +
                "textFormResultList=" + textFormResultList +
                ", fileFormResultList=" + fileFormResultList +
                '}';
    }
}
