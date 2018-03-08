package container;

import java.util.*;
import java.util.LinkedList;

/**
 * Created by kanyuxia on 2017/1/14.
 */
public class StudyIterator {

    public static void main(String[] args){
        StudyIterator studyIterator = new StudyIterator();
        List<String> arrayList = new ArrayList<String>();
        studyIterator.fill(arrayList);
        studyIterator.display(arrayList.iterator());
        List<String> linkedList = new LinkedList<String>();
        studyIterator.fill(linkedList);
        studyIterator.display(linkedList.iterator());
        Set<String> hashSet = new HashSet<String>();
        studyIterator.fill(hashSet);
        studyIterator.display(hashSet.iterator());
        Set<String> treeSet = new TreeSet<String>();
        studyIterator.fill(treeSet);
        studyIterator.display(treeSet.iterator());
        Set<String> linkedHashSet = new LinkedHashSet<String>();
        studyIterator.fill(linkedHashSet);
        studyIterator.display(linkedHashSet.iterator());
        List<String> list = new ArrayList<String>();
        list.add("dog");
        list.add("cat");
        list.add("snack");
        list.add("dog");
        studyIterator.studyListIterator(list);
    }

    public Collection fill(Collection<String> collection){
        collection.add("dog");
        collection.add("cat");
        collection.add("snack");
        collection.add("dog");
        return collection;
    }

    public void display(Iterator<String> iterator){
        while(iterator.hasNext()){
            System.out.print(iterator.next()+"\t");
        }
        System.out.println();
    }




    public void studyListIterator(List<String> list){
        List<String> list1 = new ArrayList<String>(list.size());
        ListIterator<String> listIterator = list.listIterator(list.size());
        while(listIterator.hasPrevious()){
            list1.add(listIterator.previous());
        }
        System.out.println("list:"+list);
        System.out.println("list1:"+list1);
    }



}
