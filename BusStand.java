import java.util.*;

public class BusStand {
    public static List<Integer> kthPerson(int k, List<Integer> p, List<Integer> q) {
        // Write your code here
        Map<Integer, Integer> personQueryMap = new HashMap<>();
        int max = Integer.MAX_VALUE;
        Integer[] res = new Integer[q.size()];
        System.out.println("K::"+k+", P:: "+p.size());
        System.out.println("Queue:: "+q);
        for(int i = 0; i < q.size(); i++){
            int count = 0;
            int queries = q.get(i);
            if(k > p.size() || queries > max){
                res[i] = 0;
                continue;
            }
            if(personQueryMap.get(queries) != null){
                res[i] = personQueryMap.get(queries);
                continue;
            }
            for(int j = 0; j < p.size(); j++) {
                int patienceTime = p.get(j);
                if(patienceTime >= queries ){
                    count++;
                    if(count == k){
                        res[i]=j+1;
                        personQueryMap.put(queries, res[i]);
                        break;
                    }
                }
            }
            if(count < k) {
                res[i] = 0;
                if(max > queries) {
                    max = queries;
                }
            }
        }
        return Arrays.asList(res);

    }
    public static void main(String args[]) {
        Integer[] p = new Integer[]{2,5,3};
        Integer[] q = new Integer[]{1,5};
        int k = 3;
        //Integer[] p = new Integer[]{1,4,4,3,1,2,6};
        //Integer[] q = new Integer[]{1,2,3,4,5,6,7};
        //int k = 2;

        List<Integer> patience = Arrays.asList(p);
        List<Integer> queries = Arrays.asList(q);

        System.out.println(kthPerson(k, patience, queries));
    }
}
