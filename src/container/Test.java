package container;

import java.util.*;

/**
 * Created by kanyuxia on 2017/2/14.
 */
public class Test {
    public static void main(String[] args){
        List<String> list = Collections.synchronizedList(new ArrayList<String>());
        list.add("hello");
        list.add("world");
        list.add("hello");
        System.out.println(list);
        list.remove("hello");
        System.out.println(list);
    }

}
