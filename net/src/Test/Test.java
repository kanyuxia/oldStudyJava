package Test;

import java.io.*;

/**
 * Created by kanyuxia on 2017/5/4.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        File file = new File("E:\\java\\test.txt");
        int fileLength = (int)file.length();
        byte[] buffer = new byte[fileLength];
        ReserveString reserveString = new ReserveString(new BufferedInputStream(new FileInputStream(file)));
        int c = reserveString.read(buffer, 0, fileLength);
        for (int i = 0; i < c; i++) {
            System.out.print((char) buffer[i]);
        }
    }
}
