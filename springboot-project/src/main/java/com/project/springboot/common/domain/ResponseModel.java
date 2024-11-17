package com.project.springboot.common.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseModel<T> implements Serializable {

    //状态码 200为成功
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 返回
     */
    private T data;

    /**
     * token返回
     */

    private String token;



    public ResponseModel() {
    }

    public ResponseModel(Integer code, String msg, T data) {
        super();
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public ResponseModel(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public ResponseModel(Integer code, String msg, T data, String token) {
        super();
        this.code = code;
        this.message = msg;
        this.data = data;
        this.token = token;
    }

    /**
     * 返回成功信息
     *
     * @return
     */
    public static ResponseModel ok() {
        return ResponseModel.ok(200);
    }

    /**
     * 返回成功信息
     *
     * @param msg 成功信息
     * @return
     */

    public static ResponseModel ok(String msg) {
        return ResponseModel.ok(msg, null);
    }

    /**
     * 返回成功信息
     *
     * @param data 数据
     * @param <T>
     * @return
     */
    public static <T> ResponseModel ok(T data) {
        return ResponseModel.ok("成功 ", data);
    }

    /**
     * 返回成功信息
     *
     * @param msg  成功信息
     * @param data 数据
     * @param <T>
     * @return
     */
    public static <T> ResponseModel ok(String msg, T data) {
        return new ResponseModel(0, msg, data);
    }

    /**
     * 返回成功信息
     *
     * @param msg  成功信息
     * @param data 数据
     * @param <T>
     * @return
     */
    public static <T> ResponseModel ok(String msg, T data, String token) {
        return new ResponseModel(0, msg, data, token);
    }


    /**
     * 返回错误消息
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseModel error() {
        return ResponseModel.error("错误信息");
    }

    /**
     * 返回错误消息
     *
     * @param msg 错误信息
     * @param <T>
     * @return
     */

    public static <T> ResponseModel error(String msg) {
        return ResponseModel.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  错误信息
     * @param data 数据
     * @param <T>
     * @return
     */
    public static <T> ResponseModel error(String msg, T data) {
        return new ResponseModel(1, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 错误码
     * @param msg  错误信息
     * @param <T>
     * @return
     */

    public static <T> ResponseModel error(int code, String msg) {
        return new ResponseModel(code, msg, null);
    }


}
