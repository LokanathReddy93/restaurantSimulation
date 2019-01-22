//Handles the burger machine and its time
public class BurgerMachine {
	static int availTime=0;
	synchronized static void addToQueue(int count) {
		availTime+=count*5;
	}
}
