package array;

import java.util.Arrays;

/**
 * Created by kanyuxia on 2017/1/13.
 */
public class SearchArray {
    private int[] a = {1,2,9,10,6,5,4};
    public void binarySearch(){
        System.out.println("Sort Before");
        System.out.println(Arrays.toString(a));
        System.out.println();
        System.out.println("BinarySearch: "+ Arrays.binarySearch(a,5));
        System.out.println();
        System.out.println("Sort After");
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
        System.out.println();
        System.out.println("BinarySearch After Sort: "+Arrays.binarySearch(a,5));
    }

    public static void main(String[] args){
        new SearchArray().binarySearch();
    }
}
