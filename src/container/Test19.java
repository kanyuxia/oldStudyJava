package container;

import java.util.*;
import java.util.LinkedList;

/**
 * Created by kanyuxia on 2017/1/17.
 */
public class Test19 {
    public static void main(String[] args){
        List<String> words = new ArrayList<>(Arrays.asList("A B C D E F G H I J A B D D E A B C".split(" ")));
        SimpleHashMap<String,Integer> map = new SimpleHashMap<>();
        System.out.println(words);
        for(String word : words){
            Integer count = map.get(word);
            map.put(word,count == null ? 1 : count + 1);
        }
        System.out.println(map);
    }
}

class SimpleHashMap<K,V> extends AbstractMap<K,V> {
    // 数组的大小
    private static final int SIZE = 997;
    // 数组
    @SuppressWarnings("unchecked")
    private LinkedList<EntrySet>[] buckets = new LinkedList[SIZE];

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K,V>> set = new HashSet<>();
        for(LinkedList<EntrySet> bucket : buckets){
            if(bucket == null){
                continue;
            }
            set.addAll(bucket);
        }
        return set;
    }

    @Override
    public V put(K key, V value) {
        EntrySet entrySet = new EntrySet(key,value);
        int index = Math.abs(key.hashCode()) % SIZE;
        V oldValue = null;
        if(buckets[index] == null){
            buckets[index] = new LinkedList<EntrySet>();
            buckets[index].add(entrySet);
            return null;
        }
        List<EntrySet> list = buckets[index];
        for(EntrySet e : list){
            if(e.getKey().equals(entrySet.getKey())){
                oldValue = e.getValue();
                e.setValue(entrySet.getValue());
                break;
            }
        }
        if(oldValue == null){
            list.add(entrySet);
        }
        return oldValue;
    }

    @Override
    public V get(Object key) {
        int index = Math.abs(key.hashCode()) % SIZE;
        if(buckets[index] == null){
            return null;
        }
        LinkedList<EntrySet> list = buckets[index];
        for(EntrySet  e : list){
            if(e.getKey().equals(key)){
                return e.getValue();
            }
        }
        return null;
    }

    private final class EntrySet implements Map.Entry<K, V> {

        private K key;
        private V value;

        EntrySet(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }
        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == this){
                return true;
            }
            if(obj instanceof Map.Entry){
                Map.Entry<?,?> e = (Map.Entry<?,?>)obj;
                return Objects.equals(e.getKey(),this.key) && Objects.equals(e.getValue(),this.value);
            }
            return false;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
