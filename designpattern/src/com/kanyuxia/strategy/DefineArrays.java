package com.kanyuxia.strategy;

/**
 * Created by kanyuxia on 2017/3/7.
 */
public class DefineArrays {
    @SuppressWarnings("unchecked")
    public static void sort(Object[] a){
        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a.length - i - 1; j++){
                Comparable c1 = (Comparable) a[j];
                if(c1.compareTo(a[j + 1]) > 0){
                    swap(a, j, j + 1);
                }
            }
        }
    }

    public static <T> void sort(T[] a, Comparator<? super T> c){
        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a.length - i - 1; j++){
                if(c.compare(a[j], a[j + 1]) > 0){
                    swap(a, j, j + 1);
                }
            }
        }
    }

    public static void toStrings(Object[] a){
        System.out.print("[");
        for(Object o : a){
            System.out.print(o.toString() + " ");
        }
        System.out.println("]");
    }

    private static void swap(Object[] a, int i, int j){
        Object tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
