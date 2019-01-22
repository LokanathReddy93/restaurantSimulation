//Handles the Fries machine and its time
public class FriesMachine {
	static int availTime=0;
	synchronized static void addToQueue(int count) {
		availTime+=count*3;
	}
}
