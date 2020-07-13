import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

class Solution2 {

    public static void main(String[] args) {
        int count = solution("ABCDAF", new int[]{2,4,1,-1,2,-3}, new int[]{3,2,-1,3,4,1});
        System.out.println(count);
    }
    public static int solution(String S, int[] X, int[] Y) {
        Queue<Coordinates> minHeap = new PriorityQueue<>((a, b)->a.dist - b.dist);
        for(int i=0;i<S.length();i++) {
            minHeap.offer(new Coordinates(S.charAt(i), X[i], Y[i]));
        }
        Set<Character> visited = new HashSet<>();
        int res = 0;
        while(!minHeap.isEmpty()) {
            Set<Character> set = new HashSet<>();
            Coordinates cur = minHeap.poll();
            set.add(cur.tag);
            boolean isDup = false;
            while(!minHeap.isEmpty() && minHeap.peek().dist == cur.dist) {
                Coordinates next = minHeap.poll();
                if(!set.add(next.tag))
                    isDup=true;
            }
            if(isDup) {
                break;
            }else {
                boolean isVisited = false;
                for(char c : set) {
                    if(!visited.add(c))
                        isVisited = true;
                }
                res += isVisited ? 0 : set.size();
                if(isVisited)
                    break;
            }
        }
        return res;
    }
}

class Coordinates{
    int dist;
    char tag;

    public Coordinates(char tag, int x, int y) {
        this.dist = x*x + y*y;
        this.tag = tag;
    }
}