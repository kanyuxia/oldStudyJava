package test4;

/**
 * Created by king_ant on 2016/9/24.
 */
public class extendsFunction {
    public static void main(String[] args){
        Circle circle = new Circle();
    }
}

class Simple{
    Simple(){
        System.out.println("Simple");
    }
}
class Circle extends Shape{
    Circle(){
        System.out.println("Circle");
    }
    void fun(){
        System.out.println("Circle fun");
    }
}

class Shape{
    Shape(){
        System.out.println("Shape");
        fun();
    }
    void fun(){
        System.out.println("Shape fun");
    }
}

