import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PickingNumbers {

    public static int solution(List<Integer> arr) {
        Collections.sort(arr);
        int max = 0;
        for(int i = 0; i < arr.size(); i++) {
            int count = 1;
            int val = arr.get(i);
            for(int j = i+1; j < arr.size(); j++) {
                int val1 = arr.get(j);
                if( val1 - val <= 1){
                    count++;
                    i++;
                }else{
                    break;
                }
            }
            if(max < count){
                max = count;
            }

        }
        return max;
    }
    public static void main(String[] args) {
        Integer[] arr = new Integer[]{4, 6, 5, 3, 3, 1};
        //Integer[] arr = new Integer[]{1, 2, 2, 3, 1, 2};
        //Integer[] arr = new Integer[]{1, 1, 2,2, 4, 4, 5, 5, 5};
        List<Integer> list = Arrays.asList(arr);
        System.out.println(solution(list));
    }
}
