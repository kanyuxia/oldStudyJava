package genericity;

/**
 * Created by kanyuxia on 2017/1/11.
 */
public class Holder3<T extends People & Language> {
    private T a;

    public void setA(T a){
        this.a = a;
    }

    public T getA(){
        return  a;
    }

    public static void main(String[] args){
        Holder3<Chinese> holder3 = new Holder3<Chinese>();
        holder3.setA(new Chinese(12.0));
        System.out.println(holder3.getA().getWeight()+"\n"+holder3.getA().sayLanguage());

    }
}
