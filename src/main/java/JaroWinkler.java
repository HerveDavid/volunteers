import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class JaroWinkler {

    private static class StringDistance implements Comparable<StringDistance> {
        private StringDistance(String word, double distance) {
            this.word = word;
            this.distance = distance;
        }
        public int compareTo(StringDistance s) {
            return Double.compare(distance, s.distance);
        }
        private String word;
        private double distance;
    }

    private static List<StringDistance> withinDistance(List<String> words,
                                                       double maxDistance, String string, int max) {
        List<StringDistance> result = new ArrayList<>();
        for (String word : words) {
            double distance = jaroWinklerDistance(word, string);
            if (distance <= maxDistance)
                result.add(new StringDistance(word, distance));
        }
        Collections.sort(result);
        if (result.size() > max)
            result = result.subList(0, max);
        return result;
    }

    public static double jaroWinklerDistance(String string1, String string2) {
        int len1 = string1.length();
        int len2 = string2.length();
        if (len1 < len2) {
            String s = string1;
            string1 = string2;
            string2 = s;
            int tmp = len1;
            len1 = len2;
            len2 = tmp;
        }
        if (len2 == 0)
            return len1 == 0 ? 0.0 : 1.0;
        int delta = Math.max(1, len1 / 2) - 1;
        boolean[] flag = new boolean[len2];
        Arrays.fill(flag, false);
        char[] ch1Match = new char[len1];
        int matches = 0;
        for (int i = 0; i < len1; ++i) {
            char ch1 = string1.charAt(i);
            for (int j = 0; j < len2; ++j) {
                char ch2 = string2.charAt(j);
                if (j <= i + delta && j + delta >= i && ch1 == ch2 && !flag[j]) {
                    flag[j] = true;
                    ch1Match[matches++] = ch1;
                    break;
                }
            }
        }
        if (matches == 0)
            return 1.0;
        int transpositions = 0;
        for (int i = 0, j = 0; j < len2; ++j) {
            if (flag[j]) {
                if (string2.charAt(j) != ch1Match[i])
                    ++transpositions;
                ++i;
            }
        }
        double m = matches;
        double jaro = (m / len1 + m / len2 + (m - transpositions / 2.0) / m) / 3.0;
        int commonPrefix = 0;
        len2 = Math.min(4, len2);
        for (int i = 0; i < len2; ++i) {
            if (string1.charAt(i) == string2.charAt(i))
                ++commonPrefix;
        }
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        return Double.valueOf(df.format(1.0 - (jaro + commonPrefix * 0.1 * (1.0 - jaro))).replace(",", "."));
    }

}