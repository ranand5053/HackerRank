import java.util.Arrays;
import java.util.List;

public class CavityMap {

    static String[] cavityMap(String[] grid) {
        String[] res = new String[grid.length];
        for(int i = 0; i < grid.length; i++ ) {
            if(i == 0 || i == grid.length-1){
                res[i] = grid[i];
                continue;
            }
            char[] s = grid[i].toCharArray();
            for(int j = 1; j < s.length-1; j++){
                if(res[i-1].charAt(j) == 'X'){
                    continue;
                }
                long depth = Character.getNumericValue(s[j]);
                long upDepth = Character.getNumericValue(grid[i-1].charAt(j));
                long downDepth = Character.getNumericValue(grid[i+1].charAt(j));
                long leftDepth = Character.getNumericValue(grid[i].charAt(j-1));
                long rightDepth = Character.getNumericValue(grid[i].charAt(j+1));
                if(depth > upDepth && depth > downDepth && depth > leftDepth && depth > rightDepth ) {
                    s[j]='X';
                    j++;
                }
            }
            res[i] = String.valueOf(s);
        }
        return res;
    }

    public static void main(String[] args) {
        String[] arr = new String[]{"1112", "1912", "1892", "1234"};
        String[] res = cavityMap(arr);
        for(int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }

    }
}