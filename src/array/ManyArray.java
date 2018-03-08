package array;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by kanyuxia on 2017/1/13.
 */
public class ManyArray {
    private static Random random = new Random();
    public static void main(String[] args){
        ManyArray manyArray = new ManyArray();
        manyArray.print(manyArray.produce(2,5));
        Integer[][] a = new Integer[2][3];
        for(int i = 0;i < a.length;i++){
            Arrays.fill(a[i],1);
        }
        Integer[] b = new Integer[3];
        Arrays.fill(b,12);
        Integer[] c = new Integer[3];
        Arrays.fill(c,12);
        System.out.println(Arrays.deepToString(a));
        System.out.println(Arrays.toString(b));
        System.out.println(Arrays.toString(c));
        System.out.println(Arrays.equals(b,c));
        System.out.println(Arrays.binarySearch(c,12));
        System.out.println(Arrays.hashCode(c));
        System.out.println(Arrays.toString(Arrays.copyOf(c,2)));
        System.out.println(Arrays.toString(Arrays.copyOfRange(b,0,3)));
        System.out.println(c.length);
        Object[] d = new Object[5];
        for(int i = 0;i < d.length;i++){
            d[i] = new Object();
        }
        Object[] e = Arrays.copyOf(d,5);
        System.out.println("d:"+d[1]);
        System.out.println("e:"+e[1]);
    }

    public double[][] produce(int n,int m){
        double[][] a = new double[n][m];
        for(int i = 0;i < a.length;i++){
            for(int j = 0;j< a[i].length;j++){
                a[i][j] = random.nextInt(m-n)+n;
            }
            Arrays.sort(a[i]);
        }
        return a;
    }

    public void print(double[][] a){
        System.out.println(Arrays.deepToString(a));
    }
}
