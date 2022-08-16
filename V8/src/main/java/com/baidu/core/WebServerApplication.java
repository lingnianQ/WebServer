package com.baidu.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * WebServer web容器
 * 用于实现Tomcat基本功能
 * @author sytsnb@gmail.com
 * @version V4
 * @Date 2022 2022/8/12 9:24
 */

public class WebServerApplication {
    private ServerSocket serverSocket;

    public WebServerApplication() {

        try {
            System.out.println("正在启动服务器！！");
            serverSocket = new ServerSocket(8088);
            System.out.println("服务器启动成功！！");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        try {
            while (true) {
                System.out.println("等待客户端连接！！！");
                Socket socket = serverSocket.accept();
                System.out.println("客户端连接成功！！");
                //启动一个线程处理该客户端的交互
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread t = new Thread(clientHandler);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebServerApplication server = new WebServerApplication();
        server.start();
    }
}
