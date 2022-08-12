package com.baidu;

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
            //读取请求行
            String line = readLine();
            //按空格分隔
            String[] data = line.split("\\s");
            String method = data[0];
            String uri = data[1];
            String protocol = data[2];
            System.out.println("method: " + method);
            System.out.println("uri: " + uri);
            System.out.println("protocol: " + protocol);

            Map<String, String> map = new LinkedHashMap<>();
            while (true) {
                line = readLine();
                if (line.isEmpty()) {
                    break;
                }
                String[] data1 = line.split("\\:\\s");
                String key = data1[0];
                String value = data1[1];
                map.put(key, value);
//                System.out.println("消息头： " + line);
            }
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                System.out.println(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取一行
     */
    private String readLine() throws IOException {
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
        return sb.toString().trim();
    }

//javaWeb
}
