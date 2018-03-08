package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by kanyuxia on 2017/3/3.
 */
public class Test17 {
    public static void main(String[] args) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("E:\\java\\word.txt"));
        Map<String,Integer> wordCount = new TreeMap<>();
        String[] words;
        String wordLine;
        while((wordLine = reader.readLine()) != null){
            words = wordLine.split(" ");
            for(String word : words){
                Integer number = wordCount.get(word);
                wordCount.put(word, number == null ? 1 : number + 1);
            }
        }
        reader.close();
        System.out.println(wordCount);
    }
}
