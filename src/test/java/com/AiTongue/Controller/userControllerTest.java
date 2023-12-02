package com.AiTongue.Controller;

import org.junit.jupiter.api.Test;

import java.io.IOException;


/**
 * @Author: 翰林猿
 * @Description: TODO
 **/
class userControllerTest {

    private userController UserController;

    @Test
    void register() throws IOException {
        UserController.register();
    }

    @Test
    void login() throws IOException {
        UserController.login();
    }
}