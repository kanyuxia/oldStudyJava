package io;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by kanyuxia on 2017/3/3.
 */
public class Test12 {
    public static void main(String[] args) throws IOException{
        List<String> list = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader("E:\\java\\People.java"));
        String str;
        while ((str = reader.readLine()) != null){
            list.add(str);
        }
        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\java\\CopyPeople.java"));
        ListIterator<String> iterator = list.listIterator();
        while(iterator.hasNext()){
            str = iterator.nextIndex() + "\t" + iterator.next();
            writer.write(str);
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }
}
