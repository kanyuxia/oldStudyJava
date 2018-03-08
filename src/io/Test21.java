package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by kanyuxia on 2017/3/3.
 */
public class Test21 {
    public static void main(String[] args) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str;
        while((str = reader.readLine()) != null && str.length() != 0){
            System.out.println(str.toUpperCase());
        }
    }
}
