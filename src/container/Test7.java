package container;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.LinkedList;

/**
 * Created by kanyuxia on 2017/1/15.
 */
public class Test7 {
    public static void main(String[] args){
        List<Integer> arrayList = new ArrayList<Integer>();
        fill(arrayList);
        List<Integer> linkedList = new LinkedList<Integer>();
        fill(linkedList);
        Iterator<Integer> iterator = arrayList.iterator();
        System.out.println("-----------ArrayList------------");
        while(iterator.hasNext()){
            System.out.print(iterator.next()+"\t");
        }
        System.out.println();
        System.out.println("-----------LinkedList-------------");
        Iterator<Integer> iterator1 = linkedList.iterator();
        while (iterator1.hasNext()){
            System.out.print(iterator1.next()+"\t");
        }
        ListIterator<Integer> arrayIterator = arrayList.listIterator(arrayList.size());
        ListIterator<Integer> linkedIterator = linkedList.listIterator();
        int index = 1;
        int size = arrayList.size();
        while(arrayIterator.hasPrevious()){
            if(index > 1 && index < size){
                arrayIterator.previous();
                arrayIterator.previous();
                if (linkedIterator.hasNext()){
                    arrayIterator.add(linkedIterator.next());
                }
                index++;
            }else if(index == 1){
                arrayIterator.previous();
                if (linkedIterator.hasNext()){
                    arrayIterator.add(linkedIterator.next());
                }
                index++;
            } else {
                break;
            }
        }
        System.out.println();
        System.out.println(arrayList);
    }

    public static void fill(List<Integer> list){
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
    }
}
