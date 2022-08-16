package com.baidu.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author sytsnb@gmail.com
 * @Date 2022 2022/8/15 9:25
 */

public class HttpServletRequest {
    private Socket socket;
    //请求行
    private String method;
    private String uri;
    private String protocol;
    //消息头相关信息
    private Map<String, String> headers = new LinkedHashMap<>();

    public HttpServletRequest(Socket socket) throws IOException {
        this.socket = socket;
        //1.解析请求行
        parseRequestLine();
        //2.解析消息头
        parseHeaders();
        //3.解析正文
        parseContent();

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

    //解析请求行
    public void parseRequestLine() throws IOException {
        //读取请求行
        String line = readLine();
        //按空格分隔
        String[] data = line.split("\\s");
        method = data[0];
        uri = data[1];
        protocol = data[2];
        System.out.println("method: " + method);
        System.out.println("uri: " + uri);
        System.out.println("protocol: " + protocol);
    }

    //解析消息头
    public void parseHeaders() throws IOException {
        while (true) {
            String line = readLine();
            if (line.isEmpty()) {
                break;
            }
            String[] data1 = line.split(":\\s");
            String key = data1[0];
            String value = data1[1];
            headers.put(key, value);
//                System.out.println("消息头： " + line);
        }
        Set<Map.Entry<String, String>> entrySet = headers.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            System.out.println(entry);
        }
    }

    //解析消息正文
    private void parseContent() {
    }


    public Socket getSocket() {
        return socket;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHeaders(String key) {
        return headers.get(key);
    }
}
