package container;

import java.util.*;
import java.util.LinkedList;
/**
 * Created by kanyuxia on 2017/1/13.
 */

public class StudyContainer {
    public static void main(String[] args){
        StudyContainer container = new StudyContainer();
        System.out.println(container.fill(new ArrayList<String>()));
        System.out.println(container.fill(new LinkedList<String>()));
        System.out.println(container.fill(new HashSet<String>()));
        System.out.println(container.fill(new TreeSet<String>()));
        System.out.println(container.fill(new LinkedHashSet<String>()));
        System.out.println(container.fill(new HashMap<String, String>()));
        System.out.println(container.fill(new TreeMap<String,String>()));
        System.out.println(container.fill(new LinkedHashMap<String, String>()));
    }

    public Collection fill(Collection<String> collection){
        collection.add("dog");
        collection.add("cat");
        collection.add("snack");
        collection.add("dog");
        if(collection instanceof TreeSet){
            System.out.println("TreeSet"+collection);
        }
        return collection;
    }

    public Map fill(Map<String,String> map){
        map.put("dog","Fuzzy");
        map.put("cat","Luccy");
        map.put("snack","Ducke");
        map.put("dog","Fuzzy1d");
        return map;
    }

}
