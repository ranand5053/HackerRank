import java.util.*;

public class MigratoryBirds_CountAndSortAlgo {

    static int migratoryBirds(List<Integer> arr) {
        int[] sorted = new int[arr.size()];
        for(int i = 0; i < arr.size(); i++) {
            sorted[arr.get(i)]++;
        }
        int max = 0;
        int res = 0;
        for(int i = 0; i < sorted.length; i++){
            if(sorted[i] > max){
                max = sorted[i];
                res = i;
            }

        }
        return res;
    }
    public static void main(String[] args) {
        //Integer[] arr = new Integer[]{4, 4, 5, 3, 3, 1};
        //Integer[] arr = new Integer[]{1, 4, 4, 4, 5, 3};
        //Integer[] arr = new Integer[]{1, 2, 3, 4, 5, 4, 3, 2, 1, 3, 4};
        Integer[] arr = new Integer[]{2, 5, 2, 5, 5, 5, 2, 2, 5, 5, 5, 1, 5, 2, 2, 2, 2, 2};
        int[] ary = new int[arr.length];
        /*for (int i = 0; i < arr.length; i++)
            ary[arr[i]]++;
        for(int i = 0; i < arr.length; i++)
            System.out.println(ary[i]);*/
        List<Integer> list = Arrays.asList(arr);
        System.out.println("Result "+migratoryBirds(list));
    }
}
