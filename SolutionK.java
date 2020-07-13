class SolutionK {
    static int solution(int[] A, int K) {
        int numberOfNails = A.length;
        int best = 0;
        int sameSize = 1;
        for (int i = 1; i < numberOfNails - K ; i++) {
            if (A[i] == A[i - 1])
                sameSize = sameSize + 1;
            else
                sameSize = 1;
            if (sameSize > best)
                best = sameSize;
        }
        System.out.println("Best "+best);
        int result = best +  K;

        return result;
    }

    public static void main(String args[]){
        //int[] n = new int[]{1,3,3,3,4,5,5,5,5};
        //int[] n = new int[]{1,3,3,3,4,5,5,5,5,5,5,5};
        int[] n = new int[]{1,2,3};

        System.out.println(solution(n, 3));
    }
}