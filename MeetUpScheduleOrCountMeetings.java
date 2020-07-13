import java.util.*;

public class MeetUpScheduleOrCountMeetings {

    public static int countMeetings(List<Integer> firstDay, List<Integer> lastDay) {
        // Write your code here
        Set res = new HashSet();
        Queue<DayRange> queue = new PriorityQueue<DayRange>();
        for (int i = 0; i < firstDay.size(); i++) {
            queue.offer(new DayRange(firstDay.get(i), lastDay.get(i)));
        }
        while (!queue.isEmpty()) {
            DayRange dr = queue.poll();
            int endDay = dr.endDay;
            int startDay = dr.startDay;
            while (!res.add(endDay)) {
                if(endDay == startDay)
                    break;
                else
                    endDay--;
            }
        }
        return res.size();

    }
    public static void main(String args[]) {
        Integer[] firstDay = new Integer[]{1,2,3,4,1};
        Integer[] lastDay = new Integer[]{2,3,3,4,4};

        List<Integer> firstDayList = Arrays.asList(firstDay);
        List<Integer> lastDayList = Arrays.asList(lastDay);

        System.out.println(countMeetings(firstDayList, lastDayList));
    }
}
    class DayRange implements Comparable{
        int startDay;
        int endDay;
        public DayRange(int startDay, int endDay){
            this.startDay = startDay;
            this.endDay = endDay;
        }

        @Override
        public int compareTo(Object o) {
            DayRange dRange = (DayRange)o;
            int diff = (endDay-startDay) - (dRange.endDay-dRange.startDay);
            /*if(diff == 0){
                diff = dRange.endDay - endDay;
            }
            if(diff == 0){
                diff = dRange.startDay - startDay;
            }*/
            return diff;
        }
    }

