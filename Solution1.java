import java.util.HashMap;
import java.util.Map;

public class Solution1 {


    static int solution(String s) {
        int count = 0;
        for(char c : s.toCharArray()){
            count += c == 'a' ? 1 : 0;
        }
        if(count %3 != 0)
            return 0;
        int res = 0, k = count/3, sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0;i<s.length();i++) {
            sum += s.charAt(i) == 'a' ? 1 : 0;
            if(sum == 2*k && map.get(k) != null && i < s.length() - 1 && i > 0) {
                res += map.get(k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(solution("aabaaabbbaaabbbbaaabbbbaaaa"));
    }}
