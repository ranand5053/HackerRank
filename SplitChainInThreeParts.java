
public class SplitChainInThreeParts {

    static int solution(int[] A){
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        for(int i = 1; i < A.length-1; i++) {
            int val = A[i];
            while(val >= A[++i]){
                val = A[i];
            }
            if(min2 <= min1){
                if(min1 > val) {
                    min1 = val;
                }
            }else {
                if(min2 > val) {
                    min2 = val;
                }
            }
        }
        return min1 + min2;
    }

    public static void main(String args[]){
        int[] n = new int[]{5,3,2,6,2,4,2,1,7};
        System.out.println(solution(n));
    }
}
