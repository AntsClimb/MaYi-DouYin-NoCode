package com.ants.douyin.util.result;

/**
 * @author dadan
 * @data 2020/1/10 9:09
 */
public enum ResultEnum {
    //这里是可以自己定义的，方便与前端交互即可
    SUCCESS(200,"成功"),
    UNKNOWN_ERROR(201,"未知错误"),
    DATA_IS_NULL(202,"数据为空"),
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
