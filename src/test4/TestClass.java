package test4;


import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Random;
import java.util.Scanner;

class RandomWorlds implements Readable {
    private static Random random = new Random(47);
    private static final char[] capitals = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] lowers = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] vowels = "aeiou".toCharArray();
    private int count;

    RandomWorlds(int count) {
        this.count = count;
    }

    @Override
    public int read(CharBuffer cb) throws IOException {
        if (count-- == 0) {
            return -1;
        }
        cb.append(capitals[random.nextInt(capitals.length)]);
        for (int i = 0; i < 4; i++) {
            cb.append(lowers[random.nextInt(lowers.length)]);
            cb.append(vowels[random.nextInt(vowels.length)]);
        }
        cb.append(" ");
        return 10;
    }
}
class RandomDoubles{
    private static Random random = new Random(47);
    double next(){
        return random.nextDouble();
    }
}
class AdaptedRandomDoubles extends RandomDoubles implements Readable{
    private int count;
    AdaptedRandomDoubles(int count){
        this.count = count;
    }
    @Override
    public int read(CharBuffer cb) throws IOException {
        if(count--==0){
            return -1;
        }
        String result = Double.toString(next())+" ";
        cb.append(result);
        return result.length();
    }
}

public class TestClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(new AdaptedRandomDoubles(7));
        while(scanner.hasNext()){
            System.out.println(scanner.next());
        }
    }
}





