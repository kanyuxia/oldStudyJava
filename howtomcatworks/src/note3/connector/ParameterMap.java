package note3.connector;

import java.util.HashMap;
import java.util.Map;

/**
 * Extended implementation of <strong>HashMap</strong> that includes a
 * <code>locked</code> property.  This class can be used to safely expose
 * Catalina internal parameter map objects to user classes without having
 * to clone them in order to avoid modifications.  When first created, a
 * <code>ParmaeterMap</code> instance is not locked.
 *
 * 存放Http参数：Query String or Form Data
 * Created by kanyuxia on 2017/4/27.
 */
public class ParameterMap<K, V> extends HashMap<K, V> {
    private static final long serialVersionUID = -7752723814131658494L;

    /**
     * 一些构造函数
     */
    public ParameterMap() {
        super();
    }

    public ParameterMap(int initialCapacity) {
        super(initialCapacity);
    }

    public ParameterMap(int initialCapaticity, float loadFactory) {
        super(initialCapaticity, loadFactory);
    }

    public ParameterMap(Map<K, V> map) {
        super(map);
    }

    /**
     * The current lock state of this parameter map.
     * ParameterMap是否锁住。
     */
    private boolean locked = false;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public void clear() {
        if (locked) {
            throw new IllegalStateException("parameterMap.locked");
        }
        super.clear();
    }

    @Override
    public V put(K key, V value) {
        if (locked) {
            throw new IllegalStateException("parameterMap.locked");
        }
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (locked) {
            throw new IllegalStateException("parameterMap.locked");
        }
        super.putAll(m);
    }

    @Override
    public boolean remove(Object key, Object value) {
        if (locked) {
            throw new IllegalStateException("parameterMap.locked");
        }
        return super.remove(key, value);
    }
}
