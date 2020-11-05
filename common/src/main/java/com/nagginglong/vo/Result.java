package com.nagginglong.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description:
 *
 * @author: nagginglong
 * @CreateTime: 2020/10/29   20:22
 */

@Data
@NoArgsConstructor
public class Result<T>{

    private Integer code;
    private String msg;
    private T data;

    public static  <T>Result setResult(Integer code, String msg, T data){

        Result result = new Result();

        result.code = code;
        result.msg = msg;
        result.data = data;

        return result;
    }

    public static Result ok(){

        return Result.setResult(1,"OK",null);

    }

    public static <T>Result ok(T t){

        return Result.setResult(1,"OK",t);

    }

    public static <T>Result fail(){

        return new Result().setResult(0,"fail",null);

    }

    public static <T>Result fail(String msg){

        return new Result().setResult(0,msg,null);

    }








}
