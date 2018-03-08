package container.singlelist;

/**
 * Created by kanyuxia on 2017/1/15.
 */
public interface SListIterator<E> {
    boolean hasNext();
    E next();
    void remove();
    void add(E e);
}
