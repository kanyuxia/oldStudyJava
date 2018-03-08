package array;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by kanyuxia on 2017/1/13.
 */
public class ArraySort {
    public Random random = new Random();

    private People[] peoples = new People[10];

    ArraySort(){
        init();
    }

    public void init(){
        for(int i = 0;i < peoples.length;i++){
            peoples[i] = new People(random.nextInt(100));
        }
    }

    public void sort(){
        System.out.println("Sort Before");
        System.out.println(Arrays.toString(peoples));
        System.out.println("\n");
        System.out.println("Comparable After");
        Arrays.sort(peoples,null);
        System.out.println(Arrays.toString(peoples));
        System.out.println("\n");
        System.out.println("Comparator After");
        Arrays.sort(peoples,new PeopleComparator());
        System.out.println(Arrays.toString(peoples));
    }



    public static void main(String[] args){
        ArraySort arraySort = new ArraySort();
        arraySort.sort();
    }
}
