package io;

import java.io.*;

/**
 * Created by kanyuxia on 2017/3/4.
 */
public class Test27 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        Book book = new Book("Think In Java", new People("马荧"));
//        String str = "Book Storage";
//        System.out.println(str);
//        System.out.println(book);
//        // serialize
//        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("E:\\java\\book.dat"));
//        out.writeObject(str);
//        out.writeObject(book);
//
//        // deserialize
//        ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\java\\book.dat"));
//        String str1 = (String) in.readObject();
//        Book book1 = (Book) in.readObject();
//
//        System.out.println(str1);
//        System.out.println(book1);

        Fruit fruit = new Fruit();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("E:\\java\\food.dat"));
        out.writeObject(fruit);
        out.writeObject(fruit);

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\java\\food.dat"));
        Fruit read = (Fruit) in.readObject();
        System.out.println(read);
    }
}
class Book implements Serializable {
    private static final long serialVersionUID = 6779810723508552970L;
    private String name;
    private People people;

    Book(String name, People people){
        System.out.println("Construtor function");
        this.name = name;
        this.people = people;
    }

    @Override
    public String toString() {
        return "name: " + name + "  peopleName: " + people.getName();
    }
}
class People implements Serializable{
    private String name;

    public String getName(){
        return name;
    }

    People(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

class Food {
    Food(){
        System.out.println("Food Constructor");
    }
}

class Fruit extends Food implements Serializable {
    private static final long serialVersionUID = -5197179681015030938L;
    Fruit(){
        System.out.println("Fruit Construtor");
    }
}