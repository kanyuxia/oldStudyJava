package test4;

/**
 * Created by king_ant on 2016/9/2.
 */

public class XiXueGui {
    static int count = 0;

    /*
    思路是把4位数分成4个数，然后两两配对比较
     */
    void fun1(int num) {
        int[] t = {0, 1, 2, 3};
        //把4位数分割成4位数
        int[] a = new int[4];
        int index = 0;
        int f = num;
        while (f != 0) {
            a[index++] = f % 10;
            f = f / 10;
            count++;
        }
        //将两个数比较，若相等则为吸血鬼数字
        for (int i = 0; i < 4; i++) {
            for (int j = 0, p = 0, q = 0; j < 4; j++) {
                if (i == j) {
                    count++;
                    continue;
                } else {
                    for (int x : t) {
                        if (x != i && x != j) {
                            p = x;
                            count++;
                            break;
                        }
                    }
                    for (int x : t) {
                        if (x != i && x != j && x != p) {
                            q = x;
                            count++;
                            break;
                        }
                    }
                    if ((a[i] * 10 + a[j]) * (a[p] * 10 + a[q]) == num) {
                        System.out.println("1000-10000的吸血鬼数字有：" + num);
                    }
                }
            }
        }
    }


    void fun2() {
        String[] ar_str1 = null, ar_str2;
        for (int i = 10; i < 100; i++) {
            for (int j = i + 1; j < 100; j++) {
                int i_val = i * j;
                if (i_val < 1000 || i_val > 9999)
                    continue; // 积小于1000或大于9999排除,继续下一轮环
                count++;
                ar_str1 = String.valueOf(i_val).split("");
                ar_str2 = (String.valueOf(i) + String.valueOf(j)).split("");
                java.util.Arrays.sort(ar_str1);
                java.util.Arrays.sort(ar_str2);
                if (java.util.Arrays.equals(ar_str1, ar_str2)) {
                    // 排序后比较,为真则找到一组
                    System.out.println("1000-10000的吸血鬼数字有：" + i_val);
                }
            }
        }
    }

    public void testNum() {
        fun2();
        System.out.println("共循环："+count + " 次");
    }

    public static void main(String[] args){
        new XiXueGui().testNum();
    }

}
