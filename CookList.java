import java.util.Comparator;
import java.util.PriorityQueue;
// Maintains the cook's time and the current diner order details
public class CookList {
	static PriorityQueue < Integer[] > cooksAvailable = new PriorityQueue < Integer[] > (new Comparator<Integer[]>() {

		@Override
		public int compare(Integer[] o1, Integer[] o2) {
			
			return o1[1]-o2[1];
		}
		
	});
	synchronized static int getAvailableCooks() {
		return cooksAvailable.size();
	}
	static Cook getCook(Cook myCook) {
		synchronized(cooksAvailable) {
			while(cooksAvailable.size()==0) {
				try {
					cooksAvailable.wait(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Integer[] head=cooksAvailable.poll();
			myCook.id=head[0];
			myCook.cookTime=Math.max(myCook.mydiner.seatingTime,head[1]);
			myCook.mydiner.cookName=head[0];
			myCook.rBurgers=myCook.mydiner.nBurgers;
			myCook.rFries=myCook.mydiner.nFries;
			myCook.rCoke=myCook.mydiner.ncoke;
			myCook.rSundae=myCook.mydiner.nsundae;
			return myCook;
		}
	}
	static void addCook(Integer[] ca) {
		synchronized(cooksAvailable) {
			cooksAvailable.add(ca);
			cooksAvailable.notify();
		}
	}
}
