package container;

import java.util.*;

/**
 * Created by kanyuxia on 2017/1/16.
 */
public class Test17 {
    public static void main(String[] args){
        List<String> words = new ArrayList<String>(Arrays.asList("A B C D E F G H I F I A B Z R H P K A N B".split(" ")));
        DfMap<String,Integer> map = new DfMap<String,Integer>();
        for(String s : words){
            Integer oldValue = map.get(s);
            map.put(s,oldValue == null ? 1 : oldValue + 1);
        }
        for(String s : map.keySet()){
            System.out.println(s+"="+map.get(s));
        }
    }
}

class DfMap<K,V> implements Map<K,V> {
    private List<K> keyList = new ArrayList<K>();
    private List<V> valueList = new ArrayList<V>();

    @Override
    public int size() {
        return keyList.size();
    }

    @Override
    public boolean isEmpty() {
        return keyList.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return keyList.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return valueList.contains(value);
    }

    @Override
    public V get(Object key) {
        if (!keyList.contains(key)) {
            return null;
        }
        return valueList.get(keyList.indexOf(key));
    }

    @Override
    public V put(K key, V value) {
        V oldValue = get(key);
        if (!keyList.contains(key)) {
            keyList.add(key);
            valueList.add(value);
        } else {
            valueList.set(keyList.indexOf(key), value);
        }
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        if (!keyList.contains(key)) {
            return null;
        }
        int index = keyList.indexOf(key);
        keyList.remove(key);
        return valueList.remove(index);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (K key : m.keySet()) {
            keyList.add(key);
            valueList.add(m.get(key));
        }
    }

    @Override
    public void clear() {
        valueList.clear();
        valueList.clear();
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<K>(keyList);
        return set;
    }

    @Override
    public Collection<V> values() {
        return valueList;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<Entry<K, V>>();
        Iterator<K> kIterator = keyList.iterator();
        Iterator<V> vIterator = valueList.iterator();
        while (kIterator.hasNext()) {
            set.add(new EntrySet(kIterator.next(), vIterator.next()));
        }
        return set;
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
            return Objects.hashCode(this.key) ^ Objects.hashCode(this.value);
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
            return key + "=" + value;
        }
    }
}
