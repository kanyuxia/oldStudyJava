package genericity;

/**
 * Created by kanyuxia on 2017/1/10.
 */

public class People implements Comparable<People>{
    private double weight;

    public People(double weight){
        this.weight = weight;
    }
    @Override
    public int compareTo(People people) {
        if(this.weight >people.weight){
            return 1;
        }else if(this.weight <people.weight){
            return -1;
        }
        return 0;
    }
    public double getWeight() {
        return weight;
    }
}
interface Language{
    String sayLanguage();
}

class Japanese extends Asian{
    Japanese(double weight){
        super(weight);
    }
}

class Asian extends People{
    Asian(double weight){
        super(weight);
    }
}

class American extends People{
    American(double weight){
        super(weight);
    }
}

class Chinese extends Asian implements Language{
    Chinese(double weight){
        super(weight);
    }

    @Override
    public String sayLanguage() {
        return "我说中文";
    }
}
