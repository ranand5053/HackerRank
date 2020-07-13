public class RepeatedString {
    static long repeatedString(String s, long n) {
        long length = s.length();
        long rem = n % length;
        long div = n/length;
        System.out.println("rem:: "+ rem+" , div:: "+ div);
        char[] chars = s.toCharArray();
        long remACount = 0;
        long strACount = 0;
        if(div == 0){
            length = rem;
        }
        for(int i = 0; i < length; i++){
            char c = chars[i];
            if(c == 'a'){
                strACount++;
            }
            if(rem > 0 && i == rem-1){
                remACount = strACount;
            }
        }
        long total = strACount*div + remACount;
        return total;
    }
    public static void main(String[] args) {
        String s = "a";
        long n = 1000000000000l;
        System.out.println(repeatedString(s, n));
    }
}
