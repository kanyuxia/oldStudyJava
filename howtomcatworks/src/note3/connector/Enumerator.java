package note3.connector;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kanyuxia on 2017/4/27.
 */
public class Enumerator<E> implements Enumeration<E> {
    private Iterator<E> iterator = null;

    public Enumerator(Collection<E> collection) {
        this(collection.iterator());
    }

    public Enumerator(Iterator<E> iterator) {
        super();
        this.iterator = iterator;
    }

    public Enumerator(Map<?, E> map) {
        this(map.values().iterator());
    }

    @Override
    public boolean hasMoreElements() {
        return iterator.hasNext();
    }

    @Override
    public E nextElement() {
        return (E) iterator.next();
    }
}
