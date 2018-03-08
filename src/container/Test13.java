package container;

import java.util.Arrays;
import java.util.List;

/**
 * Created by kanyuxia on 2017/1/16.
 */
public class Test13 {
    public static void main(String[] args){
        List<String> words = Arrays.asList("A B C D E F G H I A B C D I E A Z S O D A D M M H O Q A".split(" "));
        AssociativeArray<String,Integer> map = new AssociativeArray<String,Integer>(words.size());
        for(String word : words){
            Integer count = map.get(word);
            map.put(word,count == null ? 1 : count+1);
        }
        System.out.println(map);
    }
}

class AssociativeArray<K,V>{
    private Object[][] pairs;
    private int index = 0;
    AssociativeArray(int length){
        pairs = new Object[length][2];
    }
    public void put(K key,V value){
        for(int i = 0 ;i < index;i++){
            if(pairs[i][0].equals(key)){
                pairs[i][1] = value;
                return;
            }
        }
        if(index >= pairs.length){
            throw new ArrayIndexOutOfBoundsException();
        }
        pairs[index++] = new Object[]{key,value};
    }
    @SuppressWarnings("unchecked")
    public V get(K key){
        for(int i = 0;i < index ;i++){
            if(pairs[i][0].equals(key)){
                return (V) pairs[i][1];
            }
        }
        return null;
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int i = 0;i < index;i++){
            result.append(pairs[i][0]);
            result.append(":");
            result.append(pairs[i][1]);
            if(i < index -1){
                result.append("\n");
            }
        }
        return result.toString();
    }
}
