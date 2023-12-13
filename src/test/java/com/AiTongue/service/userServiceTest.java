package com.AiTongue.service;

import com.AiTongue.springboot1.SpringBoot1ApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;


/**
 * @Author: 翰林猿
 * @Description: TODO
 **/
class userServiceTest extends SpringBoot1ApplicationTests {

    @Autowired
    private userService userService;

    @Test
    void getJarDirectory() throws UnsupportedEncodingException {
        userService.getJarDirectory();
    }
}