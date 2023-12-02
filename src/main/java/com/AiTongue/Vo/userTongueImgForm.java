package com.AiTongue.Vo;

import lombok.Data;

/**
 * @Author: 翰林猿
 * @Description: TODO
 **/
@Data
public class userTongueImgForm {
    private String nickName;

    private String base64Data;

    private int age;

    private int gender;

    private int isYuejin;//是否来月经

}
