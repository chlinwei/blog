package lw.pers.blog.util;

import java.util.Random;
/**
 * 目录生成算法,总共能生成n1*n2个子目录
 */
public class DirCreateUtil {
    public static int n1 = 100;
    public static int n2 = 100;
    public static String direCreate() {
        Random random = new Random();
        //随机生成1~n1,1~n2间的数
        String s1 = String.valueOf(random.nextInt(n1) + 1);
        String s2 = String.valueOf(random.nextInt(n2) + 1);
        return "/" + s1 + "/" + s2;
    }
    public static void main(String args[]){
        System.out.println(direCreate());
    }

}
