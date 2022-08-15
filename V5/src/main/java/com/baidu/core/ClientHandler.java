package com.baidu.core;

import com.baidu.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * 与客户端完成一次HTTP的交互
 * @author sytsnb@gmail.com
 * @version V4
 * @Date 2022 2022/8/12 10:20
 */

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //1.解析请求，实例化请求对象的过程就是解析的过程
            HttpServletRequest request = new HttpServletRequest(socket);
            String uri = request.getUri();
            System.out.println("uri: " + uri);
            //2.处理请求

            //3.发送响应

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
