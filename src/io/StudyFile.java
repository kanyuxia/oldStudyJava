package io;

import java.io.File;
import java.io.IOException;

/**
 * Created by kanyuxia on 2017/3/2.
 */
public class StudyFile {
    public static void main(String[] args) throws IOException {
        File file = new File("E:\\java\\America.java");
        boolean canExecute = file.canExecute();
        System.out.println(canExecute);
        boolean canRead = file.canRead();
        System.out.println(canRead);
        boolean canWrite = file.canWrite();
        System.out.println(canWrite);
        int compareTo = file.compareTo(new File("E:\\java\\People.java"));
        System.out.println(compareTo);
        boolean createNewFile = file.createNewFile();
        System.out.println(createNewFile);
//        boolean delete = file.delete();
//        System.out.println(delete);
        boolean exists = file.exists();
        System.out.println(exists);
        String absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath);
        System.out.println(file.getName());
        System.out.println(file.getParent());
        System.out.println(file.getPath());
        System.out.println(file.isAbsolute());
        System.out.println(file.isDirectory());
        System.out.println(file.isFile());
        System.out.println(file.lastModified());
        System.out.println(file.length());
        System.out.println(file.toURI());
    }
}
