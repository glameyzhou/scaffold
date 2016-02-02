package org.glamey.scaffold.web.upload;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * <p>文件上传工具类
 * 参考文档：http://commons.apache.org/proper/commons-fileupload/using.html
 *
 * @author zhouyang.zhou.
 */
public class Uploader {

    private static final Logger LOGGER = LoggerFactory.getLogger(Uploader.class);
    private static final long MAX_SIZE_BYTES = 1024 * 1024 * 40;
    private static final int MAX_SIZE_THRESHOLD = 1024 * 1024 * 1;
    private static final File TEMP_REPOSITORY = new File(System.getProperty("user.home"));
    public static final String SEPARATOR = System.getProperty("file.separator");
    private HttpServletRequest request;
    private ServletFileUpload upload;
    private List<String> allowedSuffixList;
    private String baseDir;
    private static final DiskFileItemFactory FACTORY = new DiskFileItemFactory();

    public Uploader(HttpServletRequest request) {
        Preconditions.checkNotNull(request);
        new Uploader(request, null, null);
    }

    public Uploader(HttpServletRequest request, List<String> allowedSuffixList) {
        Preconditions.checkNotNull(request);
        Preconditions.checkNotNull(allowedSuffixList, "允许的文件后缀不能为空");
        new Uploader(request, allowedSuffixList, null);
    }

    public Uploader(HttpServletRequest request, String baseDir) {
        Preconditions.checkNotNull(request);
        Preconditions.checkNotNull(baseDir, "文件存放根目录不能为空");
        new Uploader(request, null, baseDir);
    }

    /**
     * 文件上传构造器
     *
     * @param request           {@link HttpServletRequest}
     * @param allowedSuffixList 允许上传的文件后缀
     * @param baseDir           文件上传保存的根目录
     */
    public Uploader(HttpServletRequest request, List<String> allowedSuffixList, String baseDir) {
        this.request = request;
        this.allowedSuffixList = allowedSuffixList;
        this.baseDir = baseDir;
        FACTORY.setSizeThreshold(MAX_SIZE_THRESHOLD);
        FACTORY.setRepository(TEMP_REPOSITORY);
        upload = new ServletFileUpload(FACTORY);
        upload.setSizeMax(MAX_SIZE_BYTES);
    }


    /**
     * 文件上传处理类
     *
     * @return {@link UploadResult}
     * @throws Exception 抛出异常
     */
    public UploadResult upload() throws Exception {
        boolean multipartContent = ServletFileUpload.isMultipartContent(request);
        if (!multipartContent) {
            throw new RuntimeException("上传表单enctype类型设置错误");
        }

        List<TextFormResult> textFormResultList = Lists.newArrayList();
        List<FileFormResult> fileFormResultList = Lists.newArrayList();

        List<FileItem> fileItems = upload.parseRequest(request);
        for (FileItem item : fileItems) {
            if (item.isFormField())
                textFormResultList.add(processFormField(item));
            else
                fileFormResultList.add(processUploadedFile(item));
        }
        return new UploadResult(textFormResultList, fileFormResultList);
    }

    /**
     * 解析上传域
     *
     * @param item 文件对象
     * @return {@link FileFormResult}
     * @throws IOException IO异常
     */
    private FileFormResult processUploadedFile(FileItem item) throws IOException {
        String name = item.getName();
        String fileSuffix = FilenameUtils.getExtension(name);
        if (CollectionUtils.isNotEmpty(allowedSuffixList) && !allowedSuffixList.contains(fileSuffix)) {
            throw new NotAllowedUploadException(String.format("上传文件格式不正确,fileName=%s,allowedSuffixList=%s",
                    name, allowedSuffixList));
        }
        FileFormResult file = new FileFormResult();
        file.setFieldName(item.getFieldName());
        file.setFileName(name);
        file.setContentType(item.getContentType());
        file.setSizeInBytes(item.getSize());

        //如果未设置上传路径，直接保存到项目根目录
        if (Strings.isNullOrEmpty(baseDir)) {
            baseDir = request.getRealPath("/");
        }
        File relativePath = new File(SEPARATOR + DateFormatUtils.format(new Date(), "yyyyMMdd") + file.getFileName());
        FileCopyUtils.copy(item.getInputStream(), new FileOutputStream(relativePath));
        file.setSaveRelativePath(relativePath.getAbsolutePath());
        return file;
    }

    /**
     * 解析文本域
     *
     * @param item 文件对象
     * @return {@link TextFormResult}
     */
    private TextFormResult processFormField(FileItem item) {
        return new TextFormResult(item.getFieldName(), item.getString());
    }

}
