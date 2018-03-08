package container;

import java.util.*;

/**
 * Created by kanyuxia on 2017/1/14.
 */
public class StudySet {

    public Set fill(Set<People> set){
        set.add(new People(10));
        set.add(new People(12));
        set.add(new People(5));
        set.add(new People(6));
//        set.add(null);
        return set;
    }

    public void printf(Set<People> set){
        for(People people : set){
            System.out.print(people+"\t");
        }
        System.out.println();
    }

    public static void main(String[] args){
        StudySet studySet = new StudySet();

        Set<People> hashSet = new HashSet<>();
        studySet.fill(hashSet);
        studySet.printf(hashSet);
//        System.out.println("hashSet: "+hashSet);
//        System.out.println("add People:"+hashSet.add(new People(10)));
//        System.out.println("contains:"+hashSet.contains(new People(10)));
//
        Set<People> treeSet = new TreeSet<>();
        studySet.fill(treeSet);
        studySet.printf(treeSet);
//        System.out.println("treeSet implement Comparable: "+treeSet);
//        System.out.println("add People: "+treeSet.add(new People(10)));
//        System.out.println("contains: "+treeSet.contains(new People(10)));
//        Set<People> treeSet1 = new TreeSet<People>(new PeopleComparator());
//        studySet.fill(treeSet1);
//        System.out.println("treeSet implement Comparator: "+treeSet1);
//
        Set<People> linkedHashSet = new LinkedHashSet<>();
        studySet.fill(linkedHashSet);
        studySet.printf(linkedHashSet);
//        System.out.println("linkHashSet: "+linkedHashSet);

        SortedSet<People> sortedSet = new TreeSet<>(Collections.reverseOrder());
        studySet.fill(sortedSet);
        System.out.println(sortedSet);
        System.out.println("comparator()"+sortedSet.comparator());
        System.out.println("first()"+sortedSet.first());
        System.out.println("last()"+sortedSet.last());
        System.out.println("headSet()"+sortedSet.headSet(new People(10)));
        System.out.println("taileSet()"+sortedSet.tailSet(new People(10)));
        System.out.println("subSet()"+ sortedSet.subSet(new People(5),new People(12)));
    }
}
class People implements Comparable<People>{
    private int age;

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
    People(int age){
        this.age = age;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getAge());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof People &&
                ((People) obj).getAge() == this.getAge();
    }

    @Override
    public int hashCode() {
        return this.getAge();
    }

    @Override
    public int compareTo(People o) {
        return o.getAge() > this.getAge() ? 1 : (o.getAge() == this.getAge() ? 0 : -1);
    }
}

class PeopleComparator implements Comparator<People>{
    @Override
    public int compare(People o1, People o2) {
        return o1.getAge() > o2.getAge() ? 1 : (o1.getAge() == o2.getAge() ? 0 : -1);
    }
}
