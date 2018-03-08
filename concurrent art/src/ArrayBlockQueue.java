import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kanyuxia on 2017/6/1.
 */
public class ArrayBlockQueue<E> {
    private Object[] elements;
    private ReentrantLock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();
    /** items index for next take, poll, peek or remove */
    private int takeIndex;
    /** items index for next put, offer, or add */
    private int putIndex;
    /** Number of elements in the queue */
    private int count;

    ArrayBlockQueue() {
        this(10);
    }

    ArrayBlockQueue(int initialSize) {
        elements = new Object[initialSize];
    }

    public void put(E e) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }
        lock.lock();
        try {
            while (count == elements.length) {
                notFull.await();
            }
            addQueue(e);
        } finally {
            lock.unlock();
        }
    }

    private void addQueue(E e) {
        final Object[] elements = this.elements;
        elements[putIndex] = e;
        if (++putIndex == elements.length) {
            putIndex = 0;
        }
        count++;
        notEmpty.signal();
    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            return removeQueue();
        } finally {
            lock.unlock();
        }
    }

    @SuppressWarnings("unchecked")
    private E removeQueue() {
        final Object[] elements = this.elements;
        E element = (E) elements[takeIndex];
        elements[takeIndex] = null;
        if (++takeIndex == elements.length)
            takeIndex = 0;
        count--;
        notFull.signal();
        return element;
    }
}
