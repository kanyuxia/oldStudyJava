package io;

import java.io.*;

/**
 * Created by kanyuxia on 2017/3/3.
 */
public class Test15 {
    public static void main(String[] args) throws IOException{
        DataInputStream in = new DataInputStream(
                new BufferedInputStream(new FileInputStream("E:\\java\\Chinese.java")));
        DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream("E:\\java\\CopyChinese.java")));
        byte[] bytes = new byte[3];
        while(in.read(bytes) != -1){
            out.write(bytes);
        }
        in.close();
        out.flush();
        out.close();
    }
}
