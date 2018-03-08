package container;

import java.util.Iterator;

/**
 * Created by kanyuxia on 2017/1/14.
 */
public class StudyIterable {
    public static void main(String[] args){
        ArrayIterable arrayIterable = new ArrayIterable();
        System.out.println("foreach way one");
        for(Character character:arrayIterable){
            System.out.print(character+"\t");
        }
        System.out.println();
        System.out.println("foreach way two");
        for (Character character:arrayIterable.order()){
            System.out.print(character+"\t");
        }
        System.out.println();
        System.out.println("-----------------我的萌萌的分割线-------------------");
        DefineIterable<Integer> defineIterable = new DefineIterable<Integer>();
        defineIterable.add(1);
        defineIterable.add(2);
        for(Integer integer : defineIterable){
            System.out.print(integer+"\t");
        }
    }
}
class ArrayIterable implements Iterable<Character>{
    private char[] chars = "hello world".toCharArray();
    @Override
    public Iterator<Character> iterator() {
        return new Iterator<Character>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < chars.length;
            }
            @Override
            public Character next() {
                return chars[index++];
            }
        };
    }

    public Iterable<Character> order(){
        return new Iterable<Character>() {
            @Override
            public Iterator<Character> iterator() {
                return new Iterator<Character>() {
                    private int index = chars.length -1;
                    @Override
                    public boolean hasNext() {
                        return index >= 0;
                    }
                    @Override
                    public Character next() {
                        return chars[index--];
                    }
                };
            }
        };
    }
}

class DefineIterable<E> implements Iterable<E> {
    private Object[] elementDate;
    private int cursor = 0;
    private int index = 0;
    DefineIterable(){
        elementDate = new Object[10];
    }
    DefineIterable(int size){
        elementDate = new Object[size];
    }
    @Override
    @SuppressWarnings("unchecked")
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return cursor < elementDate.length;
            }

            @Override
            public E next() {
                return (E) elementDate[cursor++];
            }
        };
    }

    public void add(E e){
        if(index > elementDate.length){
            throw new IndexOutOfBoundsException();
        }
        elementDate[index++] = e;
    }
}