import java.util.Comparator;
import java.util.PriorityQueue;
//Handles the tables resources. Maintains a priority queue of latest available table and hence
// the diner gets fastest available table.
public class Tables {
	static PriorityQueue < Integer[] > tablesAvailable = new PriorityQueue < Integer[] > (new Comparator<Integer[]>() {

		@Override
		public int compare(Integer[] o1, Integer[] o2) {
			
			return o1[1]-o2[1];
		}
		
	});
	static int getAvailableTables() {
		return tablesAvailable.size();
	}
	synchronized static void getSeated(Diners diner){
		Integer[] head=tablesAvailable.poll();
		diner.tableUsed=head[0];
		diner.seatingTime=Math.max(head[1],diner.arrivingTime);
	}
	synchronized static void addTable(Integer[] ta) {
		tablesAvailable.add(ta);
	}
}
