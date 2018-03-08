package container.singlelist;

import java.util.*;

/**
 * Created by kanyuxia on 2017/1/16.
 */
public class Test15 {
    public static void main(String[] args){
        List<String> words = new ArrayList<String>(Arrays.asList("A B C D E F G H I F I A B Z R H P K A N B".split(" ")));
        SlowMap<String,Integer> map = new SlowMap<String,Integer>();
        for(String s : words){
            Integer oldValue = map.get(s);
            map.put(s,oldValue == null ? 1 : oldValue + 1);
        }
        System.out.println(map);
    }
}

class SlowMap<K,V> extends AbstractMap<K,V>{
    private List<K> keys = new ArrayList<K>();
    private List<V> values = new ArrayList<V>();

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K,V>> set = new HashSet<Entry<K, V>>();
        Iterator<K> keyIterator = keys.iterator();
        Iterator<V> valueIterator = values.iterator();
        while(keyIterator.hasNext()){
            set.add(new MapEntry<K, V>(keyIterator.next(),valueIterator.next()));
        }
        return set;
    }

    public V put(K key,V value){
        V oldValue = get(key);
        if(!keys.contains(key)){
            keys.add(key);
            values.add(value);
        }else {
            values.set(keys.indexOf(key), value);
        }
        return oldValue;
    }

    public V get(Object key){
        if(!keys.contains(key)){
            return null;
        }
        return values.get(keys.indexOf(key));
    }

}

class MapEntry<K,V> implements Map.Entry<K,V>{
    private K key;
    private V value;

    MapEntry(K key,V value){
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
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    @Override
    public int hashCode() {
        return (key==null ? 0 : key.hashCode()) ^
                (value==null ? 0 : value.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof MapEntry)){
            return false;
        }
        MapEntry me = (MapEntry)obj;
        return (key == null ? me.getKey() == null : key.equals(me.getKey())) &&
                (value == null ? me.getValue() == null : value.equals(me.getValue()));
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}

