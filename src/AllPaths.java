import java.util.*;
import java.io.*;

public class AllPaths {
    private static HashMap<String, List<String>> left_right;

    public void topo(String begin, String end, String topoPath) throws Exception {
        left_right = new HashMap<>();
        BufferedReader r = new BufferedReader(new FileReader("topoPath"));
        String line;
        HashMap<String, Void> lines = new HashMap<>();
        while ((line = r.readLine()) != null) {
            if (lines.containsKey(line)) { // ensure no duplicate lines
                continue;
            }
            lines.put(line, null);
            int space_location = line.indexOf(' ');
            String left = line.substring(0, space_location);
            String right = line.substring(space_location + 1);
            if(left.equals(right)){ // rejects entries whereby left = right
                continue;
            }
            List<String> rights = left_right.get(left);
            if (rights == null) {
                rights = new ArrayList<String>();
                left_right.put(left, rights);
            }
            rights.add(right);
        }
        r.close();
        System.out.println("start");
        List<List<String>> routes = GetAllRoutes(begin, end);
        System.out.println("end");
        for (List<String> route : routes) {
            System.out.println(route);
        }
    }

    public static List<List<String>> GetAllRoutes(String start, String end) {
        List<List<String>> routes = new ArrayList<>();
        List<String> rights = left_right.get(start);
        if (rights != null) {
            for (String right : rights) {
                List<String> route = new ArrayList<>();
                route.add(start);
                route.add(right);
                Chain(routes, route, right, end);
            }
        }
        return routes;
    }

    public static void Chain(List<List<String>> routes, List<String> route, String right_most_currently, String end) {
        if (right_most_currently.equals(end)) {
            routes.add(route);
            return;
        }
        List<String> rights = left_right.get(right_most_currently);
        if (rights != null) {
            for (String right : rights) {
                if (!route.contains(right)) {
                    List<String> new_route = new ArrayList<String>(route);
                    new_route.add(right);
                    Chain(routes, new_route, right, end);
                }
            }
        }
    }
}
