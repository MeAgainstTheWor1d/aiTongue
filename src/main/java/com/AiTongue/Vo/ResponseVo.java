package com.AiTongue.Vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * @Author: 翰林猿
 * @Description: 与前端约定好的Vo类
 **/
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)      //去掉为空的值再返回给前端
public class ResponseVo<T> {
    private Integer status;
    private String msg;
    private T data;

    public ResponseVo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ResponseVo(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    //前端和我们约定好了，如果是成功的话要返回status=0，再带上他的msg，所以我们直接编写一个静态方法，方便一点。
    public static <T> ResponseVo<T> successByMsg(String msg){
        return new ResponseVo<>(0,msg);
    }
    public static <T> ResponseVo<T> success() {
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getDesc());
    }

    public static <T> ResponseVo<T> success(T data) {
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(), data);
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum){
        return new ResponseVo<>(responseEnum.getCode(),responseEnum.getDesc());
    }
    public static <T> ResponseVo<T> error(ResponseEnum responseEnum, String msg) {
        return new ResponseVo<>(responseEnum.getCode(), msg);
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum, BindingResult bindingResult) {
        System.out.println("1");
        return new ResponseVo<>(responseEnum.getCode(),
                Objects.requireNonNull(bindingResult.getFieldError()).getField() + " " + bindingResult.getFieldError().getDefaultMessage());
    }

}
