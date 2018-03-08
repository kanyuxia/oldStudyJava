package container;

import java.util.*;
import java.util.LinkedList;

/**
 * Created by kanyuxia on 2017/1/13.
 */
public class StudyList {
    public static void main(String[] args){
        List<Integer> list = new ArrayList<>();
        System.out.println(list.add(null));
        System.out.println("No Function Before");
        Collections.addAll(list,1,5,2,6,3,4,9,7,8,10);
        System.out.println(list);
        System.out.println("Function add(E element)");
        list.add(5);
        System.out.println(list);
        System.out.println("Function add(int index,E element)");
        list.add(0,5);
        System.out.println(list);
        System.out.println("Fucntion containers");
        System.out.println(list.contains(5));
        System.out.println("Fucntion sublist");
        List<Integer> subList = list.subList(0,5);
        System.out.println(subList);
        System.out.println("list"+list);
        System.out.println("Fucntion containerAll()");
        System.out.println(list.containsAll(subList));
        System.out.println("Function get()");
        System.out.println(list.get(0));
        System.out.println("Fucntion indexOf()");
        System.out.println(list.indexOf(5));
        System.out.println("Fucntion remove()");
        System.out.println(list.remove(5));
        System.out.println(list);
        System.out.println("Function size()");
        System.out.println(list.size());
        System.out.println("Fucntion toArray()");
        Integer[] integers = list.toArray(new Integer[0]);
        System.out.println(Arrays.toString(integers));
        System.out.println("Function clear()");
        list.clear();
        System.out.println(list);
        System.out.println("Fucntion isEmpty()");
        System.out.println(list.isEmpty());
        list = new LinkedList<>();
//        System.out.println(list.add(null));
        list.add(1);
        list.add(9);
        Collections.addAll(list,1,6,8,9,4,5,4,2,1,0,10);
        System.out.println("sort before");
        System.out.println(list);
        list.sort(new ListComparator());
        System.out.println("sort after");
        System.out.println(list);
    }
}
class ListComparator implements Comparator<Integer>{
    @Override
    public int compare(Integer o1, Integer o2) {
        return o1 > o2 ? -1 : (o1.equals(o2) ? 0 : 1);
    }
}
