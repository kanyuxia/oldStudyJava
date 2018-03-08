package array;

/**
 * Created by kanyuxia on 2017/1/13.
 */
public class People implements Comparable<People>{
    private int age;
    @Override
    public int compareTo(People o) {
        return this.age>o.age ? 1 : (this.age==o.age ? 0 : -1);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    People(){

    }
    People(int age){
        this.age = age;
    }

    @Override
    public String toString() {
        return String.valueOf(this.age);
    }
}
