package com.baidu;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 与客户端完成一次HTTP的交互
 * V2
 * @author sytsnb@gmail.com
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

            int d;
            while ((d = input.read()) != -1) {
                System.out.print((char) d);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
