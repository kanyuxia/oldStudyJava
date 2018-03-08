package container;

import java.util.*;

/**
 * Created by kanyuxia on 2017/3/2.
 */
public class StudyHashMap {
    public static void main(String[] args){
        Map<String,Integer> map = new HashMap<>();
        String[] words = "a b c d e f g h i j k l m n a d i w x z z a n b q".split(" ");
        for(String word : words){
            Integer integer = map.get(word);
            map.put(word,integer == null ? 1 : integer + 1);
        }
        System.out.println(map);
    }
}

class Book {
    private int weight;
    private int prive;
    Book(int weight, int prive){
        this.weight = weight;
        this.prive = prive;
    }

    public int getPrive() {
        return prive;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int hashCode() {
        return weight;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        if(obj instanceof Book){
            Book book = (Book) obj;
            return Objects.equals(book.getPrive(),this.prive);
        }
        return false;
    }

    @Override
    public String toString() {
        return weight + "-" + prive;
    }
}

class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>{
    private static final int DEFAULT_CAPACITY = 16;  // 哈希数组默认大小
    private int size;  // HashMap存放元素个数
    private Node[] table;  // 哈希数组

    HashMap(){
        table = new Node[DEFAULT_CAPACITY];
    }

    @Override
    public V put(K key, V value) {
        // 找到该key对应的哈希数组下标
        int index = hash(key);
        // 该链表没有元素
        if(table[index] == null){
            table[index] = new Node<>(index, key, value, null);
            size++;
            return null;
        }
        @SuppressWarnings("unchecked")
        Node<K, V> node = table[index];
        Node<K, V> prev = node;
        // 遍历该链表
        while(node != null){
            // 找到key,则替换value并返回原value
            if(Objects.equals(node.getKey(),key)){
                V oldValue = node.getValue();
                node.setValue(value);
                return oldValue;
            }
            prev = node;
            node = node.next;
        }
        // 未找到,则加入链表
        prev.next = new Node<>(index, key, value, null);
        size++;
        return null;
    }

    @Override
    public V get(Object key) {
        // 找到该key对应的哈希数组下标
        int index = hash(key);
        // 该链表没有元素
        if(table[index] == null){
            return null;
        }
        @SuppressWarnings("unchecked")
        Node<K, V> node = table[index];
        // 遍历该链表
        do {
            if(Objects.equals(node.getKey(),key)){
                return node.getValue();
            }
            node = node.next;
        } while (node != null);
        return null;
    }

    @Override
    public V remove(Object key) {
        // 找到该key对应的哈希数组下标
        int index = hash(key);
        @SuppressWarnings("unchecked")
        Node<K, V> node = table[index];
        // 该链表没有元素
        if(node == null){
            return null;
        }
        // 该链表只有一个元素
        if(node.next == null){
            if(Objects.equals(node.getKey(),key)){
                V oldValue = node.getValue();
                table[index] = null;
                size--;
                return oldValue;
            }
            return null;
        }
        // 遍历该链表
        Node<K, V> prev = node;
        node = node.next;
        while(node != null){
            if(Objects.equals(node.getKey(),key)){
                prev.next = node.next;
                size--;
                return node.getValue();
            }
            node = node.next;
            prev = prev.next;
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        for(Node<K, V> node: table ){
            while(node != null){
                set.add(node);
                node = node.next;
            }
        }
        return set;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * 计算key的哈希值，然后Hash算法(取模)求出该key对应的数组下标
     */
    public int hash(Object key){
        return Objects.hashCode(key) % table.length;
    }

    static class Node<K, V> implements Map.Entry<K, V>{
        final int hash;  // 经过hash运算得出的数组下标
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
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
        public boolean equals(Object obj) {
            if(obj == this){
                return true;
            }
            if(obj instanceof Map.Entry<?, ?>){
                Map.Entry<?, ?> entry = (Map.Entry<?, ?>) obj;
                return Objects.equals(entry.getKey(),this.key) && Objects.equals(entry.getValue(),this.value);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.key) ^ Objects.hashCode(this.value);
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
}

