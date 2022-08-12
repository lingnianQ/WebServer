package com.baidu;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 与客户端完成一次HTTP的交互
 * @author sytsnb@gmail.com
 * @version V3
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
            InputStream input = socket.getInputStream();
//            http://localhost:8088/index.html
            int d;
            StringBuilder sb = new StringBuilder();
            char pre = 'a', cur = 'a';
            while ((d = input.read()) != -1) {
                cur = (char) d;
                if (pre == 13 && cur == 10) {
                    break;
                }
                sb.append(cur);
                pre = cur;
            }
            String sb1 = sb.toString().trim();
            String[] data = sb1.split("\\s");
            String method = data[0];
            String uri = data[1];
            String protocol = data[2];
            System.out.println("method: " + method + "\nuri: " + uri + "\nprotocol: " + protocol);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
