package container.singlelist;

import java.util.NoSuchElementException;

/**
 * Created by kanyuxia on 2017/1/15.
 */
public class SList<E> {
    // 代表头结点
    private final Node<E> header = new Node<E>(null,null);


    @Override
    public String toString() {
        return super.toString();
    }

    public static class Node<E>{
        E item;
        Node<E> next;

        Node(E item,Node<E> next){
            this.item = item;
            this.next = next;
        }
    }
    // 返回SListIterator,以便迭代该list
    public SListIterator<E> SListIterator(){
       return new SListIteratorImpl();
    }

    // 实现SListIterator类
    private class SListIteratorImpl implements SListIterator<E>{
        Node<E> lastReturned = header;
        Node<E> next = header.next;
        @Override
        public boolean hasNext() {
            return next != header;
        }

        @Override
        public E next() {
            if(next == header){
                throw new NoSuchElementException();
            }
            lastReturned = next;
            next = next.next;
            return lastReturned.item;
        }

        @Override
        public void remove() {
            if(lastReturned == header){
                throw new IllegalStateException();
            }
            for(Node<E> curr = header; ;curr = curr.next){
                if(curr.next == lastReturned){
                    curr.next = lastReturned.next;
                    break;
                }
            }
            lastReturned = header;
        }

        @Override
        public void add(E e) {
            lastReturned = header;
            Node<E> newNode = new Node<E>(e,next);
            // list is Empty
            if(header.next == header){
                header.next = newNode;
            }else {
                // Find an element before the one pointed by 'next'
                for (Node<E> curr = header; ;curr = curr.next){
                    if(curr.next == next){
                        curr.next = newNode;
                        break;
                    }
                }
            }
        }
    }
}
