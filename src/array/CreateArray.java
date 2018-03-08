package array;

import java.util.Arrays;

/**
 * Created by kanyuxia on 2017/1/12.
 */
public class CreateArray {
    public static void main(String[] args){
        CreateArray createArray = new CreateArray();
        createArray.recieveArray(new Animal[]{new Animal(),new Animal()});
        Animal[] animals = {new Animal(),new Animal()};
        int[] ints = new int[]{1,2};
        System.out.println(Arrays.toString(animals));
        System.out.println(Arrays.toString(ints));
    }

    public void recieveArray(Animal[] animals){
        System.out.println(Arrays.toString(animals));
    }
}
class Animal{
    @Override
    public String toString() {
        return "I am a Animal";
    }
}
