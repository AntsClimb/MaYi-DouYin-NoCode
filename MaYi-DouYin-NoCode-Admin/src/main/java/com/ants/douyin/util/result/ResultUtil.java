package com.ants.douyin.util.result;

/**
 * @author dadan
 * @data 2020/1/10 9:10
 */
public class ResultUtil<T> {

    /**成功且带数据**/
    public static Result success(Object object){
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    /**成功但不带数据**/
    public static Result success(){
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        return success(result);
    }

    /**失败**/
    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
