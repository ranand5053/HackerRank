public class TaumAndBirthday {

    public static long taumBday(int b, int w, int bc, int wc, int z) {
        // Write your code here
        System.out.println("b: "+b+", w: "+w+", bc: "+bc+", wc: "+wc+", z: "+z);
        long res = 0;
        long diff = Math.abs((bc-wc));
        System.out.println("Diff:: "+diff);
        if(diff <= z){
            res = b*bc + w*wc;
        }else {
            res = bc < wc ? (b*bc + (w*(bc+z))) : (w*wc + (b*(wc+z)));
        }
        System.out.println("res: "+res);

        return res;

    }

    public static void main(String[] args) {

        System.out.println(taumBday(3, 3, 1, 9, 2));
        System.out.println(taumBday(7, 7, 4, 2, 1));
        System.out.println(taumBday(3, 6, 9, 1, 1));

    }
}
