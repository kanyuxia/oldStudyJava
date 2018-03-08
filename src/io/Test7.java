package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by kanyuxia on 2017/3/3.
 */
public class Test7 {
    public static void main(String[] args) throws IOException{
        LinkedList<String> list = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader("E:\\java\\People.java"));
        String str;
        while(( str = reader.readLine()) != null){
            list.add(str);
        }
        reader.close();
        // 方法一
        ListIterator<String> iterator = list.listIterator(list.size());
        while(iterator.hasPrevious()){
            System.out.println(iterator.previous());
        }

        System.out.println("~~~~~~~~~~~~~~");
        // 方法二
        while(list.peekLast() != null){
            System.out.println(list.pollLast());
        }
    }
}
