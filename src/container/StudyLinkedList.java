package container;


/**
 * Created by kanyuxia on 2017/2/28.
 */
public class StudyLinkedList {
    public static void main(String[] args){
    }
}

class LinkedList<E> {
    // LinkedList包含元素的数目
    private int size;
    // 头结点
    private Node<E> first;
    // 尾节点
    private Node<E> last;

    /**
     * 插入头部
     */
    private void linkFirst(E e){
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if(f == null){
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }

    /**
     * 插入尾部
     */
    private void linkLast(E e){
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if(l == null){
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    /**
     * 插入到某个非空结点succ之前
     */
    private void linkBefore(E e, Node<E> succ){
        final Node<E> pred = succ.prev;
        final Node<E> newNode = new Node<>(pred, e, succ);
        succ.prev = newNode;
        if(pred == null){
            last = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    /**
     * 删除不为空的头结点f
     */
    private E unlinkFirst(Node<E> f){
        final E element = f.item;
        final Node<E> next = f.next;
        f.next = null;
        f.item = null;  // help GC
        first = next;
        if(next == null){
            last = null;
        } else {
            next.prev = null;
        }
        size--;
        return element;
    }

    /**
     *  删除不为空的尾节点l
     */
    private E unlinkLast(Node<E> l){
        final E element = l.item;
        final Node<E> prev = l.prev;
        l.prev = null;
        l.item = null;  // help GC
        last = prev;
        if(prev == null){
            first = null;
        } else {
            prev.next = null;
        }
        size--;
        return element;
    }

    /**
     * 删除不为空的节点x
     */
    private E unlink(Node<E> x){
        final E element = x.item;
        final Node<E> prev = x.prev;
        final Node<E> next = x.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null; //help GC
        size--;
        return element;
    }
    private static class Node<E>{
        Node<E> prev;
        E item;
        Node<E> next;
        Node(Node<E> prev, E element, Node<E> next){
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }
}
