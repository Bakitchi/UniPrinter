package Util;

import java.util.Random;

/**
 * @Author: michael
 * @Date: 16-11-12 上午2:55
 * @Project: Uni-Pinter
 * @Package: Util
 */
public class RandomString {
    public static String RandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }
}
