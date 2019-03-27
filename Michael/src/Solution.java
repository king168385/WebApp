
// IMPORT LIBRARY PACKAGES NEEDED BY YOUR PROGRAM
// SOME CLASSES WITHIN A PACKAGE MAY BE RESTRICTED
// DEFINE ANY CLASS AND METHOD NEEDED
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

// CLASS BEGINS, THIS CLASS IS REQUIRED
public class Solution {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("test");
		
		List<Integer> forward1 = new ArrayList<Integer>(Arrays.asList(1, 8));
		List<Integer> forward2 = new ArrayList<Integer>(Arrays.asList(2, 15));
		List<Integer> forward3 = new ArrayList<Integer>(Arrays.asList(3, 9));
		
		List<Integer> back1 = new ArrayList<Integer>(Arrays.asList(1, 8));
		List<Integer> back2 = new ArrayList<Integer>(Arrays.asList(2, 11));
		List<Integer> back3 = new ArrayList<Integer>(Arrays.asList(3, 12));
		
		List<List<Integer>> forwardRouteList = new ArrayList<List<Integer>>();
		forwardRouteList.add(forward1);
		forwardRouteList.add(forward2);
		forwardRouteList.add(forward3);
		
		List<List<Integer>> backRouteList = new ArrayList<List<Integer>>();
		backRouteList.add(back1);
		backRouteList.add(back2);
		backRouteList.add(back3);
		
		optimalUtilization(20, forwardRouteList, backRouteList);
	}

	static List<List<Integer>> optimalUtilization(int maxTravelDist, List<List<Integer>> forwardRouteList,
			List<List<Integer>> returnRouteList) {
		TreeMap<Integer, List<Integer>> map = new TreeMap<Integer, List<Integer>>();
		List<List<Integer>> result = new ArrayList<List<Integer>>();

		for (List<Integer> forwardRoute : forwardRouteList) {
			if (forwardRoute.get(1) > maxTravelDist)
				continue;

			for (List<Integer> returnRdoute : returnRouteList) {
				if (returnRdoute.get(1) > maxTravelDist)
					continue;

				int sum = forwardRoute.get(1) + returnRdoute.get(1);

				if (sum <= maxTravelDist) {
					if (map.containsKey(sum)) {
						map.get(sum).add(forwardRoute.get(0));
						map.get(sum).add(returnRdoute.get(0));
					} else {
						List<Integer> route = new ArrayList<Integer>();
						route.add(forwardRoute.get(0));
						route.add(returnRdoute.get(0));
						map.put(sum, route);
					}
				}
			}
		}

		if (map.size() > 0) {
			List<Integer> optimal = map.lastEntry().getValue();

			int i = 0;
			while (i < optimal.size()) {
				result.add(optimal.subList(i, i + 2));
				i += 2;
			}
		}

		return result;
	}

	// METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
	List<List<Integer>> ClosestXdestinations(int numDestinations, List<List<Integer>> allLocations, int numDeliveries) {
		TreeMap<Double, List<Integer>> map = new TreeMap<Double, List<Integer>>();
		List<List<Integer>> result = new ArrayList<List<Integer>>();

		// calculate distance
		for (List<Integer> location : allLocations) {
			Integer x = location.get(0);
			Integer y = location.get(1);
			Double distance = Math.sqrt((x * x) + (y * y));

			if (map.containsKey(distance)) {
				map.get(distance).add(x);
				map.get(distance).add(y);
			} else {
				map.put(distance, location);
			}
		}

		if (map.size() > 0) {
			int numPick = 0;

			// pick destination
			for (List<Integer> locations : map.values()) {
				if (numPick == numDeliveries)
					break;

				int i = 0;
				while (i < locations.size() && numPick < numDeliveries) {
					result.add(locations.subList(i, i + 2));
					numPick++;
					i += 2;
				}
			}
		}

		return result;
	}
	// METHOD SIGNATURE ENDS
}