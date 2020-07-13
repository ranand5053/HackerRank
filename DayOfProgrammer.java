public class DayOfProgrammer {

    // Complete the dayOfProgrammer function below.
    static String dayOfProgrammer(int year) {
        int[] monthDays = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
        int days = 256;
        //adjusting number of days for Calendar switch Julian to Gregorian
        if(year == 1918) {
            days = 256+13;
        }
        int month = 1;
        for(;month < monthDays.length; month++){
            if(days > monthDays[month-1]) {
                days -= monthDays[month-1];
            } else{
                break;
            }
        }
        if((year > 1918 &&  ((year % 4 == 0 && year % 100 != 0) ||(year %400 ==0))) ||
                (year < 1918 && year % 4 == 0)){
            days -= 1;
        }
        return days+".0"+month+"."+year;
    }

    public static void main(String args[]) {
        System.out.println("Date: "+dayOfProgrammer(1800));
    }
}
