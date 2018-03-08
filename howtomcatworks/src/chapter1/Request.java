package chapter1;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kanyuxia on 2017/4/16.
 */
public class Request {
    private final InputStream inputStream;
    private String url;

    Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * 解析请求方法
     */
    public void parse() {
        BufferedInputStream in = new BufferedInputStream(inputStream);
        // 读取HTTP请求一些字节
        StringBuilder firstLine = new StringBuilder(1024);
        byte[] buffer = new byte[1024];
        int byteNum = 0;
        try {
            byteNum = in.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < byteNum; i++) {
            firstLine.append((char) buffer[i]);
        }
        // 解析Url
        int index = firstLine.toString().indexOf(" ");
        int index1 = firstLine.toString().indexOf(" ", index + 1);
        this.url = firstLine.toString().substring(index + 1, index1);
    }

    public String getUrl() {
        return url;
    }
}
