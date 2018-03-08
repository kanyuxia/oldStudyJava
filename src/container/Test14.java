package container;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by kanyuxia on 2017/1/14.
 */
public class Test14 {
    public static void main(String[] args){
        List<Integer> list = new LinkedList<Integer>();
        ListIterator<Integer> iterator = list.listIterator();
        for(int i = 1;i < 11;i++){
            iterator.add(i);
            if(i%2 == 0){
                iterator.previous();
            }
        }
        System.out.println(list);
    }
}
