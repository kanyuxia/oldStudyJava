package container;

import java.util.*;
import java.util.HashMap;

/**
 * Created by kanyuxia on 2017/1/14.
 */
public class StudyMap {
    public static void main(String[] args){
        StudyMap studyMap = new StudyMap();

        Map<Country,List<Province>> hashMap = new HashMap<Country,List<Province>>(10, 0.75f);
        studyMap.fill(hashMap);
        studyMap.printf(hashMap);

        TreeMap<Country,List<Province>> treeMap = new TreeMap<Country,List<Province>>();
        studyMap.fill(treeMap);
        studyMap.printf(treeMap);

        LinkedHashMap<Country,List<Province>> linkedHashMap = new LinkedHashMap<Country,List<Province>>(5,0.75f,true);
        studyMap.fill(linkedHashMap);
        // TODO: 2017/1/16 注意 LinkedHashMap(int initialCapacity,float loadFactor,boolean accessOrder)
        // TODO: 不能使用Collection接口的iterator()返回的迭代器Iterator,会发生ConcurrentModificationException
//        studyMap.printf(linkedHashMap);
        System.out.println(linkedHashMap);
        List<Province> provinces = linkedHashMap.get(new Country("中国"));
        System.out.println(provinces);
        System.out.println(linkedHashMap);
    }

    public void printf(Map<Country,List<Province>> map){
//        for (Country country : map.keySet()){
//            System.out.print(country+" 包括: ");
//            for(Province province : map.get(country)){
//                System.out.print(province+" ");
//            }
//            System.out.println();
//        }
        for(Map.Entry<Country,List<Province>> entry : map.entrySet()){
            System.out.print(entry.getKey()+" 包括: ");
            for(Province province : entry.getValue()){
                System.out.print(province + " ");
            }
            System.out.println();
        }
    }


    public Map fill(Map<Country,List<Province>> map){
        map.put(new Country("中国"), Arrays.asList(new Province("四川"),new Province("北京")));
        map.put(new Country("美国"), Arrays.asList(new Province("纽约"),new Province("华盛顿")));
        return map;
    }
}
class Country implements Comparable<Country>{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Country(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Country &&
                ((Country)obj).getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public int compareTo(Country o) {
        return 0;
    }
}
class Province{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Province(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}

