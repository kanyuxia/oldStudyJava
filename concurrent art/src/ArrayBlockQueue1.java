/**
 * Created by kanyuxia on 2017/6/1.
 */
public class ArrayBlockQueue1<E> {
    private Object[] elements;
    private final Object notFull = new Object();
    private final Object notEmpty = new Object();
    /** items index for next take, poll, peek or remove */
    private int takeIndex;
    /** items index for next put, offer, or add */
    private int putIndex;
    /** Number of elements in the queue */
    private int count;

    ArrayBlockQueue1() {
        this(10);
    }

    ArrayBlockQueue1(int initialSize) {
        elements = new Object[initialSize];
    }

    public void put(E e) throws InterruptedException {
        synchronized (notFull) {
            while (count == elements.length) {
                notFull.wait();
            }
        }
        synchronized (notEmpty) {
            final Object[] elements = this.elements;
            elements[putIndex] = e;
            if (++putIndex == elements.length) {
                putIndex = 0;
            }
            count++;
            notEmpty.notify();
        }
    }

    @SuppressWarnings("unchecked")
    public E take() throws InterruptedException {
        synchronized (notEmpty) {
            while (count == 0) {
                notEmpty.wait();
            }
        }
        synchronized (notFull) {
            final Object[] elements = this.elements;
            E e = (E) elements[takeIndex];
            elements[takeIndex] = null;
            if (++takeIndex == elements.length) {
                takeIndex = 0;
            }
            count--;
            notFull.notify();
            return e;
        }
    }
}
