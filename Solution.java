public class Solution {

    public static int solution(String S, int K) {
        int sum = 0;
        int u = 0, count = 0;
        char[] chars = new char[S.length()];
        int[] counts = new int[S.length()];
        for (int i = 0; i < S.length(); i++) {
            count = 1;
            while (i < S.length() - 1 && S.charAt(i) == S.charAt(i+1)) {
                ++count;
                ++i;
            }
            counts[u] = count;
            chars[u] = S.charAt(i);
            ++u;
            sum += 1;
            if (count > 1) sum += ("" + count).length();
        }

        int max = Integer.MIN_VALUE;

        for (int i = 0; i < u; i++) {
            int c = i;
            int cut = K;
            int rem = 0;
            while (c < u && cut > 0) {
                if (counts[c] >= cut) {
                    rem = counts[c] - cut;
                    cut = 0;
                } else {
                    cut -= counts[c];
                    ++c;
                }
            }
            if (cut == 0) {
                int savedL = 0;
                for (int j = i; j <= c; j++) {
                    if (j == c) {
                        if (rem == 0) {
                            if (i !=0 && j != S.length() - 1 && S.charAt(i - 1) == S.charAt(j + 1)) {
                                savedL += ("" + counts[i - 1]).length() + ("" + counts[j + 1]).length() - ((counts[i - 1] + counts[j + 1]) + "").length() + 1;
                            }
                            savedL += 1;
                            if (counts[j] > 1) savedL += ("" + counts[j]).length();
                        } else {
                            if (i != 0 && S.charAt(i - 1) == S.charAt(j)) {
                                savedL += ("" + counts[i - 1]).length() + ("" + counts[j]).length() - ((counts[i - 1] + rem) + "").length() + 1;
                            } else {
                                savedL += ("" + counts[j]).length();
                                if (rem > 1) {
                                    savedL -= ("" + rem).length();
                                }
                            }
                        }
                    } else {
                        savedL += 1;
                        if (counts[j] > 1) savedL += ("" + counts[j]).length();
                    }
                }
                if (savedL > max) max = savedL;
            }
        }


        return sum - max;
    }
    public static void main(String[] args) {
        int[] A = new int[]{-1,-3};
        System.out.println("Main method");
        System.out.println(solution("ABCDDDEFG", 2));
    }
}
