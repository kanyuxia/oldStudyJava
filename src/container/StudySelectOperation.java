package container;

import java.util.*;

/**
 * Created by kanyuxia on 2017/1/15.
 */
public class StudySelectOperation {

    public static void main(String[] args){
        List<String> list = Arrays.asList("A,B,C,D,E,F,G".split(","));
        StudySelectOperation studySelectOperation = new StudySelectOperation();

        studySelectOperation.selectOperation("Arrays.asList()",list);
        System.out.println(list);
        List<String> list1 = new ArrayList<String>(list);
        studySelectOperation.selectOperation("Modifiable Copy",list1);
        System.out.println(list1);
        List<String> list2 = Collections.unmodifiableList(list);
        studySelectOperation.selectOperation("unmoditiableLit()", list2);
        System.out.println(list2);
    }

    public void selectOperation(String msg, List<String> list){
        System.out.println("---------"+msg+"---------");
        Collection<String> c = list;
        Collection<String> c1 = list.subList(0,5);
        System.out.println("---------not selectOperation----------");
        System.out.println("contains(): "+c.contains("A"));
        System.out.println("containsAll(): "+c.containsAll(c1));
        System.out.println("isEmpty(): "+c.isEmpty());
        System.out.println("iterator()"+c.iterator());
        System.out.println("size()"+c.size());
        System.out.println("toArray()"+c.toArray(new String[0]));
        System.out.println("---------selectOperation--------");
        try {
            c.add("Z");
        }catch (Exception e){
            System.out.println("add()"+e);
        }
        try {
            c.addAll(c1);
        }catch (Exception e){
            System.out.println("addAll()"+e);
        }
        try {
            c.clear();
        }catch (Exception e){
            System.out.println("clear()"+e);
        }
        try {
            c.remove("A");
        }catch (Exception e){
            System.out.println("remove()"+e);
        }
        try {
            c.removeAll(c1);
        }catch (Exception e){
            System.out.println("removeAll()"+e);
        }
        try {
            c.retainAll(c1);
        }catch (Exception e){
            System.out.println("retainAll()"+e);
        }
        try {
            list.set(0,"Z");
        }catch(Exception e){
            System.out.println("list.set()"+e);
        }
    }
}
