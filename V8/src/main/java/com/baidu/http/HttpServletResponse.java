package com.baidu.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 响应对象
 * 该类的每一个实例用于表示一个HTTP协议要求的响应内容
 * 每个响应由三部分构成:
 * 状态行,响应头,响应正文
 * @author sytsnb@gmail.com
 * @Date 2022 2022/8/16 11:20
 */

public class HttpServletResponse {
    private Socket socket;

    //状态行相关信息
    private int code = 200;
    private String reason = "OK";


    //响应正文相关信息
    private File contentFile;//正文对应的实体文件

    public HttpServletResponse(Socket socket){
        this.socket = socket;
    }

    /**
     * 将当前响应对象内容按照标准的响应格式发送给客户端
     */
    public void response() throws IOException {
        //发送状态行
        sendStatusLine();
        //发送响应头
        sendHeaders();
        //发送响应正文
        sendContent();
    }
    //发送状态行
    private void sendStatusLine() throws IOException {
//        HTTP/1.1 200 OK
        println("HTTP/1.1" + " " + code + " " + reason);
    }
    //发送响应头
    private void sendHeaders() throws IOException {
        println("Content-Type: text/html");//告诉浏览器正文类型
        println("Content-Length: "+contentFile.length());//告诉浏览器正文长度(单位字节)
        //单独发送个回车+换行表示响应头发送完毕
        println("");
    }
    //发送响应正文
    private void sendContent() throws IOException {
        OutputStream out = socket.getOutputStream();
        FileInputStream fis = new FileInputStream(contentFile);
        byte[] buf = new byte[1024*10];//10kb
        int len = 0;//记录每次实际读取的字节数
        while( (len = fis.read(buf)) != -1  ){
            out.write(buf,0,len);
        }
    }


    private void println(String line) throws IOException {
        OutputStream out = socket.getOutputStream();
        byte[] data = line.getBytes(StandardCharsets.ISO_8859_1);
        out.write(data);
        out.write(13);
        out.write(10);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public File getContentFile() {
        return contentFile;
    }

    public void setContentFile(File contentFile) {
        this.contentFile = contentFile;
    }
}
