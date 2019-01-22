//Handles the Coke machine and its time
public class CokeMachine {
	static int availTime=0;
	synchronized static void addToQueue(int count) {
		availTime+=count*2;
	}
}
