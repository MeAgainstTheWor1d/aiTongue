package com.AiTongue.Vo;

import lombok.Getter;

/**
 * @Author: 翰林猿
 * @Description: 使用枚举规范一下返回的格式
 **/
@Getter
public enum ResponseEnum {

	ERROR(-1, "服务端错误"),
	SUCCESS(0, "成功"),
	IMG_NOT_EXIST(1, "图片不存在");

	Integer code;
	String desc;

	ResponseEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
