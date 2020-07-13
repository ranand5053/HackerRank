import java.io.*;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.math.BigDecimal;

public class TransactionByLocationIP{

    static Pattern dataPattern = Pattern.compile("\\[.*?\\]");
    static Pattern userIdPattern = Pattern.compile("((?<=\"userid\":)(\\w+)(?=,+))", Pattern.CASE_INSENSITIVE);
    static Pattern amountPattern = Pattern.compile("(\\$?(([1-9]\\d{0,2}(,\\d{3})*)|\\d+)?(\\.\\d{1,2}))");
    static Pattern ipPattern = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
    static Pattern locationPattern = Pattern.compile("((?<=\"location\":\\{\"id\":)(\\w+)(?=,+))", Pattern.CASE_INSENSITIVE);


    public static int getTransactions(int userId, int locationId, int netStart, int netEnd)
            throws IOException {
        String url = "https://jsonmock.hackerrank.com/api/transactions/search?userId="+userId;
        String response = getResponse(url);
        Pattern totalPagesPattern = Pattern.compile("(?<=\"total_pages\":)(\\w+)(?=,)", Pattern.CASE_INSENSITIVE);
        Matcher totalPagesMatcher = totalPagesPattern.matcher(response.toString());
        Integer totalPages = 1;
        if(totalPagesMatcher.find()){
            totalPages = Integer.parseInt(totalPagesMatcher.group());
        }
        BigDecimal total = getPageTotal(response.toString(), userId, locationId, netStart, netEnd);

        for(int i = 2; i <= totalPages; i++){
            String pageURL = url+"&page="+i;
            String pageResponse = getResponse(pageURL);
            BigDecimal newPageTotal = getPageTotal(pageResponse, userId, locationId, netStart, netEnd);
            total = total.add(newPageTotal);
        }
        total = total.setScale(0, RoundingMode.HALF_UP);
        return total.intValue();
    }

    private static String getResponse(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        String inputLine;
        StringBuffer response = new StringBuffer();
        try (InputStream is = url.openStream();
             BufferedReader in = new BufferedReader(new InputStreamReader(is))){
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return response.toString();
    }

    private static BigDecimal getPageTotal(String response, int userId, int locationId, int netStart, int netEnd){
        BigDecimal total = BigDecimal.ZERO;
        Matcher matcher = dataPattern.matcher(response);
        if(matcher.find()){
            String data = matcher.group();
            System.out.println(" Data:: " + data);
            String[] parts = data.split("\\},\\{");

            for(int i = 0; i < parts.length; i++){
                System.out.println(parts[i]);
                Matcher amountMatcher = amountPattern.matcher(parts[i]);
                amountMatcher.find();
                String amountStr = amountMatcher.group().replaceAll("[^\\d.]+", "");
                System.out.println("Amount:: "+amountStr);

                if( isUserValid(parts[i], userId)
                        && isLocationValid(parts[i], locationId)
                        && isIPValid(parts[i], netStart, netEnd)){
                    BigDecimal amount = new BigDecimal(amountStr);
                    total = total.add(amount);
                }
            }
        }
        return total;
    }
    private static boolean isUserValid(String data, int userId) {
        Matcher userIdMatcher = userIdPattern.matcher(data);
        if(userIdMatcher.find()) {
            int uid = Integer.parseInt(userIdMatcher.group());
            System.out.println("UserId :: "+ uid);
            if(userId == uid){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private static boolean isLocationValid(String data, int locationId) {
        Matcher locationMatcher = locationPattern.matcher(data);
        if(locationMatcher.find()) {
            int resLoc = Integer.parseInt(locationMatcher.group());
            System.out.println("Location :: "+locationId);
            if(locationId == resLoc){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private static boolean isIPValid(String data, int start, int end){
        Matcher ipMatcher = ipPattern.matcher(data);
        if(ipMatcher.find()) {
            String ipAddress = ipMatcher.group();
            System.out.println("IP Address :: "+ipAddress);
            String[] ipParts = ipAddress.split("\\.");
            int result = Integer.parseInt(ipParts[0]);
            if(result >= start && result <= end){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static void main(String args[]) throws Exception{
        System.out.println(getTransactions(2, 8, 5, 50));
        /*String[] s = new String[]{"{\"page\":1,\"per_page\":10,\"total\":67,\"total_pages\":7,\"data\":[{\"id\":8,\"userId\":4,\"userName\":\"Francesco De Mello\",\"timestamp\":1548805761859,\"txnType\":\"credit\",\"amount\":\"$1,214.44\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"35.151.1.82\"},{\"id\":16,\"userId\":4,\"userName\":\"Francesco De Mello\",\"timestamp\":1551533186809,\"txnType\":\"credit\",\"amount\":\"$1,233.56\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"212.215.115.165\"},{\"id\":17,\"userId\":4,\"userName\":\"Francesco De Mello\",\"timestamp\":1551693726293,\"txnType\":\"credit\",\"amount\":\"$1,806.13\",\"location\":{\"id\":6,\"address\":\"206, Portaline, Brooklyn Avenue\",\"city\":\"Brownlee\",\"zipCode\":80358},\"ip\":\"181.191.153.61\"},{\"id\":26,\"userId\":4,\"userName\":\"Francesco De Mello\",\"timestamp\":1548544617435,\"txnType\":\"credit\",\"amount\":\"$743.38\",\"location\":{\"id\":6,\"address\":\"206, Portaline, Brooklyn Avenue\",\"city\":\"Brownlee\",\"zipCode\":80358},\"ip\":\"212.215.115.165\"},{\"id\":28,\"userId\":4,\"userName\":\"Francesco De Mello\",\"timestamp\":1552809326043,\"txnType\":\"credit\",\"amount\":\"$2,769.86\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"132.169.40.222\"},{\"id\":30,\"userId\":4,\"userName\":\"Francesco De Mello\",\"timestamp\":1548262142180,\"txnType\":\"debit\",\"amount\":\"$2,647.85\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"119.162.205.226\"},{\"id\":31,\"userId\":4,\"userName\":\"Francesco De Mello\",\"timestamp\":1550206254757,\"txnType\":\"credit\",\"amount\":\"$3,960.30\",\"location\":{\"id\":8,\"address\":\"389, Everest, Barwell Terrace\",\"city\":\"Murillo\",\"zipCode\":66061},\"ip\":\"181.191.153.61\"},{\"id\":35,\"userId\":4,\"userName\":\"Francesco De Mello\",\"timestamp\":1546446032263,\"txnType\":\"credit\",\"amount\":\"$1,162.60\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"5.116.1.11\"},{\"id\":36,\"userId\":4,\"userName\":\"Francesco De Mello\",\"timestamp\":1549391785785,\"txnType\":\"debit\",\"amount\":\"$188.04\",\"location\":{\"id\":8,\"address\":\"389, Everest, Barwell Terrace\",\"city\":\"Murillo\",\"zipCode\":66061},\"ip\":\"5.116.1.11\"},{\"id\":39,\"userId\":4,\"userName\":\"Francesco De Mello\",\"timestamp\":1552166736242,\"txnType\":\"credit\",\"amount\":\"$3,705.83\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"212.215.115.165\"}]}",
        "{\"page\":\"2\",\"per_page\":10,\"total\":76,\"total_pages\":8,\"data\":[{\"id\":44,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1546401458604,\"txnType\":\"credit\",\"amount\":\"$1,134.12\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"142.216.23.1\"},{\"id\":45,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1549247308734,\"txnType\":\"credit\",\"amount\":\"$514.70\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"5.116.1.11\"},{\"id\":52,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1550887247981,\"txnType\":\"debit\",\"amount\":\"$434.38\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"181.191.153.61\"},{\"id\":55,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1550038422331,\"txnType\":\"credit\",\"amount\":\"$630.80\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"119.162.205.226\"},{\"id\":63,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1547141971176,\"txnType\":\"debit\",\"amount\":\"$1,142.55\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"35.151.1.82\"},{\"id\":68,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1547943352806,\"txnType\":\"debit\",\"amount\":\"$2,218.57\",\"location\":{\"id\":8,\"address\":\"389, Everest, Barwell Terrace\",\"city\":\"Murillo\",\"zipCode\":66061},\"ip\":\"35.151.1.82\"},{\"id\":69,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1552245792882,\"txnType\":\"credit\",\"amount\":\"$1,073.47\",\"location\":{\"id\":8,\"address\":\"389, Everest, Barwell Terrace\",\"city\":\"Murillo\",\"zipCode\":66061},\"ip\":\"119.162.205.226\"},{\"id\":74,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1546966983149,\"txnType\":\"credit\",\"amount\":\"$889.53\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"181.191.153.61\"},{\"id\":81,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1554000873959,\"txnType\":\"debit\",\"amount\":\"$3,568.55\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"212.215.115.165\"},{\"id\":82,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1546793876879,\"txnType\":\"credit\",\"amount\":\"$1,703.06\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"35.151.1.82\"}]}",
        "{\"page\":\"3\",\"per_page\":10,\"total\":76,\"total_pages\":8,\"data\":[{\"id\":83,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1546606369803,\"txnType\":\"debit\",\"amount\":\"$2,551.34\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"142.216.23.1\"},{\"id\":84,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1546369438369,\"txnType\":\"credit\",\"amount\":\"$2,615.48\",\"location\":{\"id\":6,\"address\":\"206, Portaline, Brooklyn Avenue\",\"city\":\"Brownlee\",\"zipCode\":80358},\"ip\":\"5.116.1.11\"},{\"id\":91,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1549474339557,\"txnType\":\"credit\",\"amount\":\"$652.81\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"119.162.205.226\"},{\"id\":92,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1552834351584,\"txnType\":\"credit\",\"amount\":\"$595.51\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"181.191.153.61\"},{\"id\":102,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1556165928518,\"txnType\":\"credit\",\"amount\":\"$1,688.24\",\"location\":{\"id\":8,\"address\":\"389, Everest, Barwell Terrace\",\"city\":\"Murillo\",\"zipCode\":66061},\"ip\":\"119.162.205.226\"},{\"id\":107,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1559402521261,\"txnType\":\"debit\",\"amount\":\"$1,872.07\",\"location\":{\"id\":6,\"address\":\"206, Portaline, Brooklyn Avenue\",\"city\":\"Brownlee\",\"zipCode\":80358},\"ip\":\"181.191.153.61\"},{\"id\":111,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1557076924093,\"txnType\":\"credit\",\"amount\":\"$2,330.05\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"142.216.23.1\"},{\"id\":118,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1560694086761,\"txnType\":\"credit\",\"amount\":\"$3,192.75\",\"location\":{\"id\":6,\"address\":\"206, Portaline, Brooklyn Avenue\",\"city\":\"Brownlee\",\"zipCode\":80358},\"ip\":\"119.162.205.226\"},{\"id\":120,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1557871193894,\"txnType\":\"debit\",\"amount\":\"$924.93\",\"location\":{\"id\":6,\"address\":\"206, Portaline, Brooklyn Avenue\",\"city\":\"Brownlee\",\"zipCode\":80358},\"ip\":\"132.169.40.222\"},{\"id\":121,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1561826593797,\"txnType\":\"debit\",\"amount\":\"$3,134.51\",\"location\":{\"id\":8,\"address\":\"389, Everest, Barwell Terrace\",\"city\":\"Murillo\",\"zipCode\":66061},\"ip\":\"142.216.23.1\"}]}",
        "{\"page\":\"4\",\"per_page\":10,\"total\":76,\"total_pages\":8,\"data\":[{\"id\":124,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1560330476187,\"txnType\":\"credit\",\"amount\":\"$3,238.70\",\"location\":{\"id\":6,\"address\":\"206, Portaline, Brooklyn Avenue\",\"city\":\"Brownlee\",\"zipCode\":80358},\"ip\":\"142.216.23.1\"},{\"id\":125,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1559783929312,\"txnType\":\"credit\",\"amount\":\"$3,441.63\",\"location\":{\"id\":7,\"address\":\"770, Deepends, Stockton Street\",\"city\":\"Ripley\",\"zipCode\":44139},\"ip\":\"132.169.40.222\"},{\"id\":128,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1560404217056,\"txnType\":\"debit\",\"amount\":\"$1,186.85\",\"location\":{\"id\":7,\"address\":\"770, Deepends, Stockton Street\",\"city\":\"Ripley\",\"zipCode\":44139},\"ip\":\"119.162.205.226\"},{\"id\":129,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1556517581997,\"txnType\":\"debit\",\"amount\":\"$3,482.87\",\"location\":{\"id\":8,\"address\":\"389, Everest, Barwell Terrace\",\"city\":\"Murillo\",\"zipCode\":66061},\"ip\":\"35.151.1.82\"},{\"id\":130,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1559695261267,\"txnType\":\"debit\",\"amount\":\"$317.42\",\"location\":{\"id\":8,\"address\":\"389, Everest, Barwell Terrace\",\"city\":\"Murillo\",\"zipCode\":66061},\"ip\":\"119.162.205.226\"},{\"id\":134,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1561802945256,\"txnType\":\"credit\",\"amount\":\"$965.92\",\"location\":{\"id\":6,\"address\":\"206, Portaline, Brooklyn Avenue\",\"city\":\"Brownlee\",\"zipCode\":80358},\"ip\":\"119.162.205.226\"},{\"id\":135,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1554254315000,\"txnType\":\"debit\",\"amount\":\"$141.50\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"111.83.155.103\"},{\"id\":137,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1554867084094,\"txnType\":\"debit\",\"amount\":\"$2,569.53\",\"location\":{\"id\":6,\"address\":\"206, Portaline, Brooklyn Avenue\",\"city\":\"Brownlee\",\"zipCode\":80358},\"ip\":\"5.116.1.11\"},{\"id\":139,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1557579078372,\"txnType\":\"credit\",\"amount\":\"$2,762.92\",\"location\":{\"id\":6,\"address\":\"206, Portaline, Brooklyn Avenue\",\"city\":\"Brownlee\",\"zipCode\":80358},\"ip\":\"119.162.205.226\"},{\"id\":145,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1560438366364,\"txnType\":\"debit\",\"amount\":\"$3,121.99\",\"location\":{\"id\":6,\"address\":\"206, Portaline, Brooklyn Avenue\",\"city\":\"Brownlee\",\"zipCode\":80358},\"ip\":\"111.83.155.103\"}]}",
        "{\"page\":\"5\",\"per_page\":10,\"total\":76,\"total_pages\":8,\"data\":[{\"id\":151,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1558504716984,\"txnType\":\"credit\",\"amount\":\"$1,701.09\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"181.191.153.61\"},{\"id\":157,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1561025417910,\"txnType\":\"credit\",\"amount\":\"$2,438.49\",\"location\":{\"id\":7,\"address\":\"770, Deepends, Stockton Street\",\"city\":\"Ripley\",\"zipCode\":44139},\"ip\":\"119.162.205.226\"},{\"id\":166,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1559740888182,\"txnType\":\"debit\",\"amount\":\"$2,451.88\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"5.116.1.11\"},{\"id\":167,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1558099494221,\"txnType\":\"debit\",\"amount\":\"$2,671.97\",\"location\":{\"id\":7,\"address\":\"770, Deepends, Stockton Street\",\"city\":\"Ripley\",\"zipCode\":44139},\"ip\":\"142.216.23.1\"},{\"id\":177,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1560380372070,\"txnType\":\"credit\",\"amount\":\"$959.88\",\"location\":{\"id\":8,\"address\":\"389, Everest, Barwell Terrace\",\"city\":\"Murillo\",\"zipCode\":66061},\"ip\":\"212.215.115.165\"},{\"id\":184,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1554579202054,\"txnType\":\"credit\",\"amount\":\"$3,717.84\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"35.151.1.82\"},{\"id\":188,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1554216081353,\"txnType\":\"credit\",\"amount\":\"$1,153.61\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"212.215.115.165\"},{\"id\":191,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1559101665729,\"txnType\":\"credit\",\"amount\":\"$430.89\",\"location\":{\"id\":6,\"address\":\"206, Portaline, Brooklyn Avenue\",\"city\":\"Brownlee\",\"zipCode\":80358},\"ip\":\"35.151.1.82\"},{\"id\":195,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1558056583495,\"txnType\":\"debit\",\"amount\":\"$189.16\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"132.169.40.222\"},{\"id\":197,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1554259601254,\"txnType\":\"credit\",\"amount\":\"$2,308.85\",\"location\":{\"id\":7,\"address\":\"770, Deepends, Stockton Street\",\"city\":\"Ripley\",\"zipCode\":44139},\"ip\":\"119.162.205.226\"}]}",
        "{\"page\":\"6\",\"per_page\":10,\"total\":76,\"total_pages\":8,\"data\":[{\"id\":198,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1555164373498,\"txnType\":\"credit\",\"amount\":\"$2,639.43\",\"location\":{\"id\":7,\"address\":\"770, Deepends, Stockton Street\",\"city\":\"Ripley\",\"zipCode\":44139},\"ip\":\"212.215.115.165\"},{\"id\":201,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1569412528132,\"txnType\":\"debit\",\"amount\":\"$3,683.03\",\"location\":{\"id\":6,\"address\":\"206, Portaline, Brooklyn Avenue\",\"city\":\"Brownlee\",\"zipCode\":80358},\"ip\":\"35.151.1.82\"},{\"id\":203,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1566561334738,\"txnType\":\"debit\",\"amount\":\"$3,013.97\",\"location\":{\"id\":7,\"address\":\"770, Deepends, Stockton Street\",\"city\":\"Ripley\",\"zipCode\":44139},\"ip\":\"5.116.1.11\"},{\"id\":212,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1564619118271,\"txnType\":\"debit\",\"amount\":\"$3,408.88\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"142.216.23.1\"},{\"id\":213,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1565296786685,\"txnType\":\"debit\",\"amount\":\"$3,220.14\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"142.216.23.1\"},{\"id\":225,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1568894452011,\"txnType\":\"debit\",\"amount\":\"$2,108.38\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"35.151.1.82\"},{\"id\":232,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1569107804438,\"txnType\":\"credit\",\"amount\":\"$3,135.85\",\"location\":{\"id\":6,\"address\":\"206, Portaline, Brooklyn Avenue\",\"city\":\"Brownlee\",\"zipCode\":80358},\"ip\":\"132.169.40.222\"},{\"id\":235,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1566672098896,\"txnType\":\"credit\",\"amount\":\"$2,744.16\",\"location\":{\"id\":8,\"address\":\"389, Everest, Barwell Terrace\",\"city\":\"Murillo\",\"zipCode\":66061},\"ip\":\"5.116.1.11\"},{\"id\":240,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1568544242024,\"txnType\":\"credit\",\"amount\":\"$2,070.92\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"5.116.1.11\"},{\"id\":241,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1566899821513,\"txnType\":\"debit\",\"amount\":\"$1,236.75\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"181.191.153.61\"}]}",
        "{\"page\":\"7\",\"per_page\":10,\"total\":76,\"total_pages\":8,\"data\":[{\"id\":245,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1568849983123,\"txnType\":\"debit\",\"amount\":\"$1,522.35\",\"location\":{\"id\":7,\"address\":\"770, Deepends, Stockton Street\",\"city\":\"Ripley\",\"zipCode\":44139},\"ip\":\"142.216.23.1\"},{\"id\":247,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1564263573286,\"txnType\":\"debit\",\"amount\":\"$408.17\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"212.215.115.165\"},{\"id\":249,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1569117159151,\"txnType\":\"debit\",\"amount\":\"$3,614.78\",\"location\":{\"id\":6,\"address\":\"206, Portaline, Brooklyn Avenue\",\"city\":\"Brownlee\",\"zipCode\":80358},\"ip\":\"5.116.1.11\"},{\"id\":253,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1566081309542,\"txnType\":\"credit\",\"amount\":\"$3,515.10\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"132.169.40.222\"},{\"id\":256,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1563004223065,\"txnType\":\"credit\",\"amount\":\"$619.12\",\"location\":{\"id\":1,\"address\":\"948, Entroflex, Franklin Avenue\",\"city\":\"Ilchester\",\"zipCode\":84181},\"ip\":\"5.116.1.11\"},{\"id\":260,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1566556666749,\"txnType\":\"credit\",\"amount\":\"$209.13\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"132.169.40.222\"},{\"id\":261,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1567973338308,\"txnType\":\"credit\",\"amount\":\"$2,366.91\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"181.191.153.61\"},{\"id\":262,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1567629432279,\"txnType\":\"debit\",\"amount\":\"$2,778.55\",\"location\":{\"id\":9,\"address\":\"961, Neptide, Elliott Walk\",\"city\":\"Bourg\",\"zipCode\":68602},\"ip\":\"181.191.153.61\"},{\"id\":271,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1562638297825,\"txnType\":\"debit\",\"amount\":\"$1,577.37\",\"location\":{\"id\":7,\"address\":\"770, Deepends, Stockton Street\",\"city\":\"Ripley\",\"zipCode\":44139},\"ip\":\"132.169.40.222\"},{\"id\":276,\"userId\":2,\"userName\":\"Bob Martin\",\"timestamp\":1566970227213,\"txnType\":\"debit\",\"amount\":\"$533.62\",\"location\":{\"id\":7,\"address\":\"770, Deepends, Stockton Street\",\"city\":\"Ripley\",\"zipCode\":44139},\"ip\":\"132.169.40.222\"}]}"};
        BigDecimal total = BigDecimal.ZERO;
        for(int i = 0; i < s.length; i++) {
            BigDecimal newPageTotal = getPageTotal(s[i], 4, 6, 100, 250);
            System.out.println("Page 1 Total:: " + total.intValue());
            total = total.add(newPageTotal);
        }
        total = total.setScale(0, RoundingMode.HALF_UP);
        System.out.println(" Total:: " + total.intValue());*/
    }
}