package genericity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by kanyuxia on 2017/1/10.
 */
public class StudyGenericity {

    private List<People> peoples = new ArrayList<People>();
    private Random random = new Random();

    public StudyGenericity(){
        init();
    }

    private void init(){
        for(int i = 0;i<10;i++){
            People people = new People(random.nextInt(70)+30);
            peoples.add(people);
        }
    }

    public void testComparable(){
        People[] peopleArray = new People[peoples.size()];
        peoples.toArray(peopleArray);

        for(People people:peopleArray){
            System.out.println(people.getWeight());
        }

        System.out.println();
        // 使用自然排序
        Arrays.sort(peopleArray,null);

        for(People people:peopleArray){
            System.out.println(people.getWeight());
        }
    }

    public static void main(String[] args){
        new StudyGenericity().testComparable();
    }


}
