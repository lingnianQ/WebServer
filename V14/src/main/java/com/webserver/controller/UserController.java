package com.webserver.controller;


import com.webserver.core.DispatcherServlet;
import com.webserver.entity.User;
import com.webserver.http.HttpServletRequest;
import com.webserver.http.HttpServletResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;

/**
 * @author sytsnb@gmail.com
 * @Date 2022 2022/8/17 10:15
 */
public class UserController {

    private static File userDir;

    static {
        userDir = new File("V14/src/main/resources/template/user");
        if (!userDir.exists()) {
            userDir.mkdirs();
        }
    }

    /**
     * 用户注册
     * @param request
     * @param response
     */
    public void regUser(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameters("username");
        String password = request.getParameters("password");
        String nickname = request.getParameters("nickname");
        String ageStr = request.getParameters("age");

        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                nickname == null || nickname.isEmpty() ||
                ageStr == null || ageStr.isEmpty() || !ageStr.matches("[0-9]+")) {
            response.sendRedirect("/register/reg_info_error.html");
            return;
        }


        int age = Integer.parseInt(ageStr);
        User user = new User(username, password, nickname, age);

        File file = new File(userDir, username + ".obj");
        if (file.exists()) {
            response.sendRedirect("/register/have_user.html");
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/register/reg_success.html");

        System.out.println("user: " + user);

    }
}
