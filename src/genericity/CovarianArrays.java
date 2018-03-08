package genericity;

/**
 * Created by kanyuxia on 2017/1/11.
 */
public class CovarianArrays {

    public static void main(String[] args){
        People people = new Asian(10);
        System.out.println(people.getWeight());
        System.out.println(people.getClass().getName());
        people = new American(200);
        System.out.println(people.getWeight());
        System.out.println(people.getClass().getName());

        People[] peoples = new Asian[10];
        System.out.println(peoples.getClass().getName());
        peoples[2] = new Chinese(100);
        System.out.println(peoples[2].getWeight());
        try{
              peoples[0] = new People(1);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
