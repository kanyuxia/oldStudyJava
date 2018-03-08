package container;

import java.util.*;
import java.util.LinkedList;

/**
 * Created by kanyuxia on 2017/2/2.
 */
public class Test20 {
    public static void main(String[] args){
        List<String> words = new ArrayList<>(Arrays.asList("A B C D E F G H I J K A B D I A O E D F".split(" ")));
        DefineHashMap<String,Integer> map = new DefineHashMap<>();
        System.out.println(words);
        for(String word : words){
            Integer count = map.get(word);
            map.put(word,count == null ? 1 : count + 1);
        }
        System.out.println(map);
    }
}

class DefineHashMap<K, V> extends AbstractMap<K, V> {

    private static final int SIZE = 997;

    @SuppressWarnings("unchecked")
    private LinkedList<EntrySet>[] buckets = new LinkedList[SIZE];

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for(LinkedList<EntrySet> bucket : buckets){
            if(bucket == null){
                continue;
            }
            set.addAll(bucket);
        }
        return set;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public V remove(Object key) {
        int index = Math.abs(key.hashCode()) % SIZE;
        if(buckets[index] == null){
            return null;
        }
        Iterator<EntrySet> iterator = buckets[index].iterator();
        while(iterator.hasNext()){
            EntrySet e = iterator.next();
            if(e.getKey().equals(key)){
                V oldValue = e.getValue();
                iterator.remove();
                return oldValue;
            }
        }
        return null;
    }

    @Override
    public void clear() {
//        for(LinkedList<EntrySet> bucket : buckets){
//            if(bucket == null){
//                continue;
//            }
//            bucket.clear();
//        }
        buckets = new LinkedList[SIZE];
    }

    @Override
    public V put(K key, V value) {
        EntrySet pair = new EntrySet(key,value);
        int index = Math.abs(key.hashCode()) % SIZE;
        V oldValue = null;
        if(buckets[index] == null){
            buckets[index] = new LinkedList<EntrySet>();
            buckets[index].add(pair);
            return null;
        }
        for(EntrySet e : buckets[index]){
            if(e.getKey().equals(pair.getKey())){
                oldValue = e.getValue();
                e.setValue(pair.getValue());
                break;
            }
        }
        if(oldValue == null){
            buckets[index].add(pair);
        }
        return oldValue;
    }

    @Override
    public V get(Object key) {
        int index = Math.abs(key.hashCode()) % SIZE;
        if(buckets[index] == null){
            return null;
        }
        for(EntrySet e : buckets[index]){
            if(e.getKey().equals(key)){
                return e.getValue();
            }
        }
        return null;
    }

    private class EntrySet implements Map.Entry<K,V>{
        private K key;
        private V value;

        EntrySet(K key,V value){
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = value;
            this.value = value;
            return oldValue;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == this) {
                return true;
            }
            if(obj instanceof Map.Entry) {
                Map.Entry e = (Map.Entry) obj;
                return Objects.equals(e.getKey(),this.key) && Objects.equals(e.getValue(),this.value);
            }
            return false;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}
