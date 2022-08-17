package com.webserver.controller;


import com.webserver.core.DispatcherServlet;
import com.webserver.entity.User;
import com.webserver.http.HttpServletRequest;
import com.webserver.http.HttpServletResponse;

import java.io.*;
import java.net.URISyntaxException;

/**
 * @author sytsnb@gmail.com
 * @Date 2022 2022/8/17 10:15
 */
public class UserController {

    private static File userDir;

    static {
        userDir = new File("V14/src/main/resources/template/users");
        if (!userDir.exists()) {
            userDir.mkdirs();
        }
    }

    /**
     * 用户登录
     * @param request
     * @param response
     */
    public void login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameters("username");
        String password = request.getParameters("password");
        User user = new User(username, password);

        File file = new File(userDir, username + ".obj");
        if (!file.exists() || username == null || password == null || username.isEmpty() || password.isEmpty()) {
            response.sendRedirect("/login/login_info_error.html");
            return;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            User user1 = (User) ois.readObject();
            if (user.getName().equals(user1.getName())&&user.getPassword().equals(user1.getPassword())){
                response.sendRedirect("/login/login_success.html");
                return;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/login/login_fail.html");
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
