package container;

import java.util.*;

import static java.util.Arrays.copyOf;

/**
 * Created by kanyuxia on 2017/2/28.
 */
public class StudyArrayList {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        fill(list);
        list.add(1,5);
        list.set(1,10);
        list.remove(new Integer(2));
        for(int i = 0; i < list.size(); i++){
            System.out.print(list.get(i) + "\t");
        }
        System.out.println();
        for(Integer integer : list){
            System.out.print(integer + "\t");
        }
        System.out.println("size:" + list.size());
    }
    private static void fill(List<Integer> list) {
        for(int i = 0; i < 100; i++){
            list.add(i);
        }
    }

}
class ArrayList<E> extends AbstractList<E> implements List<E> {
    // 默认容量
    private static final int DEFAULT_CAPACITY = 10;
    // 存放元素数量
    private int size;
    // 存放元素的数组
    private Object[] elementData;

    ArrayList(){
        elementData = new Object[DEFAULT_CAPACITY];
    }

    ArrayList(int initCapacity){
        if(initCapacity > 0){
            elementData = new Object[initCapacity];
        }else {
            throw new IllegalArgumentException("Illegal Capacity" + initCapacity);
        }
    }

    ArrayList(Collection< ? extends E> c){
        elementData = c.toArray();
        this.size = elementData.length;
        if(size != 0){
            if(elementData.getClass() != Object[].class){
                elementData = copyOf(elementData,size,Object[].class);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        check(index);
        return (E) elementData[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E e) {
        ensureCapacity(size + 1);
        elementData[size] = e;
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        check(index);
        ensureCapacity(size + 1);
        System.arraycopy(elementData,index,elementData,index+1,size - index);
        elementData[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        for(int index = 0; index < size; index++){
            if(Objects.equals(o,elementData[index])){
                int numMoved = size - index - 1;
                if(numMoved > 0){
                    System.arraycopy(elementData, index + 1, elementData, index, numMoved);
                }
                elementData[size] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        check(index);
        E oldValue = (E) elementData[index];
        int numMoved = size - index - 1;
        if(numMoved > 0){
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[size] = null;
        size--;
        return oldValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        check(index);
        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    // 验证索引
    private void check(int index){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("Index:" + index + "Size:" + size);
        }
    }
    // 验证+扩容
    private void ensureCapacity(int minCapacity){
        // 验证
        if(minCapacity > elementData.length){
            // 扩容
            int oldCapacity = elementData.length;
            int newCapacity = (int) (oldCapacity * 1.5);
            elementData =  Arrays.copyOf(elementData, newCapacity);
        }
    }

}
