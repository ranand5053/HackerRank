import java.util.Arrays;

public class ElectronicsShop {

    static int getMoneySpent(int[] keyboards, int[] drives, int b) {
        Arrays.sort(keyboards);
        Arrays.sort(drives);
        int totalKeyboards = keyboards.length;
        int totalDrives = drives.length;
        int max = 0;
        System.out.println("keyboards[0]: "+keyboards[0]+" , drives[0]:: "+drives[0]);

        if( (keyboards[0] + drives[0] > b) || (keyboards[0] > b) || (drives[0] > b) ){
            return -1;
        }
        int j = totalDrives-1;
        for(int i = 0; i < totalKeyboards; i++) {
            int val1 = keyboards[i];
            int total = 0;
            System.out.println("val1: "+val1);

            while(j >= 0){
                int val2 = drives[j];
                total = val1 + val2;
                System.out.println(" val2: "+val2+", total: "+total);
                if(total > b){
                    j--;
                    continue;
                }else {
                    if(total > max) {
                        max = total;
                    }
                    break;
                }
            }
        }
        return max;
    }
    public static void main(String[] args) {
        int[] keyboards = new int[]{20,21,35,40,50,55};
        int[] drives = new int[]{2,3,15,18,25,28};
        //int[] keyboards = new int[]{3,1};
        //int[] drives = new int[]{5,2,8};
        int b = 40;
        System.out.println(getMoneySpent( keyboards, drives, b));
    }
}
