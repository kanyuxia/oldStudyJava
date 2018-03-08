package io;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by kanyuxia on 2017/3/3.
 */
public class Test16 {
    public static void main(String[] args) throws IOException{
        RandomAccessFile rf = new RandomAccessFile("E:\\java\\hello.txt","r");
        RandomAccessFile rf1 = new RandomAccessFile("E:\\java\\copyhello.txt","rw");
        byte[] bytes = new byte[1];
        rf.seek(rf.length()/3);
        while(rf.read(bytes) != -1){
            System.out.println(rf.getFilePointer());
            rf1.write(bytes);
        }
        rf.close();
        rf1.close();
    }
}
