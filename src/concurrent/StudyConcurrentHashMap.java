package concurrent;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kanyuxia on 2017/5/21.
 */
public class StudyConcurrentHashMap {
    public static void main(String[] args) throws Exception {

    }
}


class ConcurrentHashMap<K, V> extends AbstractMap<K, V> {

    // ------------------------------Construct method
    @SuppressWarnings("unchecked")
    public ConcurrentHashMap() {
        this.segments = new Segment[DEFAULT_CONCURRENCY_LEVEL];
        for (int i = 0; i < segments.length; i++) {
            segments[i] = new Segment<>(DEFAULT_INITIAL_CAPACITY);
        }
    }

    // -----------------------------Field
    /**
     * 散列映射表的默认初始容量为 16，即初始默认为16个桶
     * 在构造函数中没有指定这个参数时，使用本参数
     */
    static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
     * 散列表的默认并发级别为 16。该值表示当前更新线程的估计数
     * 在构造函数中没有指定这个参数时，使用本参数
     */
    static final int DEFAULT_CONCURRENCY_LEVEL = 16;

    static final int SEGMENT_SHIFT = 28;

    static final int SEGMENT_MASK = 15;



    /**
     * 由Segment对象组成的数组
     */
    final Segment<K, V>[] segments;

    transient Set<Entry<K,V>> entrySet;

    // --------------------------Some util methods
    /**
     * 哈希再散列，减少哈希冲突:JDK1.6算法
     * @param h key的哈希值
     * @return 再散列后的哈希值
     */
    private int hash(int h) {
        h += (h <<  15) ^ 0xffffcd7d;
        h ^= (h >>> 10);
        h += (h <<   3);
        h ^= (h >>>  6);
        h += (h <<   2) + (h << 14);
        return h ^ (h >>> 16);
    }

    /**
     * 通过key散列后的哈希值来得到segments数组中对应的 Segment
     * @param hash key再散列后的哈希值
     * @return Segment segment
     */
    private Segment<K,V> segmentFor(int hash) {
        return segments[(hash >>> SEGMENT_SHIFT) & SEGMENT_MASK];
    }

    // -------------------------------Some public methods
    @Override
    public V get(Object key) {
        if (key == null) {
            throw new NullPointerException();
        }
        int hash = hash(key.hashCode());
        return segmentFor(hash).get(key, hash);
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        int hash = hash(key.hashCode());
        return segmentFor(hash).put(key, value, hash);
    }

    @Override
    public boolean remove(Object key, Object value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        int hash = hash(key.hashCode());
        return segmentFor(hash).remove(key, value, hash) != null;
    }

    @Override
    public V remove(Object key) {
        if (key == null) {
            throw new NullPointerException();
        }
        int hash = hash(key.hashCode());
        V value = segmentFor(hash).get(key, hash);
        return segmentFor(hash).remove(key, value, hash);
    }

    @Override
    public int size() {
        final Segment<K,V>[] segments = this.segments;
        long sum = 0;
        long check = 0;
        int[] mc = new int[segments.length];
        // Try a few times to get accurate count. On failure due to
        // continuous async changes in table, resort to locking.
        for (int k = 0; k < 2; ++k) {
            check = 0;
            sum = 0;
            int mcsum = 0;
            for (int i = 0; i < segments.length; ++i) {
                sum += segments[i].count;
                mcsum += mc[i] = segments[i].modCount;
            }
            if (mcsum != 0) {
                for (int i = 0; i < segments.length; ++i) {
                    check += segments[i].count;
                    if (mc[i] != segments[i].modCount) {
                        check = -1; // force retry
                        break;
                    }
                }
            }
            if (check == sum)
                break;
        }
        if (check != sum) { // Resort to locking all segments
            sum = 0;
            for (int i = 0; i < segments.length; ++i)
                segments[i].lock();
            for (int i = 0; i < segments.length; ++i)
                sum += segments[i].count;
            for (int i = 0; i < segments.length; ++i)
                segments[i].unlock();
        }
        if (sum > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        else
            return (int)sum;
    }

    @Override
    public void clear() {
        for (int i = 0; i < segments.length; i++)
            segments[i].clear();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K,V>> es = entrySet;
        return (es != null) ? es : (entrySet = new EntrySet());
    }


    // ----------------------------Inner class
    static class Segment<K, V> extends ReentrantLock implements Serializable {
        // --------------------Construct method
        @SuppressWarnings("unchecked")
        Segment(int initialCapacity) {
            table = new Node[initialCapacity];
        }
        // ---------------------Field
        private static final long serialVersionUID = 7249069246763182397L;
        // 该Segment中Node的数目
        volatile int count;
        // 该Segment中Node[]结构的变化次数
        int modCount;
        // 存放Node的数组
        volatile Node<K, V>[] table;


        // ----------------------Some methods
        /**
         * 根据key再散列后的哈希值,返回segment中第一个链表节点
         * @param hash key再散列后的哈希值
         * @return 返回segment中第一个链表节点
         */
        Node<K, V> getFirst(int hash) {
            return table[hash & (table.length - 1)];
        }

        V get(Object key, int hash) {
            // 读volatile变量，获取主内存中共享变量
            if (count != 0) {
                Node<K, V> node = getFirst(hash);
                while (node != null) {
                    if (key.equals(node.key) && node.hash == hash) {
                        V value = node.getValue();
                        if (value != null) {
                            return value;
                        }
                        // 如果value为null，说明发生了重排序，加锁后重读
                        return readValueUnderLock(node); // recheck
                    }
                    node = node.next;
                }
                return null;
            }
            return null;
        }

        V readValueUnderLock(Node<K,V> e) {
            lock();             // 获取锁
            try {
                return e.value;
            } finally {
                unlock();       // 释放锁
            }
        }

        V put(K key, V value, int hash) {
            lock();             // 获取锁
            try {
                // 这里c是用来验证是否超过容量的，我没有写扩容机制。
                // 虽然看起来这里如果没有扩容机制的话，就可以不使用本地变量c，
                // 但是这里必须使用本地变量把count的值加一，不能直接count++，
                // 因为只有单个volatile变量的写才有原子性，如果是volatile++,则不具有原子性。
                // 而且我们要先读volatile变量才能获取主内存中的共享变量。
                int c = count;
                c++;
                Node<K, V> node = getFirst(hash);
                Node<K, V> e = null;
                while (node != null) {
                    if (key.equals(node.key) && value.equals(node.value) && hash == node.hash) {
                        e = node;
                    }
                    node = node.next;
                }
                // 如果该key、value存在，则修改后返回原值，且结果没有变化。
                if (e != null) {
                    V oldValue = e.value;
                    e.value = value;
                    return oldValue;
                }
                // 该key、value不存在，则添加一个新节点添加到链表头：
                // 这样的话原链表结构不会发生变化，使得其他读线程正常遍历这个链表。
                Node<K, V> first = getFirst(hash);
                e = new Node<>(hash, first, key, value);
                table[hash & (table.length - 1)] = e;
                // 因为添加新节点了，所以modCount要加1
                modCount++;
                // count数量加1：写volatile变量使得该修改对所有读都有效。
                count = c;
                return value;
            } finally {
                unlock();       // 释放锁
            }
        }

        V remove(Object key, Object value, int hash) {
            lock();     // 获取锁
            try {
                // 与put方法中意义相似，就解释了
                int c = count;
                c--;
                Node<K, V> node = getFirst(hash);
                Node<K, V> e = null;
                while (node != null) {
                    if (node.hash == hash && node.key.equals(key) && node.value.equals(value)) {
                        e = node;
                    }
                    node = node.next;
                }
                // 该key、value不存在，返回null
                if (e == null) {
                    return null;
                }
                // 该key、value存在，需要删除一个节点
                // 这里的删除没有真正意义上的删除，它新建了一个链表
                // 使得原链表结果没有发生变化，其他读线程正常遍历这个链表
                V oldValue = e.value;
                Node<K, V> newFirst = e.next;
                Node<K, V> first = getFirst(hash);
                while (first != e) {
                    newFirst = new Node<>(first.hash, newFirst, first.key, first.value);
                    first = first.next;
                }
                table[hash & (table.length - 1)] = newFirst;
                // 因为删除了一个节点，所以modCount要加1
                modCount++;
                // 写volatile变量使得该修改对所有读都有效。
                count = c;
                return oldValue;
            } finally {
                unlock();           // 释放锁
            }
        }

        void clear() {
            if (count != 0) {
                lock();             // 获取锁
                try {
                    Node<K,V>[] tab = table;
                    for (int i = 0; i < tab.length ; i++)
                        tab[i] = null;
                    // 因为删除了一个节点，所以modCount要加1
                    ++modCount;
                    // 写volatile变量使得该修改对所有读都有效。
                    count = 0;
                } finally {
                    unlock();       // 释放锁
                }
            }
        }
    }

    static class Node<K, V> implements Entry<K, V> {
        final int hash;
        final Node<K, V> next;
        final K key;
        volatile V value;

        public Node(int hash, Node<K, V> next, K key, V value) {
            this.hash = hash;
            this.next = next;
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
    }



    final class EntrySet extends AbstractSet<Entry<K,V>> {
        public Iterator<Entry<K,V>> iterator() {
            return new EntryIterator();
        }
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Entry<?,?> e = (Entry<?,?>)o;
            V v = ConcurrentHashMap.this.get(e.getKey());
            return v != null && v.equals(e.getValue());
        }
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Entry<?,?> e = (Entry<?,?>)o;
            return ConcurrentHashMap.this.remove(e.getKey(), e.getValue());
        }
        public int size() {
            return ConcurrentHashMap.this.size();
        }
        public void clear() {
            ConcurrentHashMap.this.clear();
        }
    }

    final class EntryIterator extends HashIterator implements Iterator<Entry<K,V>> {
        public Entry<K,V> next() {
            Node<K,V> e = super.nextEntry();
            return new WriteThroughEntry(e.key, e.value);
        }
    }

    final class WriteThroughEntry
            extends SimpleEntry<K,V>
    {
        WriteThroughEntry(K k, V v) {
            super(k,v);
        }

        /**
         * Set our entry's value and write through to the map. The
         * value to return is somewhat arbitrary here. Since a
         * WriteThroughEntry does not necessarily track asynchronous
         * changes, the most recent "previous" value could be
         * different from what we return (or could even have been
         * removed in which case the put will re-establish). We do not
         * and cannot guarantee more.
         */
        public V setValue(V value) {
            if (value == null) throw new NullPointerException();
            V v = super.setValue(value);
            ConcurrentHashMap.this.put(getKey(), value);
            return v;
        }
    }

    /* ---------------- Iterator Support -------------- */

    abstract class HashIterator {
        int nextSegmentIndex;
        int nextTableIndex;
        Node<K,V>[] currentTable;
        Node<K, V> nextEntry;
        Node<K, V> lastReturned;

        HashIterator() {
            nextSegmentIndex = segments.length - 1;
            nextTableIndex = -1;
            advance();
        }

        public boolean hasMoreElements() { return hasNext(); }

        final void advance() {
            if (nextEntry != null && (nextEntry = nextEntry.next) != null)
                return;

            while (nextTableIndex >= 0) {
                if ( (nextEntry = currentTable[nextTableIndex--]) != null)
                    return;
            }

            while (nextSegmentIndex >= 0) {
                Segment<K,V> seg = segments[nextSegmentIndex--];
                if (seg.count != 0) {
                    currentTable = seg.table;
                    for (int j = currentTable.length - 1; j >= 0; --j) {
                        if ( (nextEntry = currentTable[j]) != null) {
                            nextTableIndex = j - 1;
                            return;
                        }
                    }
                }
            }
        }

        public boolean hasNext() { return nextEntry != null; }

        Node<K,V> nextEntry() {
            if (nextEntry == null)
                throw new NoSuchElementException();
            lastReturned = nextEntry;
            advance();
            return lastReturned;
        }

        public void remove() {
            if (lastReturned == null)
                throw new IllegalStateException();
            ConcurrentHashMap.this.remove(lastReturned.key);
            lastReturned = null;
        }
    }
}
