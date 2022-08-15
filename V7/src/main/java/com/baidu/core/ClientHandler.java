package com.baidu.core;

import com.baidu.http.HttpServletRequest;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
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
    private static File dir;
    private static File staticDir;

    static {
        try {
            dir = new File(ClientHandler.class.getClassLoader().getResource(".").toURI());
            staticDir = new File(dir, "static");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Socket socket;

    private int code;

    private String status;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //1.解析请求，实例化请求对象的过程就是解析的过程
            HttpServletRequest request = new HttpServletRequest(socket);

            //2.处理请求
            String path = request.getUri();
            System.out.println("请求的抽象路径 uri: " + path);
            //3.发送响应
            //本版本测试,将resources下的static目录中的index.html页面响应给浏览器
            //实际虚拟机执行是查看的是target/class目录下的内容
            //maven项目编译后会将src/main/java和src/main/resources下的内容合并放在target/classes下
            //因此我们实际要定位的是target/classes/static/index.html
            File file = new File(staticDir, path);
            /* 如果不是一个文件
             */
            if (file.getName().equals("")) {
                code = 200;
                status = " OK";
                file = new File(staticDir, "index.html");
            }
            if (!file.isFile()) {
                code = 404;
                status = " not found";
                file = new File(staticDir, "root/404.html");
            }

            /*
                HTTP/1.1 200 OK(CRLF)
                Content-Type: text/html(CRLF)
                Content-Length: 2546(CRLF)(CRLF)
                1011101010101010101......
             */

            //发送状态行
            String line = "HTTP/1.1 " + code + status;
            printLine(line);

            //发送响应头
            printLine("Content-Type: text/html");
            printLine("Content-Length: " + file.length());//告诉浏览器正文长度(单位字节)
            //单独发送个回车+换行表示响应头发送完毕
            printLine("");// ""-----out.write(13); out.write(10);

            //发送响应正文(index.html页面的所有数据)
            OutputStream out = socket.getOutputStream();
            FileInputStream fis = new FileInputStream(file);
            byte[] buf = new byte[1024 * 10];//10kb
            int len = 0;//记录每次实际读取的字节数
            while ((len = fis.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //按照HTTP协议要求,一问一答后断开连接
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void printLine(String line) throws IOException {
        OutputStream out = socket.getOutputStream();
        byte[] data = line.getBytes(StandardCharsets.ISO_8859_1);
        out.write(data);
        out.write(13);
        out.write(10);
    }
}
