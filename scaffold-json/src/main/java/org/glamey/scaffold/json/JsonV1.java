package org.glamey.scaffold.json;


import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author by zhouyang.zhou.
 */
public class JsonV1<T> {
    private Integer status = Codes.OK;
    private String message;
    private T data;

    public JsonV1() {
    }

    public JsonV1(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
