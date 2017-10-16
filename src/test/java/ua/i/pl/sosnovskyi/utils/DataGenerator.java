package ua.i.pl.sosnovskyi.utils;

import java.util.Random;

public class DataGenerator {
    private static Random random;
    private static char[] upperChars = {'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M',
            'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'};
    private static char[] chars = {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm',
            'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'};
    private static int length = chars.length - 1;

    public static String getWord() {
        StringBuilder sb = new StringBuilder();
        random = new Random(System.currentTimeMillis());
        int currentWordLength = random.nextInt(10);
        sb.append(upperChars[random.nextInt(length)]);
        for (int i = 0; i < currentWordLength; i++) {
            sb.append(chars[random.nextInt(length)]);
        }
        return sb.toString();
    }

    public static String getEmail(){
        StringBuilder sb = new StringBuilder();
        random = new Random(System.currentTimeMillis());
//        int currentWordLength = random.nextInt(length);
//        for (int i = 0; i < currentWordLength; i++) {
//            sb.append(chars[random.nextInt(length)]);
//        }
        sb.append(getWord().toLowerCase());
        sb.append('@');
        sb.append(getWord().toLowerCase());
        sb.append('.');
        sb.append(getWord().toLowerCase());
        return sb.toString();
    }
    public static String getCode(){
        random = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        int length=5;
        for(int i=0; i<length; i++){
        sb.append(random.nextInt(9))  ;
        }
        return sb.toString();
    }
}
